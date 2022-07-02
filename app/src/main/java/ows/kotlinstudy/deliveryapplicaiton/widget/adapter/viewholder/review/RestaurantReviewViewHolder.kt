package ows.kotlinstudy.deliveryapplicaiton.widget.adapter.viewholder.review

import androidx.core.view.isGone
import androidx.core.view.isVisible
import ows.kotlinstudy.deliveryapplicaiton.R
import ows.kotlinstudy.deliveryapplicaiton.databinding.ViewholderEmptyBinding
import ows.kotlinstudy.deliveryapplicaiton.databinding.ViewholderRestaurantBinding
import ows.kotlinstudy.deliveryapplicaiton.databinding.ViewholderRestaurantReviewBinding
import ows.kotlinstudy.deliveryapplicaiton.extensions.clear
import ows.kotlinstudy.deliveryapplicaiton.extensions.load
import ows.kotlinstudy.deliveryapplicaiton.model.Model
import ows.kotlinstudy.deliveryapplicaiton.model.restaurant.RestaurantModel
import ows.kotlinstudy.deliveryapplicaiton.model.restaurant.review.RestaurantReviewModel
import ows.kotlinstudy.deliveryapplicaiton.screen.base.BaseViewModel
import ows.kotlinstudy.deliveryapplicaiton.util.provider.ResourcesProvider
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.listener.AdapterListener
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.listener.restaurant.RestaurantListListener
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.viewholder.ModelViewHolder

class RestaurantReviewViewHolder(
    private val binding: ViewholderRestaurantReviewBinding,
    viewModel: BaseViewModel,
    resourcesProvider: ResourcesProvider
) : ModelViewHolder<RestaurantReviewModel>(binding, viewModel, resourcesProvider) {

    override fun reset() = with(binding) {
        reviewThumbnailImage.clear()
        reviewThumbnailImage.isGone = true
    }

    override fun bindData(model: RestaurantReviewModel) {
        super.bindData(model)
        with(binding) {
            if (model.thumbnailImageUri != null) {
                reviewThumbnailImage.isVisible = true
                reviewThumbnailImage.load(model.thumbnailImageUri.toString(), 24f)
            } else {
                reviewThumbnailImage.isGone = true
            }

            reviewTitleText.text = model.title
            reviewText.text = model.description

            ratingBar.rating = model.grade.toFloat()
        }
    }

    override fun bindViews(model: RestaurantReviewModel, adapterListener: AdapterListener) = Unit
}