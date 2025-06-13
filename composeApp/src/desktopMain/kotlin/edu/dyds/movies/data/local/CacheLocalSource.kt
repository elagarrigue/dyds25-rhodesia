package edu.dyds.movies.data.local

import edu.dyds.movies.domain.entity.Movie

class CacheLocalSource: MoviesLocalSource {

    private val cacheList: MutableList<Movie> = mutableListOf()

    override suspend fun getPopularMoviesFromSource(): List<Movie> {
        return cacheList
    }

    override suspend fun update(popularMovies: List<Movie>) {
        cacheList.addAll(popularMovies)
    }
}