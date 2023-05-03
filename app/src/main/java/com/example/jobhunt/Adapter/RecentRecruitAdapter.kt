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
    private val recentRecruitList: List<RecentRecruit>
) : RecyclerView.Adapter<RecentRecruitAdapter.ViewHolder>(), Filterable {

    private var filteredList = recentRecruitList
    private var companyNameList = recentRecruitList.map {
        it.companyName
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recruitName: TextView = itemView.findViewById(R.id.recruit_name)
        private val recruitTitle: TextView = itemView.findViewById(R.id.recruit_title)
        private val recruitImage: ImageView = itemView.findViewById(R.id.recruit_img)
        private val recruitPosition: TextView = itemView.findViewById(R.id.ability)
        private val recruitPlan : TextView = itemView.findViewById(R.id.expire_date)

        fun bind(recentRecruit: RecentRecruit) {
            recruitName.text = recentRecruit.companyName
            recruitTitle.text = recentRecruit.content
            recruitPosition.text = recentRecruit.position
            recruitPlan.text = recentRecruit.plan

            Glide.with(itemView.context)
                .load(recentRecruit.imgUrl)
                .placeholder(R.drawable.baseline_feedback_24)
                .into(recruitImage)

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
}