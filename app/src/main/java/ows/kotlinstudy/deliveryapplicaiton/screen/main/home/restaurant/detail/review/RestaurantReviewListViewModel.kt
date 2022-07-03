package ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.detail.review

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ows.kotlinstudy.deliveryapplicaiton.data.repository.restaurant.review.RestaurantReviewRepository
import ows.kotlinstudy.deliveryapplicaiton.model.restaurant.review.RestaurantReviewModel
import ows.kotlinstudy.deliveryapplicaiton.screen.base.BaseViewModel

class RestaurantReviewListViewModel(
    private val restaurantTitle: String,
    private val restaurantReviewRepository: RestaurantReviewRepository
): BaseViewModel() {

    val reviewStateLiveData = MutableLiveData<RestaurantReviewState>(RestaurantReviewState.Uninitialized)

    override fun fetchData(): Job = viewModelScope.launch {
        reviewStateLiveData.value = RestaurantReviewState.Loading
        val reviews = restaurantReviewRepository.getReviews(restaurantTitle)
        reviewStateLiveData.value = RestaurantReviewState.Success(
            reviews.map {
                RestaurantReviewModel(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    grade = it.grade,
                    thumbnailImageUri = it.images?.first()
                )
            }
        )
    }
}