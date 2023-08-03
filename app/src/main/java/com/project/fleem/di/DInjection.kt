package com.project.fleem.di

import com.project.fleem.data.FleemRepository
import com.project.fleem.data.FleemRepository.*


object DInjection {
    @Volatile
    private var instance: FleemRepository? = null

    private fun getInstance(): FleemRepository =
        instance ?: synchronized(this) {
            FleemRepository().apply {
                instance = this
            }
        }

    fun provideRepository(): FleemRepository {
        return getInstance()
    }
}