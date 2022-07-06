package ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.detail.review

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ows.kotlinstudy.deliveryapplicaiton.data.entity.ReviewEntity
import ows.kotlinstudy.deliveryapplicaiton.data.repository.restaurant.review.DefaultRestaurantReviewRepository
import ows.kotlinstudy.deliveryapplicaiton.data.repository.restaurant.review.RestaurantReviewRepository
import ows.kotlinstudy.deliveryapplicaiton.model.restaurant.review.RestaurantReviewModel
import ows.kotlinstudy.deliveryapplicaiton.screen.base.BaseViewModel

class RestaurantReviewListViewModel(
    private val restaurantTitle: String,
    private val restaurantReviewRepository: RestaurantReviewRepository
) : BaseViewModel() {

    val reviewStateLiveData =
        MutableLiveData<RestaurantReviewState>(RestaurantReviewState.Uninitialized)

    override fun fetchData(): Job = viewModelScope.launch {
        reviewStateLiveData.value = RestaurantReviewState.Loading
        val result = restaurantReviewRepository.getReviews(restaurantTitle)

        when (result) {
            is DefaultRestaurantReviewRepository.Result.Success<*> -> {
                val reviews = result.data as List<ReviewEntity>
                reviewStateLiveData.value = RestaurantReviewState.Success(
                    reviews.map {
                        RestaurantReviewModel(
                            id = it.hashCode().toLong(),
                            title = it.title,
                            description = it.content,
                            grade = it.rating,
                            thumbnailImageUri = if(it.imageUriList.isNullOrEmpty()){
                                null
                            }else{
                                Uri.parse(it.imageUriList.first())
                            }
                        )
                    }
                )
            }
            else -> Unit
        }
    }
}