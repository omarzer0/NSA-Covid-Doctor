package az.zero.nsacoviddoctor.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import az.zero.nsacoviddoctor.R
import az.zero.nsacoviddoctor.domain.model.data_adapter.PreventionData

class PrevHomeAdapter(
    private val preventionData: List<PreventionData>,
) :
    RecyclerView.Adapter<PrevHomeAdapter.HomeViewHolder>() {
    var data = preventionData
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        var view =
            layoutInflater.inflate(R.layout.prevention_list_item, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)

    }


    override fun getItemCount() = data.size

    inner class HomeViewHolder(var itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val preventionImage: ImageView = itemView.findViewById(R.id.prev_item_iv)
        val preventionTitle: TextView = itemView.findViewById(R.id.prev_item_tv)


        fun bind(item: PreventionData) {
            preventionImage.setImageResource(item.preventionImage)
            preventionTitle.text = item.preventionTitle
        }


    }
}