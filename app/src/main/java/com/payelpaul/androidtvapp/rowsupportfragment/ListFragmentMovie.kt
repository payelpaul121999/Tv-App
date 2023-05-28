package com.payelpaul.androidtvapp.rowsupportfragment

import android.os.Bundle
import android.view.View
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.*
import com.payelpaul.androidtvapp.model.CastResponse
import com.payelpaul.androidtvapp.presenter.ItemPresenter
import com.payelpaul.androidtvapp.model.Movie
import com.payelpaul.androidtvapp.model.MovieList
import com.payelpaul.androidtvapp.presenter.CastPresenter

class ListFragmentMovie : RowsSupportFragment() {
    private var itemSelectedListener: ((Movie) -> Unit)? = null
    private var itemClickListener: ((Movie) -> Unit)? = null
    private var itemIndexSelectedListener: ((Int) -> Unit)? = null
    private val listRowPresenter = object : ListRowPresenter(FocusHighlight.ZOOM_FACTOR_MEDIUM) {
        override fun isUsingDefaultListSelectEffect(): Boolean {
            return false
        }
    }.apply {
        shadowEnabled = false
    }

    private var rootAdapter: ArrayObjectAdapter = ArrayObjectAdapter(listRowPresenter)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = rootAdapter
        onItemViewSelectedListener = ItemViewSelectedListener()
        onItemViewClickedListener = ItemViewClickListener()
    }

    fun bindData(dataList: MovieList) {


        val arrayObjectAdapter = ArrayObjectAdapter(ItemPresenter())

        dataList.results.forEach {
            arrayObjectAdapter.add(it)
        }

        val headerItem = HeaderItem("New Popular Movie")
        val listRow = ListRow( headerItem,arrayObjectAdapter)
        rootAdapter.add(listRow)

    }

    fun bindCastData(list: List<CastResponse.Cast>) {
        val arrayObjectAdapter = ArrayObjectAdapter(CastPresenter())

        list.forEach { content ->
            arrayObjectAdapter.add(content)
        }

        val headerItem = HeaderItem("Cast & Crew")
        val listRow = ListRow(headerItem, arrayObjectAdapter)
        rootAdapter.add(listRow)
    }

    fun setOnContentSelectedListener(listener: (Movie) -> Unit) {
        this.itemSelectedListener = listener
    }

    fun setOnItemClickListener(listener: (Movie) -> Unit) {
        this.itemClickListener = listener
    }
    fun getIndexOfLastItemPosition(listener : (Int) -> Unit){
        this.itemIndexSelectedListener = listener
    }

    inner class ItemViewSelectedListener : OnItemViewSelectedListener {
        override fun onItemSelected(
            itemViewHolder: Presenter.ViewHolder?,
            item: Any?,
            rowViewHolder: RowPresenter.ViewHolder?,
            row: Row?
        ) {
            if (item is Movie) {
                itemSelectedListener?.invoke(item)
                val indexOfItem = ((row as ListRow).adapter as ArrayObjectAdapter).indexOf(item)
                itemIndexSelectedListener?.invoke(indexOfItem)
            }

        }
    }

    inner class ItemViewClickListener : OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder?,
            item: Any?,
            rowViewHolder: RowPresenter.ViewHolder?,
            row: Row?
        ) {
            if (item is Movie) {
                itemClickListener?.invoke(item)

            }
        }

    }

    fun requestFocusOfData(): View {
        val view = view
        view?.requestFocus()
        return view!!
    }

}
