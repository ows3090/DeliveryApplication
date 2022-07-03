package ows.kotlinstudy.deliveryapplicaiton.screen.main.like

import androidx.core.view.isGone
import androidx.core.view.isVisible
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ows.kotlinstudy.deliveryapplicaiton.databinding.FragmentRestaurantLikeListBinding
import ows.kotlinstudy.deliveryapplicaiton.model.restaurant.RestaurantModel
import ows.kotlinstudy.deliveryapplicaiton.screen.base.BaseFragment
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.detail.RestaurantDetailActivity
import ows.kotlinstudy.deliveryapplicaiton.util.provider.ResourcesProvider
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.ModelRecyclerAdapter
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.listener.restaurant.RestaurantLikeListListener

class RestaurantLikeListFragment :
    BaseFragment<RestaurantLikeListViewModel, FragmentRestaurantLikeListBinding>() {

    override val viewModel by viewModel<RestaurantLikeListViewModel>()

    override fun getViewBinding(): FragmentRestaurantLikeListBinding =
        FragmentRestaurantLikeListBinding.inflate(layoutInflater)

    private val resouceProvider by inject<ResourcesProvider>()

    private val adapter by lazy {
        ModelRecyclerAdapter<RestaurantModel, RestaurantLikeListViewModel>(
            listOf(),
            viewModel,
            resouceProvider,
            object : RestaurantLikeListListener {
                override fun onDislikeItem(model: RestaurantModel) {
                    viewModel.dislikeRestaurant(model.toEntity())
                }

                override fun onClickItem(model: RestaurantModel) {
                    startActivity(
                        RestaurantDetailActivity.newIntent(
                            requireContext(),
                            model.toEntity()
                        )
                    )
                }
            }
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.fecthData()
    }

    override fun initViews() {
        binding.recyclerView.adapter = adapter
    }

    override fun observeData() = viewModel.restaurantListLiveData.observe(viewLifecycleOwner) {
        checkListEmpty(it)
    }

    private fun checkListEmpty(restaurantList: List<RestaurantModel>) {
        val isEmpty = restaurantList.isEmpty()
        binding.recyclerView.isGone = isEmpty
        binding.emptyResultTextView.isVisible = isEmpty
        if (isEmpty.not()) {
            adapter.submitList(restaurantList)
        }
    }

    companion object {
        const val TAG = "RestaurantLikeListFragment"

        fun newInstance() = RestaurantLikeListFragment()
    }
}