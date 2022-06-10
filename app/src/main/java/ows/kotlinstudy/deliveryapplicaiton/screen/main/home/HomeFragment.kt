package ows.kotlinstudy.deliveryapplicaiton.screen.main.home

import org.koin.android.viewmodel.ext.android.viewModel
import ows.kotlinstudy.deliveryapplicaiton.databinding.FragmentHomeBinding
import ows.kotlinstudy.deliveryapplicaiton.screen.base.BaseFragment

/**
 * android tint vs app tint => app : AppCompat 라이브러리에 포함, 즉 API 별 호환성으로 인해 app tint로 설정해야 함.
 */
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    override val viewModel by viewModel<HomeViewModel>()

    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    override fun observeData() {

    }

    companion object {

        fun newInstance() = HomeFragment()

        const val TAG = "HomeFragment"
    }

}