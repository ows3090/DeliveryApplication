package ows.kotlinstudy.deliveryapplicaiton.data.response.search

data class SearchPoiInfo(
    val totalCount: String,
    val count: String,
    val page: String,
    val pois: Pois
)