import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jobhunt.R
import com.example.jobhunt.dataModel.RecentRecruit
import java.util.*

class RecentRecruitAdapter(private val recentRecruitList: MutableList<RecentRecruit>) : RecyclerView.Adapter<RecentRecruitAdapter.ViewHolder>(),
    Filterable {
//RecentRecruitAdapter 클래스 정의. RecyclerView.Adapter 클래스를 상속받아 만들어진 클래스이며, ViewHolder 클래스를 사용한다.

    // Declare properties for the filtered list and list of company names
    private var filteredList = recentRecruitList

    private var companyNameList = recentRecruitList.map {
        it.companyName
    }

    init {
        // Initialize the companyNameList
        companyNameList = recentRecruitList.map {
            it.companyName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recent, parent, false)//item_recent 레이아웃을 inflate하여 사용한다.
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recentRecruit = recentRecruitList[position]
        holder.recruitName.text = recentRecruit.companyName
        holder.recruitTitle.text = recentRecruit.content
        holder.recruitPosition.text = recentRecruit.position
        holder.recruitPlan.text = recentRecruit.plan
        // 이미지 로드하기
        Glide.with(holder.itemView.context)
            .load(recentRecruit.imgUrl) // RecentRecruit 객체에서 이미지 URL 가져오기
            .placeholder(R.drawable.baseline_feedback_24)
            .into(holder.recruitImage)

    }
    //onBindViewHolder 메소드를 오버라이드하여, 뷰 홀더와 데이터를 바인딩한다.
    //position은 데이터의 위치를 의미한다. 해당 위치의 recentRecruit 객체를 가져와서 holder 객체에 있는 뷰에 데이터를 설정한다.


    //getItemCount 메소드를 오버라이드하여, 어댑터가 관리하는 데이터의 크기를 반환한다.
    override fun getItemCount(): Int {
        return recentRecruitList.size
    }
    // Return the filter object
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredResults = mutableListOf<RecentRecruit>()
                val searchQuery = constraint.toString().lowercase(Locale.getDefault()) // toLowercase() 에서 변경

                if (searchQuery.isEmpty()) {
                    // If the search query is empty, show all items
                    filteredResults.addAll(recentRecruitList)
                } else {
                    // Otherwise, show only the items whose company name matches the search query
                    for (i in 0 until recentRecruitList.size) {
                        val companyName = companyNameList[i]
                        if (companyName.lowercase(Locale.getDefault()).contains(searchQuery)) {
                            filteredResults.add(recentRecruitList[i])
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredResults
                return filterResults
            }
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as MutableList<RecentRecruit>
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recruitName: TextView = itemView.findViewById(R.id.recruit_name)
        val recruitTitle: TextView = itemView.findViewById(R.id.recruit_title)
        val recruitImage: ImageView = itemView.findViewById(R.id.recruit_img) // ImageView 추가
        val recruitPosition: TextView = itemView.findViewById(R.id.ability)
        val recruitPlan : TextView = itemView.findViewById(R.id.expire_date)

        init {

            recruitImage.setOnClickListener {
                val url = filteredList[adapterPosition].url // 클릭한 항목의 링크 가져오기
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.jobkorea.co.kr$url")) // 링크intent domain+
                it.context.startActivity(intent) // 인텐트 실행
            }

        }
    }
}