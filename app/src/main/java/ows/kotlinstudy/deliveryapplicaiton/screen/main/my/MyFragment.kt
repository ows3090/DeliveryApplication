package ows.kotlinstudy.deliveryapplicaiton.screen.main.my

import org.koin.android.viewmodel.ext.android.viewModel
import ows.kotlinstudy.deliveryapplicaiton.databinding.FragmentMyBinding
import ows.kotlinstudy.deliveryapplicaiton.screen.base.BaseFragment

class MyFragment : BaseFragment<MyViewModel, FragmentMyBinding>() {

    override val viewModel by viewModel<MyViewModel>()

    override fun getViewBinding(): FragmentMyBinding = FragmentMyBinding.inflate(layoutInflater)

    override fun observeData() {

    }

    companion object {
        fun newInstance() = MyFragment()

        const val TAG = "MyFragment"
    }

}