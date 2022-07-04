package ows.kotlinstudy.deliveryapplicaiton.data.repository.order

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import ows.kotlinstudy.deliveryapplicaiton.data.entity.OrderEntity
import ows.kotlinstudy.deliveryapplicaiton.data.entity.RestaurantFoodEntity

class DefaultOrderRepository(
    private val ioDispatcher: CoroutineDispatcher,
    private val firestore: FirebaseFirestore
) : OrderRepository {
    override suspend fun orderMenu(
        userId: String,
        restaurantId: Long,
        foodMenuList: List<RestaurantFoodEntity>,
        restaurantTitle: String
    ): Result = withContext(ioDispatcher) {
        val result: Result
        val orderMenuData = hashMapOf(
            "restaurantId" to restaurantId,
            "userId" to userId,
            "orderMenuList" to foodMenuList,
            "restaurantTitle" to restaurantTitle
        )
        result = try {
            firestore.collection("order").add(orderMenuData)
            Result.Success<Any>()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e)
        }
        return@withContext result
    }

    override suspend fun getAllOrderMenus(userId: String): Result = withContext(ioDispatcher) {
        return@withContext try {
            val result: QuerySnapshot = firestore
                .collection("order")
                .whereEqualTo("userId", userId)
                .get()
                .await()

            Result.Success(result.documents.map {
                OrderEntity(
                    id = it.id,
                    userId = it.get("userId") as String,
                    restaurantId = it.get("restaurantId") as Long,
                    foodMenuList = (it.get("orderMenuList") as ArrayList<Map<String, Any>>).map { food ->
                        RestaurantFoodEntity(
                            id = food.get("id") as String,
                            title = food.get("title") as String,
                            prices = (food.get("prices") as Long).toInt(),
                            imageUrl = food.get("imageUrl") as String,
                            restaurantId = food.get("restaurantId") as Long,
                            description = food.get("description") as String,
                            restaurantTitle = food.get("restaurantTitle") as String
                        )
                    },
                    restaurantTitle = (it.get("restaurantTitle") as String)
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