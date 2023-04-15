import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobhunt.R
import com.example.jobhunt.dataModel.RecentRecruit

class RecentRecruitAdapter(private val recentRecruitList: List<RecentRecruit>) : RecyclerView.Adapter<RecentRecruitAdapter.ViewHolder>() {
//RecentRecruitAdapter 클래스 정의. RecyclerView.Adapter 클래스를 상속받아 만들어진 클래스이며, ViewHolder 클래스를 사용한다.


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recent, parent, false)//item_recent 레이아웃을 inflate하여 사용한다.
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recentRecruit = recentRecruitList[position]
        holder.recruitName.text = recentRecruit.content
        holder.recruitTitle.text = recentRecruit.position
    }
    //onBindViewHolder 메소드를 오버라이드하여, 뷰 홀더와 데이터를 바인딩한다.
    //position은 데이터의 위치를 의미한다. 해당 위치의 recentRecruit 객체를 가져와서 holder 객체에 있는 뷰에 데이터를 설정한다.


    //getItemCount 메소드를 오버라이드하여, 어댑터가 관리하는 데이터의 크기를 반환한다.
    override fun getItemCount(): Int {
        return recentRecruitList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recruitName: TextView = itemView.findViewById(R.id.recruit_name)
        val recruitTitle: TextView = itemView.findViewById(R.id.recruit_title)
    }
}