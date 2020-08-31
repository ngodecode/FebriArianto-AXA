package id.fxlib.crawler.list

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.fxlib.crawler.febriarianto_axa.R
import id.fxlib.crawler.febriarianto_axa.model.UserContent

class ListAdapter(private val items:List<UserContent>) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_adapter, null)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = items[position]
        holder.txtText.text = "Id: ${item.id}\nUserId : ${item.userId}\nTitle: ${item.title}\nBody: ${item.body}"

        holder.itemView.setOnClickListener {

            val dialog = AlertDialog.Builder(holder.itemView.context)
            dialog.setTitle("Detail Id: ${item.id}")
            dialog.setMessage("UserId : ${item.userId}\nTitle: ${item.title}\nBody: ${item.body}")
            dialog.create().show()

        }
    }

}