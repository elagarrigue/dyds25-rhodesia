package edu.dyds.movies.data

import edu.dyds.movies.data.external.MoviesDetailsExternalSource
import edu.dyds.movies.data.external.PopularMoviesExternalSource
import edu.dyds.movies.data.local.MoviesLocalSource
import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.repository.MoviesRepository

class MoviesRepositoryImp(
    private val moviesLocalSource: MoviesLocalSource,
    private val popularMoviesExternalSource: PopularMoviesExternalSource,
    private val movieDetailsExternalSource: MoviesDetailsExternalSource
): MoviesRepository {

    override suspend fun getPopularMovies(): List<Movie> =
        try{
            moviesLocalSource.getPopularMoviesFromSource().ifEmpty {
                popularMoviesExternalSource.getPopularMoviesFromSource().also {
                    moviesLocalSource.update(it)
                }
            }
        } catch(_: Exception) {
            emptyList()
        }

    override suspend fun getMovieDetails(title: String): Movie? =
        movieDetailsExternalSource.getMovieDetailsFromSource(title)
}
