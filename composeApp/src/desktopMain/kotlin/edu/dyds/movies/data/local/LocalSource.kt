package edu.dyds.movies.data.local

import edu.dyds.movies.domain.entity.Movie

interface LocalSource {
    suspend fun getMovieDetailsFromSource(id: Int): Movie
    suspend fun getPopularMoviesFromSource(): List<Movie>
    suspend fun update(popularMovies: List<Movie>)
}