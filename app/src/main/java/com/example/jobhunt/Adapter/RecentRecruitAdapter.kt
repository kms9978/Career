import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobhunt.R
import com.example.jobhunt.data.RecentRecruit

class RecentRecruitAdapter(private val recentRecruitList: List<RecentRecruit>) : RecyclerView.Adapter<RecentRecruitAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recent, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recentRecruit = recentRecruitList[position]
        holder.recruitName.text = recentRecruit.content
        holder.recruitTitle.text = recentRecruit.position
    }

    override fun getItemCount(): Int {
        return recentRecruitList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recruitName: TextView = itemView.findViewById(R.id.recruit_name)
        val recruitTitle: TextView = itemView.findViewById(R.id.recruit_title)
    }
}