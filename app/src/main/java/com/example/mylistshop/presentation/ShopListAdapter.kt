package com.example.mylistshop.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mylistshop.R
import com.example.mylistshop.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {
    var count = 0

    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        Log.d("ShopListAdapter", "onCreateViewHolder , count:${++count} ")
        val layout=when(viewType){
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            // если кто то из разработчиков добавил новый viewType и забыл его обработать тут, то желательно бросить исключение
            //это надо чтобы на этапе разработки когда кто то забудет обработать нужный viewType, то у него приложение упало с понятной ошибкой
            else -> throw  RuntimeException( "Unknown view type : $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopItemViewHolder(view)
    }


    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]
        holder.view.setOnClickListener{

        }
        holder.tvName.text = shopItem.name
        holder.tvCount.text = shopItem.count.toString()
        holder.view.setOnLongClickListener {
            true
        }
    }

    override fun onViewRecycled(holder: ShopItemViewHolder) {
        super.onViewRecycled(holder)
        holder.tvName.text = ""
        holder.tvCount.text = ""
        holder.tvName.setTextColor(
            ContextCompat.getColor(
                holder.view.context,
                android.R.color.black
            )
        )
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    //отвечает за определение типа View
    override fun getItemViewType(position: Int): Int {
        // по позиции элемента получаем элемент из списка
        val item = shopList[position]
        //если элемент активный, то надо вернуть число ,если нет то другое
        return if (item.enabled) {
            VIEW_TYPE_ENABLED

        } else {
            VIEW_TYPE_DISABLED
        }
    }

    class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name_item)
        val tvCount = view.findViewById<TextView>(R.id.tv_count_add)
    }
    companion object{
        const val VIEW_TYPE_ENABLED=0
        const val VIEW_TYPE_DISABLED=1

        const val MAX_POOL_SIZE = 10
    }
}