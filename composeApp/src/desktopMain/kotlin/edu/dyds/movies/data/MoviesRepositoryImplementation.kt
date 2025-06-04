package edu.dyds.movies.data

import edu.dyds.movies.data.external.ExternalSource
import edu.dyds.movies.data.external.HTTPSource
import edu.dyds.movies.data.local.CacheEmulator
import edu.dyds.movies.data.local.LocalSource
import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.repository.MoviesRepository

class MoviesRepositoryImplementation(
    private val cacheMovies: LocalSource,
    private val source: ExternalSource
): MoviesRepository {

    override suspend fun getPopularMovies(): List<Movie> =
        cacheMovies.getPopularMoviesFromSource().ifEmpty {
            source.getPopularMoviesFromSource().map { it.toDomainMovie() }.apply {
                cacheMovies.update(this)
            }
        }

    override suspend fun getMovieDetails(id: Int): Movie? =
        /*
         * TODO: preguntar si está bien esto, porque resultado de la
         *  implementación de getPopularMovies, siempre resulta en un
         *  cache hit y nunca pide los detalles a source (remote source)
         */
        cacheMovies.getMovieDetailsFromSource(id)?:
            source.getMovieDetailsFromSource(id)?.toDomainMovie()

}