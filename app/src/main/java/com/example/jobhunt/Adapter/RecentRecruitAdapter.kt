import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jobhunt.R
import com.example.jobhunt.dataModel.RecentRecruit
import java.util.*

class RecentRecruitAdapter(
    private val recentRecruitList: List<RecentRecruit>) : RecyclerView.Adapter<RecentRecruitAdapter.ViewHolder>(),
    Filterable {

    private var filteredList = recentRecruitList
    private var companyNameList = recentRecruitList.map {
        it.companyName
    }

    init {
        companyNameList = recentRecruitList.map {
            it.companyName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recent, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recentRecruit = filteredList[position]
        holder.recruitName.text = recentRecruit.companyName
        holder.recruitTitle.text = recentRecruit.content
        holder.recruitPosition.text = recentRecruit.position
        holder.recruitPlan.text = recentRecruit.plan

        Glide.with(holder.itemView.context)
            .load(recentRecruit.imgUrl)
            .placeholder(R.drawable.baseline_feedback_24)
            .into(holder.recruitImage)
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
                filteredList = results?.values as List<RecentRecruit>
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