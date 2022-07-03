package ows.kotlinstudy.deliveryapplicaiton.screen.main.my

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ows.kotlinstudy.deliveryapplicaiton.data.preference.AppPreferenceManager
import ows.kotlinstudy.deliveryapplicaiton.screen.base.BaseViewModel

class MyViewModel(
    private val appPreferenceManager: AppPreferenceManager
) : BaseViewModel() {

    val myStateLiveData = MutableLiveData<MyState>(MyState.UnInitialized)

    override fun fecthData(): Job = viewModelScope.launch {
        myStateLiveData.value = MyState.Loading
        appPreferenceManager.getIdToken()?.let {
            myStateLiveData.value = MyState.Login(it)
        } ?: kotlin.run {
            myStateLiveData.value = MyState.Success.NotRegistered
        }
    }

    fun saveToken(idToken: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            appPreferenceManager.putIdToken(idToken)
            fecthData()
        }
    }

    fun setUserInfo(firebaseUser: FirebaseUser?) = viewModelScope.launch{
        firebaseUser?.let { user ->
            myStateLiveData.value = MyState.Success.Registered(
                userName = user.displayName?: "익명",
                profileImageUri = user.photoUrl
            )
        }?: kotlin.run {
            myStateLiveData.value = MyState.Success.NotRegistered
        }
    }

    fun signOut() = viewModelScope.launch {
        withContext(Dispatchers.IO){
            appPreferenceManager.removeIdToken()
        }
        fecthData()
    }
}