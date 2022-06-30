package ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.detail.review

import androidx.core.os.bundleOf
import org.koin.android.viewmodel.ext.android.viewModel
import ows.kotlinstudy.deliveryapplicaiton.data.entity.RestaurantFoodEntity
import ows.kotlinstudy.deliveryapplicaiton.databinding.FragmentListBinding
import ows.kotlinstudy.deliveryapplicaiton.screen.base.BaseFragment

class RestaurantReviewListFragment :
    BaseFragment<RestaurantReviewListViewModel, FragmentListBinding>() {

    override val viewModel by viewModel<RestaurantReviewListViewModel>()

    override fun getViewBinding(): FragmentListBinding = FragmentListBinding.inflate(layoutInflater)

    override fun observeData() {

    }

    companion object {
        const val RESTAURANT_ID_KEY = "restaurantId"

        fun newInstance(
            restaurantId: Long
        ): RestaurantReviewListFragment {
            val bundle = bundleOf(
                RESTAURANT_ID_KEY to restaurantId
            )
            return RestaurantReviewListFragment().apply {
                arguments = bundle
            }
        }
    }
}