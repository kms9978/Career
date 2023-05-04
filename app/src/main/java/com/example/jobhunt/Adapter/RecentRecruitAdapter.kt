
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jobhunt.R
import com.example.jobhunt.Service.BookMarkService
import com.example.jobhunt.dataModel.BookMarkData
import com.example.jobhunt.dataModel.RecentRecruit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class RecentRecruitAdapter(
    private var recentRecruitList: List<RecentRecruit> = emptyList(),
    private val onBookmarkClick: (position: Int) -> Unit
) : RecyclerView.Adapter<RecentRecruitAdapter.ViewHolder>(), Filterable {

    private var filteredList = recentRecruitList

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://54.227.205.92:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val bookmarkService = retrofit.create(BookMarkService::class.java)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recruitName: TextView = itemView.findViewById(R.id.recruit_name)
        private val recruitTitle: TextView = itemView.findViewById(R.id.recruit_title)
        private val recruitImage: ImageView = itemView.findViewById(R.id.recruit_img)
        private val recruitPosition: TextView = itemView.findViewById(R.id.ability)
        private val recruitPlan: TextView = itemView.findViewById(R.id.expire_date)
        private val bookmarkSwitch: Switch = itemView.findViewById(R.id.add_bookmark)

        fun bind(recentRecruit: RecentRecruit) {
            recruitName.text = recentRecruit.companyName
            recruitTitle.text = recentRecruit.content
            recruitPosition.text = recentRecruit.position
            recruitPlan.text = recentRecruit.plan

            Glide.with(itemView.context)
                .load(recentRecruit.imgUrl)
                .placeholder(R.drawable.baseline_feedback_24)
                .into(recruitImage)

            bookmarkSwitch.isChecked = recentRecruit.isBookmarked
            bookmarkSwitch.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = filteredList[position]
                    item.isBookmarked = !item.isBookmarked
                    if (item.isBookmarked) {
                        val bookmarkData = BookMarkData(
                            user_bookmark_id = 0L, // user_bookmark_id는 서버에서 할당되므로 일단 0으로 설정합니다.
                            bookMarkImg = item.imgUrl,
                            bookMarkName = item.companyName,
                            bookMark_Start_Date = item.plan.split("~")[0].trim(),
                            bookMark_End_Date = item.plan.split("~")[1].trim(),
                            company_link = item.url
                        )
                        val call = bookmarkService.saveBookmark(bookmarkData)
                        call.enqueue(object : Callback<Void> {
                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                // 전송 성공시 처리할 코드
                            }

                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                // 전송 실패시 처리할 코드
                            }
                        })
                    } else {
                        // 북마크를 해제하는 경우에는 서버에서 해당 북마크를 삭제해야 합니다.
                        // 이 부분은 서버에서 DELETE 메소드를 이용해서 북마크를 삭제하는 로직을 작성해야 합니다.
                    }
                    onBookmarkClick(position)
                }
            }

            itemView.setOnClickListener {
                val url = recentRecruit.url
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.jobkorea.co.kr$url"))
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recent, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredList[position])
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredResults = mutableListOf<RecentRecruit>()
                val searchQuery = constraint.toString().lowercase(Locale.getDefault())

                if (searchQuery.isEmpty()) {
                    filteredResults.addAll(recentRecruitList)
                } else {
                    for (i in 0 until recentRecruitList.size) {
                        val content = recentRecruitList[i].content
                        if (content?.lowercase(Locale.getDefault())?.contains(searchQuery) == true) {
                            filteredResults.add(recentRecruitList[i])
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredResults
                filterResults.count = filteredResults.size
                return filterResults
            }
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                results?.let {
                    @Suppress("UNCHECKED_CAST")
                    filteredList = results.values as? List<RecentRecruit> ?: emptyList()
                    notifyDataSetChanged()
                }
            }
        }
    }
    fun setRecentRecruitDataList(recentRecruitList: List<RecentRecruit>, searchQuery: String = "") {
        this.recentRecruitList = recentRecruitList
        filteredList = if (searchQuery.isEmpty()) {
            recentRecruitList
        } else {
            recentRecruitList.filter {
                it.content?.lowercase(Locale.getDefault())?.contains(searchQuery.lowercase(Locale.getDefault())) == true
            }
        }
        notifyDataSetChanged()
    }
}