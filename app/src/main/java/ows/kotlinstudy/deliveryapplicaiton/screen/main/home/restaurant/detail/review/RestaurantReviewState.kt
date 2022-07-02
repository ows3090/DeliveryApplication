package ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.detail.review

import ows.kotlinstudy.deliveryapplicaiton.data.entity.RestaurantReviewEntity
import ows.kotlinstudy.deliveryapplicaiton.model.restaurant.review.RestaurantReviewModel

sealed class RestaurantReviewState {

    object Uninitialized : RestaurantReviewState()

    object Loading : RestaurantReviewState()

    data class Success(
        val reviewList: List<RestaurantReviewModel>
    ) : RestaurantReviewState()

}