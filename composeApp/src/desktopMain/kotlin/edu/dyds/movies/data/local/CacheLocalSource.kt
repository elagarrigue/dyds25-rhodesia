package edu.dyds.movies.data.local

import edu.dyds.movies.domain.entity.Movie

class CacheLocalSource: MoviesLocalSource {

    private val emulatorList: MutableList<Movie> = mutableListOf()

    override suspend fun getMovieDetailsFromSource(id: Int): Movie =
        emulatorList.first { it.id == id }


    override suspend fun getPopularMoviesFromSource(): List<Movie> {
        return emulatorList
    }

    override suspend fun update(popularMovies: List<Movie>) {
        emulatorList.addAll(popularMovies)
    }
}