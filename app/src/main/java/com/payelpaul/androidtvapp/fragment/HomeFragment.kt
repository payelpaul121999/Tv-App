package com.payelpaul.androidtvapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.payelpaul.androidtvapp.MainViewModel
import com.payelpaul.androidtvapp.R
import com.payelpaul.androidtvapp.api.NetworkResult
import com.payelpaul.androidtvapp.databinding.FragmentHomeBinding
import com.payelpaul.androidtvapp.rowsupportfragment.ListFragmentMovie
import com.payelpaul.androidtvapp.model.Movie
import com.payelpaul.androidtvapp.model.MovieList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var indexPage: Int = 10
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)


        bindObserver(indexPage)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }


    private fun bindObserver(id:Int) {
        viewModel.movieLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                is NetworkResult.Success -> {
                    val data = it.data
                    initData(it.data)
                    //Log.d("JAPAN",data?.results?.size.toString())
                }
              is NetworkResult.Error -> {
                  Log.d("JAPAN",it.toString())
              }
                else -> {
                    Log.d("JAPAN",it.toString())
                }
            }
        })
        viewModel.getAllMovieList(id)
    }
    private fun updateBanner(dataList: Movie) {
        binding.title.text = dataList.title
        binding.description.text = dataList.overview
        val url = "https://www.themoviedb.org/t/p/w780" + dataList.backdrop_path
        Glide.with(this).load(url).into(binding.imgBanner)
    }
    private fun initData(dataList: MovieList?) {
//        Log.d("JAP",dataList!!.results.size.toString())
        val  listFragment = ListFragmentMovie()
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.list_fragment, listFragment)
        transaction.commit()
        if (dataList != null) {
            listFragment.bindData(dataList)
        }
        listFragment.setOnContentSelectedListener {
            updateBanner(it)
        }
        listFragment.setOnItemClickListener {
            val intent  = Intent(requireContext(), DetailFragment::class.java)
            intent.putExtra("ID", it.id)
            startActivity(intent)
        }
        listFragment.getIndexOfLastItemPosition {
            if (it == dataList!!.results.size - 1){
                //viewModel.getAllMovieList(indexPage++)
              Log.d("JAP","last index  ")
            }else{
                Log.d("JAP","last index not")
            }
        }
       // listFragment.requestFocusOfData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




    

}