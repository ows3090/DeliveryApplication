package ows.kotlinstudy.deliveryapplicaiton.widget.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ows.kotlinstudy.deliveryapplicaiton.model.CellType
import ows.kotlinstudy.deliveryapplicaiton.model.Model
import ows.kotlinstudy.deliveryapplicaiton.screen.base.BaseViewModel
import ows.kotlinstudy.deliveryapplicaiton.util.mapper.ModelViewHolderMapper
import ows.kotlinstudy.deliveryapplicaiton.util.provider.ResourcesProvider
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.listener.AdapterListener
import ows.kotlinstudy.deliveryapplicaiton.widget.adapter.viewholder.ModelViewHolder

class ModelRecyclerAdapter<M : Model, VM : BaseViewModel>(
    private var modelList: List<Model>,
    private val viewModel: VM,
    private val resourceProvider: ResourcesProvider,
    private val adapterListener: AdapterListener
) : ListAdapter<Model, ModelViewHolder<M>>(Model.DIFF_CALLBACK) {

    override fun getItemCount(): Int = modelList.size

    override fun getItemViewType(position: Int): Int = modelList[position].type.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder<M> {
        return ModelViewHolderMapper.map(
            parent,
            CellType.values()[viewType],
            viewModel,
            resourceProvider
        )
    }

    /**
     * UNCHCKED_CAST : 자식클래스로 변환 시 Cast Lint 에러 방지
     */
    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: ModelViewHolder<M>, position: Int) {
        with(holder) {
            bindData(modelList[position] as M)
            bindViews(modelList[position] as M, adapterListener)
        }
    }


    /**
     * list가 null이면 무시
     */
    override fun submitList(list: MutableList<Model>?) {
        list?.let { modelList = it }
        super.submitList(list)
    }
}