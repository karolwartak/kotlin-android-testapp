package com.aragh.kotlin2.screen

import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext


open class CoroutinePresenter(override val coroutineContext: CoroutineContext) : CoroutineScope {

  protected fun <T> runInCoroutine(operation: () -> Deferred<T>,
                                   onSuccess: (T) -> Unit = {},
                                   onFailure: (Exception) -> Unit = {}) {
    launch(coroutineContext) {
      try {
        val result = operation.invoke().await()
        onSuccess(result)
      } catch (e: Exception) {
        onFailure(e)
      }
    }
  }

}