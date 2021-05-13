package android.example.blowonboredom.ui.viewmodel

import android.example.blowonboredom.data.model.RandomActivity
import android.example.blowonboredom.data.repository.FavoriteActivitiesRepository
import android.example.blowonboredom.ui.viewmodel.states.CompletableStates
import android.example.blowonboredom.ui.viewmodel.states.SuccessStates
import android.example.blowonboredom.utils.observers.DisposableCompletableObserverImpl
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import javax.inject.Inject

@HiltViewModel
class FavoriteActivitiesVM @Inject constructor(
    private val repository : FavoriteActivitiesRepository<RandomActivity, String>
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _favoriteSuccessState = MutableLiveData<SuccessStates<List<RandomActivity>>>()
    val favoriteActivitiesState : LiveData<SuccessStates<List<RandomActivity>>> = _favoriteSuccessState

    private val _favoriteCompletableStates = MutableLiveData<CompletableStates<String>>()
    val favoriteCompletableStates : LiveData<CompletableStates<String>> = _favoriteCompletableStates

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun addToFavoriteActivities(item : RandomActivity) {
        compositeDisposable.add(
            repository.addToFavoriteActivities(item)
                .subscribeWith(
                    DisposableCompletableObserverImpl(_favoriteCompletableStates, "Added to favorites")
                )
        )
    }

    fun deleteFromFavoriteActivities(id : String) {
        compositeDisposable.add(
            repository.deleteFromFavoriteActivities(id).subscribeWith(
                DisposableCompletableObserverImpl(_favoriteCompletableStates, "Deleted from favorites")
            )
        )
    }

    fun getAllFavoritesActivities() {
        compositeDisposable.add(
            repository.getAllFavoriteActivities().subscribeWith(
                object  : DisposableSingleObserver<List<RandomActivity>>() {

                    override fun onError(e: Throwable?) {
                        _favoriteSuccessState.postValue(SuccessStates.Error(""))
                    }

                    override fun onSuccess(t: List<RandomActivity>) {
                        _favoriteSuccessState.postValue(SuccessStates.Success(t))
                    }

                }
            )
        )
    }
}