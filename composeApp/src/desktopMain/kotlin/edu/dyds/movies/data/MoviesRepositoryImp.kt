package edu.dyds.movies.data

import edu.dyds.movies.data.external.MoviesExternalSourceBroker
import edu.dyds.movies.data.local.MoviesLocalSource
import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.repository.MoviesRepository

class MoviesRepositoryImp(
    private val moviesLocalSource: MoviesLocalSource,
    private val moviesExternalSourceBroker: MoviesExternalSourceBroker
): MoviesRepository {

    override suspend fun getPopularMovies(): List<Movie> =
        try{
            moviesLocalSource.getPopularMoviesFromSource().ifEmpty {
                moviesExternalSourceBroker.getPopularMoviesFromSource().also {
                    moviesLocalSource.update(it)
                }
            }
        } catch(_: Exception) {
            emptyList()
        }

    override suspend fun getMovieDetails(id: Int): Movie? =
        try {
            moviesExternalSourceBroker.getMovieDetailsFromSource(id)
        } catch (_: Exception) {
            null
        }

}