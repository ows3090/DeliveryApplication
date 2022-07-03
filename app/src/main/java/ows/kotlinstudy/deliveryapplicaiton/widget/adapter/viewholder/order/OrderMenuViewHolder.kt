package ows.kotlinstudy.deliveryapplicaiton.widget.adapter.viewholder.order

import com.bumptech.glide.load.resource.bitmap.CenterCrop
import ows.kotlinstudy.deliveryapplicaiton.R
import ows.kotlinstudy.deliveryapplicaiton.databinding.ViewholderFoodMenuBinding
import ows.kotlinstudy.deliveryapplicaiton.databinding.ViewholderOrderMenuBinding
import ows.kotlinstudy.deliveryapplicaiton.extensions.clear
import ows.kotlinstudy.deliveryapplicaiton.extensions.load
import ows.kotlinstudy.deliveryapplicaiton.model.restaurant.food.FoodModel
import ows.kotlinstudy.deliveryapplicaiton.screen.base.BaseViewModel
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.detail.menu.RestaurantMenuListViewModel
import ows.kotlinstudy.deliveryapplicaiton.util.provider.ResourcesProvider
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.listener.AdapterListener
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.listener.order.OrderMenuListListener
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.listener.restaurant.FoodMenuListListener
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.viewholder.ModelViewHolder

class OrderMenuViewHolder(
    private val binding: ViewholderOrderMenuBinding,
    viewModel: BaseViewModel,
    resourcesProvider: ResourcesProvider
) : ModelViewHolder<FoodModel>(binding, viewModel, resourcesProvider) {

    override fun reset() = with(binding) {
        foodImage.clear()
    }

    override fun bindData(model: FoodModel) {
        super.bindData(model)

        with(binding) {
            foodImage.load(model.imageUrl, 24f, CenterCrop())
            foodTitleText.text = model.title
            foodDescriptionText.text = model.description
            priceText.text = resourcesProvider.getString(R.string.price, model.prices)
        }
    }

    override fun bindViews(model: FoodModel, adapterListener: AdapterListener) {
        if(adapterListener is OrderMenuListListener){
            binding.removeButton.setOnClickListener {
                adapterListener.onRemoveItem(model)
            }
        }
    }
}