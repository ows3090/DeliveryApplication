package ows.kotlinstudy.deliveryapplicaiton.data.repository.restaurant.review

import ows.kotlinstudy.deliveryapplicaiton.data.entity.RestaurantReviewEntity

interface RestaurantReviewRepository {

    suspend fun getReviews(restaurantTitle: String): List<RestaurantReviewEntity>
}