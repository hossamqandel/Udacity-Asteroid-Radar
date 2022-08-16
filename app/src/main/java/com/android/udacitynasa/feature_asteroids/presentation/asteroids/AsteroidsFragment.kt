package com.android.udacitynasa.feature_asteroids.presentation.asteroids

import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.android.udacitynasa.R
import com.android.udacitynasa.databinding.FragmentAsteroidsBinding
import com.android.udacitynasa.feature_asteroids.domain.model.Asteroid
import com.android.udacitynasa.feature_asteroids.domain.model.Picture
import com.android.udacitynasa.utils.Const.YOUTUBE
import com.android.udacitynasa.utils.IClickHelper
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
@AndroidEntryPoint
class AsteroidsFragment : Fragment() {

    private var _binding: FragmentAsteroidsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AsteroidViewModel by viewModels()
    private val mAdapter by lazy { AsteroidAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAsteroidsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClicks()
        collectFlow()
        onRefresh()
        onError()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.filterByWeek -> viewModel.onAsteroid(OrderEvent.WeekOrder)
            R.id.filterByDay -> viewModel.onAsteroid(OrderEvent.TodayOrder)
            R.id.filterBySaved -> viewModel.onAsteroid(OrderEvent.SavedOrder)
        }
        return super.onOptionsItemSelected(item)
    }



    private fun onRefresh(){
        binding.swipeRefresh.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE)
        binding.swipeRefresh.setOnRefreshListener {
            // Trying again by sending a new request to the server
            viewModel.onPicture(AsteroidEvent.PictureOfTheDay)
            viewModel.onAsteroid(OrderEvent.SavedOrder)

            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun onClicks() {
        binding.apply {
            mAdapter.setOnItemClick(object : IClickHelper {
                override fun onItemClick(asteroid: Asteroid) {
                    val direction = AsteroidsFragmentDirections.actionAsteroidsFragmentToAsteroidDetailsFragment(
                        asteroid.closeApproachDate,
                        asteroid.absoluteMagnitude.toFloat(),
                        asteroid.estimatedDiameter.toFloat(),
                        asteroid.relativeVelocity.toFloat(),
                        asteroid.distanceFromEarth.toFloat(),
                        asteroid.id, asteroid.isPotentiallyHazardous
                    )
                    findNavController().navigate(direction)
                }
            })
        }
    }

    private fun collectFlow() {
        binding.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.state.collectLatest { asteroidState ->
                        asteroidState.pictureOfDay?.let {
                            setPictureOfTheDay(it)
                        }
                        setProgressVisibility(asteroidState.isLoading)
                        setupRecyclerViewEnvironment(asteroidState.asteroids)
                    }
                }
            }
        }
    }


    private fun setupRecyclerViewEnvironment(asteroids: List<Asteroid>) {
        mAdapter.setAsteroids(asteroids ?: emptyList())
        binding.rvAsteroids.adapter = mAdapter
        Log.e(TAG, "initRecycler: $asteroids")
    }

    private fun setPictureOfTheDay(picture: Picture) {
        binding.apply {
            if (!picture.url.contains(YOUTUBE)) {

                try {
                    ivImageOfDay.contentDescription = picture.title
                    println("${picture.title}")
                    Picasso.get()
                        .load(picture.url)
                        .error(R.drawable.nasa_egypt)
                        .into(ivImageOfDay)
                } catch (e: Exception) {
                    Log.e(TAG, "setPictureOfTheDay: ${e.localizedMessage ?: e.toString()}")
                }
            } else {
                ivImageOfDay.contentDescription = picture.title
            }
        }
    }

    private fun setProgressVisibility(isVisible: Boolean){
        binding.progress.isVisible = isVisible
    }

    private fun onError(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.errorsChannel.collectLatest { errorMessage ->
                    Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }


}