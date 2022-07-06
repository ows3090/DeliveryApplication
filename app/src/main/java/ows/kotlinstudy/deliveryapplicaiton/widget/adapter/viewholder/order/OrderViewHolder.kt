package ows.kotlinstudy.deliveryapplicaiton.widget.adapter.viewholder.order

import com.bumptech.glide.load.resource.bitmap.CenterCrop
import ows.kotlinstudy.deliveryapplicaiton.R
import ows.kotlinstudy.deliveryapplicaiton.databinding.ViewholderFoodMenuBinding
import ows.kotlinstudy.deliveryapplicaiton.databinding.ViewholderOrderBinding
import ows.kotlinstudy.deliveryapplicaiton.databinding.ViewholderOrderMenuBinding
import ows.kotlinstudy.deliveryapplicaiton.extensions.clear
import ows.kotlinstudy.deliveryapplicaiton.extensions.load
import ows.kotlinstudy.deliveryapplicaiton.model.restaurant.food.FoodModel
import ows.kotlinstudy.deliveryapplicaiton.model.restaurant.order.OrderModel
import ows.kotlinstudy.deliveryapplicaiton.screen.base.BaseViewModel
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.detail.menu.RestaurantMenuListViewModel
import ows.kotlinstudy.deliveryapplicaiton.util.provider.ResourcesProvider
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.listener.AdapterListener
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.listener.order.OrderListListener
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.listener.order.OrderMenuListListener
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.listener.restaurant.FoodMenuListListener
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.viewholder.ModelViewHolder

class OrderViewHolder(
    private val binding: ViewholderOrderBinding,
    viewModel: BaseViewModel,
    resourcesProvider: ResourcesProvider
) : ModelViewHolder<OrderModel>(binding, viewModel, resourcesProvider) {

    override fun reset() = Unit

    override fun bindData(model: OrderModel) {
        super.bindData(model)

        with(binding) {
            orderTitleText.text =
                resourcesProvider.getString(R.string.order_history_title, model.orderId)

            val foodMenuList = model.foodMenuList

            foodMenuList
                .groupBy { it.title }
                .entries.forEach { (title, menuList) ->
                    val orderDataStr =
                        orderContentText.text.toString() + "메뉴 : ${title} | 가격 : ${menuList.first().prices}원 X ${menuList.size}\n"

                    orderContentText.text = orderDataStr
                }
            orderContentText.text = orderContentText.text.trim()

            orderTotalPriceText.text =
                resourcesProvider.getString(
                    R.string.price,
                    foodMenuList.map { it.prices }.reduce { total, prices -> total + prices }
                )
        }
    }

    override fun bindViews(model: OrderModel, adapterListener: AdapterListener){
        if(adapterListener is OrderListListener){
            binding.root.setOnClickListener {
                adapterListener.writeRestaurantReview(model.orderId, model.restaurantTitle)
            }
        }
    }
}