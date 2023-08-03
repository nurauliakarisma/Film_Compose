package com.project.fleem.data

import com.project.fleem.models.Cart
import com.project.fleem.models.FilmData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class FleemRepository {
    private val fleemCartList = mutableListOf<Cart>()

    init {
        if (fleemCartList.isEmpty()) {
            FilmData.dummyFilms.forEach {
                fleemCartList.add(Cart(it, 0))
            }
        }
    }

    fun getAllFLeemCart() = flowOf(fleemCartList)

    fun getFilmById(filmId: Long) = fleemCartList.first {
        it.film.id == filmId
    }

    fun updateFilmCart(filmId: Long, newValue: Int): Flow<Boolean> {
        val index = fleemCartList.indexOfFirst { it.film.id == filmId }
        val result = if (index >= 0) {
            val fleemCart = fleemCartList[index]
            fleemCartList[index] =
                fleemCart.copy(film = fleemCart.film, count = newValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedFilmCarts() = getAllFLeemCart()
        .map {
            it.filter { cartFilm ->
                cartFilm.count != 0
            }
        }
}