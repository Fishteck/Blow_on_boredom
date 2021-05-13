package android.example.blowonboredom.ui.viewmodel.states

sealed class CompletableStates<out T>(
    val message: String? = null
) {
    class Complete(message : String) : CompletableStates<String>(message)
    class Error(message : String) : CompletableStates<String>(message)
}