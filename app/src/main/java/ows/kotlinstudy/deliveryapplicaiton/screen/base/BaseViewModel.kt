package ows.kotlinstudy.deliveryapplicaiton.screen.base

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected var stateBundle: Bundle? = null

    open fun fecthData(): Job = viewModelScope.launch { }

    open fun storeState(stateBundle: Bundle) {
        this.stateBundle = stateBundle
    }
}