package ows.kotlinstudy.deliveryapplicaiton.util.event

import kotlinx.coroutines.flow.MutableSharedFlow
import ows.kotlinstudy.deliveryapplicaiton.screen.main.MainTabMenu

class MenuChangeEventBus {

    val mainTabMenuFlow = MutableSharedFlow<MainTabMenu>()

    suspend fun changeMenu(menu: MainTabMenu){
        mainTabMenuFlow.emit(menu)
    }
}