package ows.kotlinstudy.deliveryapplicaiton.util.mapper

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import ows.kotlinstudy.deliveryapplicaiton.databinding.ViewholderEmptyBinding
import ows.kotlinstudy.deliveryapplicaiton.model.CellType
import ows.kotlinstudy.deliveryapplicaiton.model.Model
import ows.kotlinstudy.deliveryapplicaiton.screen.base.BaseViewModel
import ows.kotlinstudy.deliveryapplicaiton.util.provider.ResourcesProvider
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.viewholder.EmptyViewHolder
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.viewholder.ModelViewHolder

object ModelViewHolderMapper {

    @Suppress("UNCHECKED_CAST")
    fun <M : Model> map(
        parent: ViewGroup,
        type: CellType,
        viewModel: BaseViewModel,
        resourcesProvider: ResourcesProvider
    ): ModelViewHolder<M> {
        val inflater = LayoutInflater.from(parent.context)
        val viewHolder = when (type) {
            CellType.EMPTY_CELL -> EmptyViewHolder(
                ViewholderEmptyBinding.inflate(inflater, parent, false),
                viewModel,
                resourcesProvider
            )
        }
        return viewHolder as ModelViewHolder<M>
    }
}