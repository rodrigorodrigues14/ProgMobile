package com.example.projetothefinal.interfaceFrags.Home.CardFrags.Cards

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.example.projetothefinal.R
import retrofit.Sumario

class CardRecyclerAdapter(private val context: Context, private var stocklist: List<Sumario>) :
    RecyclerView.Adapter<CardRecyclerAdapter.MyViewHolder>() {

    private var clickListener: DetailedViewClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_view_design, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return stocklist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = stocklist[position]

        with(holder) {
            preco.text = item.current_prince.toString()
            simbolo.text = item.symbol
            percentagem.text = item.change_percent.toString()

            val color = ContextCompat.getColor(context, if (item.change_percent < 0) R.color.red else R.color.green)
            percentagem.setTextColor(color)
            percgrafico.setTextColor(color)

            val rotationAngle = if (item.change_percent < 0) 180f else 0f
            percgrafico.rotation = rotationAngle

            Picasso.get().load(item.logo_url).into(image)

            itemView.setOnClickListener {
                clickListener?.onDetailedViewClick(item)
            }
        }
    }

    fun setDetailedItemClickListener(listener: DetailedViewClick) {
        clickListener = listener
    }

    interface DetailedViewClick {
        fun onDetailedViewClick(sumario: Sumario)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val preco: TextView = itemView.findViewById(R.id.tvPreco)
        val simbolo: TextView = itemView.findViewById(R.id.tvSimbolo)
        val percentagem: TextView = itemView.findViewById(R.id.tvPercentagem)
        val percgrafico: TextView = itemView.findViewById(R.id.tvPercentagemGrafico)
    }
}
