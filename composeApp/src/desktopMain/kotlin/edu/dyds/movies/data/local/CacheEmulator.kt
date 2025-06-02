package edu.dyds.movies.data.local

import edu.dyds.movies.domain.entity.Movie

class CacheEmulator: LocalSource {

    private val emulatorList: MutableList<Movie> = mutableListOf()

    override suspend fun getMovieDetailsFromSource(id: Int): Movie? =
        try {
            emulatorList.first { it.id == id }
        } catch (_: NoSuchElementException) {
            null
        }


    override suspend fun getPopularMoviesFromSource(): List<Movie> {
        return emulatorList
    }

    override suspend fun update(popularMovies: List<Movie>) {
        emulatorList.addAll(popularMovies)
    }
}