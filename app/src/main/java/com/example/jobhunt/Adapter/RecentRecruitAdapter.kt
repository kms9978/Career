
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jobhunt.R
import com.example.jobhunt.Service.BookMarkService
import com.example.jobhunt.DataModel.BookMarkData
import com.example.jobhunt.DataModel.response.BookMarkResponse
import com.example.jobhunt.DataModel.RecentRecruit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class RecentRecruitAdapter(
    private var recentRecruitList: List<RecentRecruit> = emptyList(), // 최근 채용 정보 목록
    private var context: Context, // 어댑터를 사용하는 액티비티의 컨텍스트
    private var bookmarkService: BookMarkService // 북마크 서비스
) : RecyclerView.Adapter<RecentRecruitAdapter.ViewHolder>(), Filterable {

    private var filteredList = recentRecruitList // 필터링된 최근 채용 정보 목록


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recruitName: TextView = itemView.findViewById(R.id.recruit_name)
        private val recruitTitle: TextView = itemView.findViewById(R.id.recruit_title)
        private val recruitImage: ImageView = itemView.findViewById(R.id.recruit_img)
        private val recruitPosition: TextView = itemView.findViewById(R.id.ability)
        private val recruitPlan : TextView = itemView.findViewById(R.id.expire_date)
        private val bookmarkCheckBox : CheckBox = itemView.findViewById(R.id.add_bookmark)
        fun bind(recentRecruit: RecentRecruit) {
            recruitName.text = recentRecruit.companyName // 회사 이름 설정
            recruitTitle.text = recentRecruit.content // 채용 정보 제목 설정
            recruitPosition.text = recentRecruit.position // 채용 포지션 설정
            recruitPlan.text = recentRecruit.plan // 채용 마감일 설정
            Glide.with(itemView.context) // Glide를 사용하여 이미지 로드
                .load(recentRecruit.imgUrl)
                .placeholder(R.drawable.baseline_feedback_24)
                .into(recruitImage)

            bookmarkCheckBox.setOnCheckedChangeListener(null) // 리스너 제거

            bookmarkCheckBox.isChecked = recentRecruit.isChecked // 저장된 체크박스 상태를 설정

            bookmarkCheckBox.setOnCheckedChangeListener { _, isChecked ->
                recentRecruit.isChecked = isChecked // 체크박스 상태를 저장
                if (isChecked) {
                    addBookmark(recentRecruit)
                } else {
                    //deleteBookMark(recentRecruit.bookMarkData)
                }
            }
            itemView.setOnClickListener {
                val url = recentRecruit.link
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.jobkorea.co.kr$url"))
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { // 뷰홀더 생성
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recent, parent, false) // 뷰 인플레이션
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredList[position]) // 뷰홀더 바인딩
    }

    override fun getItemCount(): Int {
        return filteredList.size // 최근 채용 정보 목록의 크기를 반환합니다.
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

    private fun addBookmark(recentRecruit: RecentRecruit) {
        val bookMarkData = BookMarkService.BookMark(recentRecruit)
        bookmarkService.saveBookMark(bookMarkData).enqueue(object : Callback<BookMarkResponse> {
            override fun onResponse(call: Call<BookMarkResponse>, response: Response<BookMarkResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "${bookMarkData.bookMarkName} 즐겨찾기 추가 완료!", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e(ContentValues.TAG, "Failed to add ${recentRecruit.companyName} to bookmark: ${response.code()} ${response.message()}")
                    Toast.makeText(context, "${recentRecruit.companyName} 즐겨찾기 추가 실패: ${response.code()} ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<BookMarkResponse>, t: Throwable) {
                Log.e(ContentValues.TAG, "Failed to add ${recentRecruit.companyName} to bookmark", t)
                Toast.makeText(context, "${recentRecruit.companyName} 즐겨찾기 추가 실패: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun deleteBookMark(bookmarkData: BookMarkData?) {
        val userBookmarkId = bookmarkData?.user_bookmark_id
        if (userBookmarkId != null && userBookmarkId != 0L) {
            bookmarkService.deleteBookMark(userBookmarkId).enqueue(object : Callback<BookMarkResponse> {
                override fun onResponse(call: Call<BookMarkResponse>, response: Response<BookMarkResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(context, "북마크가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e(ContentValues.TAG, "Failed to delete bookmark: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<BookMarkResponse>, t: Throwable) {
                    Log.e(ContentValues.TAG, "Failed to delete bookmark", t)
                }
            })
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