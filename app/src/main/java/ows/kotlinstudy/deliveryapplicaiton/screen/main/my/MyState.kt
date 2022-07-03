package ows.kotlinstudy.deliveryapplicaiton.screen.main.my

import android.net.Uri
import androidx.annotation.StringRes
import ows.kotlinstudy.deliveryapplicaiton.data.entity.OrderEntity

sealed class MyState {

    object UnInitialized : MyState()

    object Loading: MyState()

    data class Login(
        val idToken: String
    ): MyState()

    sealed class Success : MyState(){

        data class Registered(
            val userName: String,
            val profileImageUri: Uri?,
            val orderList : List<OrderEntity>
        ): Success()

        object NotRegistered: Success()
    }

    data class Error(
        @StringRes val messageId: Int,
        val e: Throwable
    ): MyState()

}