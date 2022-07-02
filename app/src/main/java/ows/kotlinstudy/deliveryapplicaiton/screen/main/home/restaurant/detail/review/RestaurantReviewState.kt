package ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.detail.review

import ows.kotlinstudy.deliveryapplicaiton.data.entity.RestaurantReviewEntity

sealed class RestaurantReviewState {

    object Uninitialized : RestaurantReviewState()

    object Loading : RestaurantReviewState()

    data class Success(
        val reviewList: List<RestaurantReviewEntity>
    ) : RestaurantReviewState()

}