package ows.kotlinstudy.deliveryapplicaiton.data.repository.restaurant.review

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import ows.kotlinstudy.deliveryapplicaiton.data.entity.ReviewEntity

class DefaultRestaurantReviewRepository(
    private val ioDispatcher: CoroutineDispatcher,
    private val firestore: FirebaseFirestore
) : RestaurantReviewRepository {

    override suspend fun getReviews(restaurantTitle: String): Result =
        withContext(ioDispatcher) {
            return@withContext try {
                val snapshot = firestore
                    .collection("review")
                    .whereEqualTo("restaurantTitle", restaurantTitle)
                    .get()
                    .await()

                Result.Success(snapshot.documents.map {
                    ReviewEntity(
                        userId = it.get("userId") as String,
                        title = it.get("title") as String,
                        createdAt = it.get("createdAt") as Long,
                        content = it.get("content") as String,
                        rating = (it.get("rating") as Double).toFloat(),
                        imageUriList = it.get("imageUriList") as? List<String>,
                        orderId = it.get("orderId") as String,
                        restaurantTitle = it.get("restaurantTitle") as String
                    )
                })
            } catch (e: Exception) {
                e.printStackTrace()
                Result.Error(e)
            }
        }

    sealed class Result {
        data class Success<T>(
            val data: T? = null
        ) : Result()

        data class Error(
            val e: Throwable
        ) : Result()
    }
}