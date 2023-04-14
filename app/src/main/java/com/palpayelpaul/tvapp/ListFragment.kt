package com.palpayelpaul.tvapp

import android.os.Bundle
import android.view.View
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.*


class ListFragment : RowsSupportFragment() {

    private var itemSelectedListener: ((DataModel.Result.Detail) -> Unit)? = null
    private var rootAdapter: ArrayObjectAdapter =
        ArrayObjectAdapter(ListRowPresenter(FocusHighlight.ZOOM_FACTOR_MEDIUM))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = rootAdapter

        onItemViewSelectedListener = ItemViewSelectedListener()
    }

    fun bindData(dataList: DataModel) {
        val arrayObjectAdapter = ArrayObjectAdapter(ItemPresenter())
        dataList.result.forEachIndexed { index, result ->
            arrayObjectAdapter.clear()
            arrayObjectAdapter.addAll(0,result.details)

            val headerItem = HeaderItem(result.title)
            val listRow = ListRow(headerItem, arrayObjectAdapter)
            rootAdapter.add(listRow)

        }

    }

    fun setOnContentSelectedListener(listener : (DataModel.Result.Detail) -> Unit){
        this.itemSelectedListener = listener
    }

    inner class ItemViewSelectedListener : OnItemViewSelectedListener{
        override fun onItemSelected(
            itemViewHolder: Presenter.ViewHolder?,
            item: Any?,
            rowViewHolder: RowPresenter.ViewHolder?,
            row: Row?
        ) {
            if (item is DataModel.Result.Detail){
                itemSelectedListener?.invoke(item)
            }

        }

    }


}