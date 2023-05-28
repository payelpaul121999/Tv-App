package com.payelpaul.androidtvapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.payelpaul.androidtvapp.MainViewModel
import com.payelpaul.androidtvapp.R
import com.payelpaul.androidtvapp.databinding.FragmentDetailBinding
import com.payelpaul.androidtvapp.rowsupportfragment.ListFragmentMovie
import com.payelpaul.androidtvapp.rowsupportfragment.PlaybackActivity
import com.payelpaul.androidtvapp.model.DetailResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : FragmentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private var _binding: FragmentDetailBinding?=null
    private val binding get() = _binding!!
    val castFragment = ListFragmentMovie()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        addFragment(castFragment)
        val profileName = intent.getIntExtra("ID",0)
        viewModel.getMovieDetails(profileName)
        viewModel.detailsLiveData.observe(this) {
            Log.d("####--",it.data?.title.toString())
            setData(it.data)
        }
        setContentView(binding.root)


        viewModel.createCastResponse(profileName)
        viewModel.castLiveData.observe(this){
            castFragment.bindCastData(it.data?.cast!!)
        }

        binding.play.setOnClickListener {
            val intent = Intent(this@DetailFragment, PlaybackActivity::class.java)
          //  intent.putExtra(DetailFragment.MOVIE, "22")
            startActivity(intent)
        }

    }
    private fun addFragment(castfragment: ListFragmentMovie) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.cast_fragment, castfragment)
        transaction.commit()
    }
    private fun setData(response: DetailResponse?) {

        binding.title.text = response?.title ?: ""
        binding.subtitle.text = getSubtitle(response)
        binding.description.text = response?.overview ?: ""

        val path = "https://www.themoviedb.org/t/p/w780" + (response?.backdrop_path ?: "")
        Glide.with(this)
            .load(path)
            .into(binding.imgBanner)

    }

    fun getSubtitle(response: DetailResponse?): String {
        val rating = if (response!!.adult) {
            "18+"
        } else {
            "13+"
        }

        val genres = response.genres.joinToString(
            prefix = " ",
            postfix = " • ",
            separator = " • "
        ) { it.name }

        val hours: Int = response.runtime / 60
        val min: Int = response.runtime % 60

        return rating + genres + hours + "h " + min + "m"

    }

    companion object {
        const val SHARED_ELEMENT_NAME = "hero"
        const val MOVIE = "Movie"
    }

}

