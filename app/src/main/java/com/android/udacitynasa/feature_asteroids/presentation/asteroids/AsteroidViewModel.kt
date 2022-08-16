package com.android.udacitynasa.feature_asteroids.presentation.asteroids

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.udacitynasa.feature_asteroids.domain.use_case.GetAsteroidsUseCase
import com.android.udacitynasa.feature_asteroids.domain.use_case.GetPictureOfTheDayUseCase
import com.android.udacitynasa.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.N)
@HiltViewModel
class AsteroidViewModel @Inject constructor(
    private val getPictureOfTheDayUseCase: GetPictureOfTheDayUseCase,
    private val getAsteroidsUseCase: GetAsteroidsUseCase,
) : ViewModel() {

    private var _state = MutableStateFlow(AsteroidState())
    val state: StateFlow<AsteroidState> get() = _state.asStateFlow()

    private var _errorsChannel = Channel<String>()
    val errorsChannel get() = _errorsChannel.receiveAsFlow()

    init {
        onPicture(AsteroidEvent.PictureOfTheDay)
        onAsteroid()
    }

    fun onPicture(event: AsteroidEvent) {
        when (event) {
            is AsteroidEvent.PictureOfTheDay -> {
                viewModelScope.launch {
                    getPictureOfTheDayUseCase.invoke().onEach { Resource ->
                        when (Resource) {
                            is Resource.Loading -> {
                                _state.value = state.value.copy(
                                    isLoading = true,
                                    pictureOfDay = Resource.data,
                                )
                            }

                            is Resource.Success -> {
                                _state.value =
                                    state.value.copy(
                                        isLoading = false,
                                        pictureOfDay = Resource.data,
                                    )
                            }

                            is Resource.Error -> {
                                _state.value =
                                    state.value.copy(
                                        isLoading = false,
                                        pictureOfDay = Resource.data,
                                    )
                                _errorsChannel.send("${Resource.message}")
                            }
                        }
                    }.launchIn(viewModelScope)
                }
            }
        }
    }


    fun onAsteroid(orderEvent: OrderEvent = OrderEvent.SavedOrder){
        getAsteroidsUseCase.invoke(orderEvent).onEach { Resource ->
            when(Resource){
                is Resource.Loading -> {
                    _state.value = state.value.copy(
                        isLoading = true,
                        asteroids = Resource.data ?: emptyList()
                    )
                }
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        asteroids = Resource.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        asteroids = Resource.data ?: emptyList()
                    )
                    _errorsChannel.send("${Resource.message}")
                }
            }
        }.launchIn(viewModelScope)
    }

}