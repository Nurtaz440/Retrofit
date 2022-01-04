package mening.dasturim.retrofitapp.model

import java.io.Serializable


data class InfoItems(
    val id: String,
    val owner:DataPostItem,
    val text: String,
    val publishDate: String,
    val image:String


):Serializable