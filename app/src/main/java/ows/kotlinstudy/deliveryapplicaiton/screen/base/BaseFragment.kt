package ows.kotlinstudy.deliveryapplicaiton.screen.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.Job

abstract class BaseFragment<VM : BaseViewModel, VB : ViewBinding> : Fragment() {

    abstract val viewModel: VM

    protected val binding: VB
        get() = _binding!!

    private var _binding: VB? = null

    abstract fun getViewBinding(): VB

    private lateinit var fetchJob: Job

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initState()
    }

    open fun initState() {
        arguments?.let {
            viewModel.storeState(it)
        }
        initViews()
        fetchJob = viewModel.fecthData()
        observeData()
    }

    open fun initViews() = Unit

    abstract fun observeData()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        if (fetchJob.isActive) {
            fetchJob.cancel()
        }
        super.onDestroy()
    }
}