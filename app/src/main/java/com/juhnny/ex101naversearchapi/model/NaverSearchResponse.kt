package com.juhnny.ex101naversearchapi.model

//필요한 값들만 사용하면 된다.
data class NaverSearchResponse(val total:Int, val display:Int, val items:MutableList<SearchItem>)

data class SearchItem(val title:String,
                      val link:String,
                      val image:String,
                      val lprice:Int,
                      val mallName:String,
                      val brand:String)