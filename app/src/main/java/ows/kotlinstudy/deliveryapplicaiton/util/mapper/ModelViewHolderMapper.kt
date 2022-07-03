package ows.kotlinstudy.deliveryapplicaiton.util.mapper

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import ows.kotlinstudy.deliveryapplicaiton.data.entity.RestaurantEntity
import ows.kotlinstudy.deliveryapplicaiton.databinding.*
import ows.kotlinstudy.deliveryapplicaiton.model.CellType
import ows.kotlinstudy.deliveryapplicaiton.model.Model
import ows.kotlinstudy.deliveryapplicaiton.screen.base.BaseViewModel
import ows.kotlinstudy.deliveryapplicaiton.util.provider.ResourcesProvider
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.viewholder.EmptyViewHolder
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.viewholder.ModelViewHolder
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.viewholder.food.FoodMenuViewHolder
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.viewholder.order.OrderMenuViewHolder
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.viewholder.restaurant.LikeRestaurantViewHolder
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.viewholder.restaurant.RestaurantViewHolder
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.viewholder.review.RestaurantReviewViewHolder

object ModelViewHolderMapper {

    @Suppress("UNCHECKED_CAST")
    fun <M : Model> map(
        parent: ViewGroup,
        type: CellType,
        viewModel: BaseViewModel,
        resourcesProvider: ResourcesProvider
    ): ModelViewHolder<M> {
        val inflater = LayoutInflater.from(parent.context)
        val viewHolder = when (type) {
            CellType.EMPTY_CELL -> EmptyViewHolder(
                ViewholderEmptyBinding.inflate(inflater, parent, false),
                viewModel,
                resourcesProvider
            )
            CellType.RESTAURANT_CELL -> RestaurantViewHolder(
                ViewholderRestaurantBinding.inflate(inflater, parent, false),
                viewModel,
                resourcesProvider
            )
            CellType.LIKE_RESTAURANT_CELL -> LikeRestaurantViewHolder(
                ViewholderLikeRestaurantBinding.inflate(inflater, parent, false),
                viewModel,
                resourcesProvider
            )
            CellType.FOOD_CELL -> FoodMenuViewHolder(
                ViewholderFoodMenuBinding.inflate(inflater, parent, false),
                viewModel,
                resourcesProvider
            )
            CellType.REVIEW_CELL -> RestaurantReviewViewHolder(
                ViewholderRestaurantReviewBinding.inflate(inflater, parent, false),
                viewModel,
                resourcesProvider
            )
            CellType.ORDER_FOOD_CELL -> OrderMenuViewHolder(
                ViewholderOrderMenuBinding.inflate(inflater, parent, false),
                viewModel,
                resourcesProvider
            )
        }
        return viewHolder as ModelViewHolder<M>
    }
}