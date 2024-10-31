package com.aaronrubidev.to_doapp.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aaronrubidev.to_doapp.R
import com.aaronrubidev.to_doapp.data.models.Priority
import com.aaronrubidev.to_doapp.data.models.ToDoData

class ListAdapter : RecyclerView.Adapter<ListAdapter.ItemViewHolder>() {

    private var dataList = emptyList<ToDoData>()


    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title_text)
        val description: TextView = view.findViewById(R.id.description_text)
        val priorityIndicator: CardView = view.findViewById(R.id.priority_indicator)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataList[position]
        holder.title.text = item.title
        holder.description.text = item.description

        val priority = dataList[position].priority
        when (priority) {
            Priority.HIGH -> holder.priorityIndicator.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.red
                )
            )

            Priority.MEDIUM -> holder.priorityIndicator.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.yellow
                )
            )

            Priority.LOW -> holder.priorityIndicator.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.green
                )
            )
        }
    }

    fun setData(todoData: List<ToDoData>) {
        this.dataList = todoData
        notifyDataSetChanged()
    }
}
