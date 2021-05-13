package android.example.blowonboredom.utils.observers

import android.example.blowonboredom.ui.viewmodel.states.CompletableStates
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.observers.DisposableCompletableObserver

class DisposableCompletableObserverImpl(
    private val mutableLiveData: MutableLiveData<CompletableStates<String>>,
    private val onCompleteMessage: String
) :
    DisposableCompletableObserver() {
    override fun onComplete() {
        mutableLiveData.postValue( CompletableStates.Complete(onCompleteMessage) )
    }

    override fun onError(e: Throwable?) {
        mutableLiveData.postValue( CompletableStates.Error(e?.message.toString()) )
    }
}


