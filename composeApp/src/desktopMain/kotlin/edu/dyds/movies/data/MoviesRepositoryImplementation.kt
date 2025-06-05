package edu.dyds.movies.data

import edu.dyds.movies.data.external.ExternalSource
import edu.dyds.movies.data.local.LocalSource
import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.repository.MoviesRepository

class MoviesRepositoryImplementation(
    private val cacheMovies: LocalSource,
    private val source: ExternalSource
): MoviesRepository {

    override suspend fun getPopularMovies(): List<Movie> =
        try{
            cacheMovies.getPopularMoviesFromSource().ifEmpty {
                source.getPopularMoviesFromSource().also {
                    cacheMovies.update(it)
                }
            }
        } catch(_: Exception) {
            emptyList()
        }

    override suspend fun getMovieDetails(id: Int): Movie? =
        try {
            source.getMovieDetailsFromSource(id)
        } catch (_: Exception) {
            null
        }

}