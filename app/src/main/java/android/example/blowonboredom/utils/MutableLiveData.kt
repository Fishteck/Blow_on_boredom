package android.example.blowonboredom.utils

import androidx.lifecycle.MutableLiveData

fun <T : Any?> MutableLiveData<T>.setDefault(initialValue : T) = apply { value = initialValue }
fun <T : Any?> MutableLiveData<T>.set(newValue : T) = apply { value = newValue }