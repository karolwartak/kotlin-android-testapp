package com.aragh.kotlin2.screen

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import kotlin.coroutines.experimental.CoroutineContext


open class CoroutinePresenter(private val coroutineContext: CoroutineContext = UI) {

  protected fun <T> runInCoroutine(operation: () -> T,
                                   onSuccess: (T) -> Unit = {},
                                   onFailure: (Exception) -> Unit = {}) {
    launch(this@CoroutinePresenter.coroutineContext) {
      try {
        val result = withContext(CommonPool) { operation() }
        onSuccess(result)
      } catch (e: Exception) {
        onFailure(e)
      }
    }
  }

}