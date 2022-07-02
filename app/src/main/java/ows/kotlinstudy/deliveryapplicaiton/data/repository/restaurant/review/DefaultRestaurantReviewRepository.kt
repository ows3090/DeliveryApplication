package ows.kotlinstudy.deliveryapplicaiton.data.repository.restaurant.review

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ows.kotlinstudy.deliveryapplicaiton.data.entity.RestaurantReviewEntity

class DefaultRestaurantReviewRepository(
    private val ioDispatcher: CoroutineDispatcher
) : RestaurantReviewRepository {

    override suspend fun getReviews(restaurantTitle: String): List<RestaurantReviewEntity> =
        withContext(ioDispatcher) {
            return@withContext (0..10).map {
                RestaurantReviewEntity(
                    id = 0,
                    title = "제목 ${it}",
                    description = "내용 ${it}",
                    grade = (1 until 5).random()
                )
            }
        }
}