package ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.detail.review

import android.widget.Toast
import androidx.core.os.bundleOf
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ows.kotlinstudy.deliveryapplicaiton.data.entity.RestaurantFoodEntity
import ows.kotlinstudy.deliveryapplicaiton.databinding.FragmentListBinding
import ows.kotlinstudy.deliveryapplicaiton.screen.base.BaseFragment

class RestaurantReviewListFragment :
    BaseFragment<RestaurantReviewListViewModel, FragmentListBinding>() {

    override val viewModel by viewModel<RestaurantReviewListViewModel>() {
        parametersOf(
            arguments?.getString(RESTAURANT_TITLE_KEY)
        )
    }

    override fun getViewBinding(): FragmentListBinding = FragmentListBinding.inflate(layoutInflater)

    override fun observeData() = viewModel.reviewStateLiveData.observe(viewLifecycleOwner) {
        when (it) {
            is RestaurantReviewState.Success -> {
                handleSuccess(it)
            }
        }
    }

    private fun handleSuccess(reviewState: RestaurantReviewState.Success) {
        Toast.makeText(requireContext(), reviewState.reviewList.toString(), Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val RESTAURANT_TITLE_KEY = "restaurantTitle"

        fun newInstance(
            restaurantTitle: String
        ): RestaurantReviewListFragment {
            val bundle = bundleOf(
                RESTAURANT_TITLE_KEY to restaurantTitle
            )
            return RestaurantReviewListFragment().apply {
                arguments = bundle
            }
        }
    }
}