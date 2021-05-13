package android.example.blowonboredom.ui.viewmodel

import android.example.blowonboredom.data.model.RandomActivity
import android.example.blowonboredom.data.repository.RandomActivityRepository
import android.example.blowonboredom.ui.viewmodel.states.SuccessStates
import android.example.blowonboredom.utils.set
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import javax.inject.Inject

@HiltViewModel
class RandomActivityVM @Inject constructor(private val repository: RandomActivityRepository<RandomActivity>) :
    ViewModel() {


    private val compositeDisposable = CompositeDisposable()

    private val _randomActivitiesState = MutableLiveData<SuccessStates<RandomActivity>>()
    val randomActivitiesState: LiveData<SuccessStates<RandomActivity>> = _randomActivitiesState


    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun getRandom() {

        _randomActivitiesState.set(SuccessStates.Loading)

        compositeDisposable.add(
            repository.getRandomActivity().subscribeWith(
                object : DisposableSingleObserver<RandomActivity>() {

                    override fun onSuccess(t: RandomActivity) {
                        _randomActivitiesState.postValue(SuccessStates.Success(t))
                    }

                    override fun onError(e: Throwable?) {
                        _randomActivitiesState.postValue(SuccessStates.Error(e?.message.toString()))
                    }

                }
            )
        )
    }


}