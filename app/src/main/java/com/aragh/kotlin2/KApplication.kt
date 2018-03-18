package com.aragh.kotlin2

import android.app.Application
import com.aragh.kotlin2.di.usersModule
import org.koin.android.ext.android.startKoin


class KApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    startKoin(this, listOf(usersModule))
  }
}