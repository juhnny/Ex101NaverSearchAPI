package com.juhnny.ex101naversearchapi.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.juhnny.ex101naversearchapi.R
import com.juhnny.ex101naversearchapi.databinding.RecyclerItemNaverSearchBinding
import com.juhnny.ex101naversearchapi.model.SearchItem

class NaverSearchAdapter(val context:Context, val items:MutableList<SearchItem>) : RecyclerView.Adapter<NaverSearchAdapter.VH>() {

    inner class VH(itemView:View) : RecyclerView.ViewHolder(itemView){
        val b = RecyclerItemNaverSearchBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView:View = LayoutInflater.from(context).inflate(R.layout.recycler_item_naver_search, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        //웹 정보를 받아오다보면 <> 태그문이 포함된 경우가 있다.
        //태그문을 없애는 방법
        var title = HtmlCompat.fromHtml(items[position].title, HtmlCompat.FROM_HTML_MODE_COMPACT)

        holder.b.title.text = title
        holder.b.lowPrice.text = items[position].lprice.toString()
        holder.b.brand.text = items[position].brand
        Glide.with(context).load(items[position].image).into(holder.b.iv)

        holder.b.root.setOnClickListener{
            //누르면 링크를 갖고 웹사이트 띄우기
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(items[position].link)))
        }
    }

    override fun getItemCount(): Int = items.size
}