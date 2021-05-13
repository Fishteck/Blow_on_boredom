package android.example.blowonboredom.ui.viewmodel.states

sealed class SuccessStates<out T> (
    val data: T? = null,
    val message : String? = null
        ) {
    class Success<out T>( data : T) : SuccessStates<T>(data)
    class Error<T>(message : String, data: T? = null) : SuccessStates<T>(data, message)
    object Loading: SuccessStates<Nothing>()
}