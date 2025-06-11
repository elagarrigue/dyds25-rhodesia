package edu.dyds.movies.data

import edu.dyds.movies.data.external.MoviesExternalSource
import edu.dyds.movies.data.local.MoviesLocalSource
import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.repository.MoviesRepository

class MoviesRepositoryImp(
    private val moviesLocalSource: MoviesLocalSource,
    private val moviesExternalSource: MoviesExternalSource
): MoviesRepository {

    override suspend fun getPopularMovies(): List<Movie> =
        try{
            moviesLocalSource.getPopularMoviesFromSource().ifEmpty {
                moviesExternalSource.getPopularMoviesFromSource().also {
                    moviesLocalSource.update(it)
                }
            }
        } catch(_: Exception) {
            emptyList()
        }

    override suspend fun getMovieDetails(id: Int): Movie? =
        try {
            moviesExternalSource.getMovieDetailsFromSource(id)
        } catch (_: Exception) {
            null
        }

}