package ows.kotlinstudy.deliveryapplicaiton.data.entity

data class ReviewEntity(
    val userId: String,
    val title: String,
    val createdAt: Long,
    val content: String,
    val rating: Float,
    val imageUriList: List<String>? = null,
    val orderId: String,
    val restaurantTitle: String
)
