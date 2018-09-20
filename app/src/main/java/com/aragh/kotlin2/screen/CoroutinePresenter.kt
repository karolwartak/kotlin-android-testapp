package com.aragh.kotlin2.screen

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext


open class CoroutinePresenter(private val coroutineContext: CoroutineContext = UI) {

  protected fun <T> runInCoroutine(operation: () -> Deferred<T>,
                                   onSuccess: (T) -> Unit = {},
                                   onFailure: (Exception) -> Unit = {}) {
    launch(this@CoroutinePresenter.coroutineContext) {
      try {
        val result = operation.invoke().await()
        onSuccess(result)
      } catch (e: Exception) {
        onFailure(e)
      }
    }
  }

}