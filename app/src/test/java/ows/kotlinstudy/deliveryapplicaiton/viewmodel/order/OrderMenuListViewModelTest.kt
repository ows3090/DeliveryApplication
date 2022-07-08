package ows.kotlinstudy.deliveryapplicaiton.viewmodel.order

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import org.mockito.Mock
import org.mockito.Mockito
import ows.kotlinstudy.deliveryapplicaiton.data.entity.OrderEntity
import ows.kotlinstudy.deliveryapplicaiton.data.entity.RestaurantFoodEntity
import ows.kotlinstudy.deliveryapplicaiton.data.repository.order.DefaultOrderRepository
import ows.kotlinstudy.deliveryapplicaiton.data.repository.order.OrderRepository
import ows.kotlinstudy.deliveryapplicaiton.data.repository.restaurant.food.RestaurantFoodRepository
import ows.kotlinstudy.deliveryapplicaiton.model.CellType
import ows.kotlinstudy.deliveryapplicaiton.model.restaurant.food.FoodModel
import ows.kotlinstudy.deliveryapplicaiton.screen.order.OrderMenuListViewModel
import ows.kotlinstudy.deliveryapplicaiton.screen.order.OrderMenuState
import ows.kotlinstudy.deliveryapplicaiton.viewmodel.ViewModelTest

@ExperimentalCoroutinesApi
class OrderMenuListViewModelTest : ViewModelTest() {

    @Mock
    lateinit var firebaseAuth: FirebaseAuth

    @Mock
    lateinit var firebaseUser: FirebaseUser

    private val orderMenuListViewModel by inject<OrderMenuListViewModel> {
        parametersOf(firebaseAuth)
    }

    private val restaurantFoodRepository by inject<RestaurantFoodRepository>()

    private val orderRepository by inject<OrderRepository>()

    private val restaurantId = 0L

    private val restaurantTitle = "식당명"

    /**
     * 1. 장바구니에 메뉴 담기
     * 2. 장바구니 메뉴 리스트 확인
     * 3. 장바구니 목록으로 주문
     */

    @Test
    fun `insert food menus in basket`() = runBlockingTest {
        (0 until 10).forEach {
            restaurantFoodRepository.insertFoodMenuInBasket(
                RestaurantFoodEntity(
                    id = it.toString(),
                    title = "메뉴 ${it}",
                    description = "소개 $it",
                    prices = it,
                    imageUrl = "",
                    restaurantId = restaurantId,
                    restaurantTitle = restaurantTitle
                )
            )
        }
        assert(restaurantFoodRepository.getAllFoodMenuListInBasket().size == 10)
    }

    @Test
    fun `test load order menu list`() = runBlockingTest {
        `insert food menus in basket`()
        val testObservable = orderMenuListViewModel.orderMenuStateLiveData.test()
        orderMenuListViewModel.fetchData()

        testObservable.assertValueSequence(
            listOf(
                OrderMenuState.Uninitialized,
                OrderMenuState.Loading,
                OrderMenuState.Success(
                    restaurantFoodModelList = restaurantFoodRepository.getAllFoodMenuListInBasket()
                        .map {
                            FoodModel(
                                id = it.hashCode().toLong(),
                                type = CellType.ORDER_FOOD_CELL,
                                title = it.title,
                                description = it.description,
                                prices = it.prices,
                                imageUrl = it.imageUrl,
                                restaurantId = it.restaurantId,
                                foodId = it.id,
                                restaurantTitle = it.restaurantTitle
                            )
                        }
                )
            )
        )
    }

    @Test
    fun `test do order menu list`() = runBlockingTest {
        `insert food menus in basket`()

        val userId = "asdf"
        Mockito.`when`(firebaseAuth.currentUser).then { firebaseUser }
        Mockito.`when`(firebaseUser.uid).then { userId }

        val testObservable = orderMenuListViewModel.orderMenuStateLiveData.test()
        orderMenuListViewModel.fetchData()

        val menuListInBasket = restaurantFoodRepository.getAllFoodMenuListInBasket().map { it.copy() }

        val menuListInBasketModelList = menuListInBasket.map {
            FoodModel(
                id = it.hashCode().toLong(),
                type = CellType.ORDER_FOOD_CELL,
                title = it.title,
                description = it.description,
                prices = it.prices,
                imageUrl = it.imageUrl,
                restaurantId = it.restaurantId,
                foodId = it.id,
                restaurantTitle = it.restaurantTitle
            )
        }

        orderMenuListViewModel.orderMenu()

        testObservable.assertValueSequence(
            listOf(
                OrderMenuState.Uninitialized,
                OrderMenuState.Loading,
                OrderMenuState.Success(
                    restaurantFoodModelList = menuListInBasketModelList
                ),
                OrderMenuState.Order
            )
        )

        assert(orderRepository.getAllOrderMenus(userId) is DefaultOrderRepository.Result.Success<*>)
        val result = (orderRepository.getAllOrderMenus(userId) as DefaultOrderRepository.Result.Success<*>).data

        assert(
            result?.equals(
                listOf(
                    OrderEntity(
                        id = 0.toString(),
                        userId = userId,
                        restaurantId = restaurantId,
                        foodMenuList = menuListInBasket,
                        restaurantTitle = restaurantTitle
                    )
                )
            ) ?: false
        )
    }
}