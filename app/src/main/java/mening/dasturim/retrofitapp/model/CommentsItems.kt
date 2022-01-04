package mening.dasturim.retrofitapp.model

import java.io.Serializable

data class CommentsItems (
    val id : String,
    val message:String,
    val owner:DataPostItem,
    val publishDate:String

        ):Serializable