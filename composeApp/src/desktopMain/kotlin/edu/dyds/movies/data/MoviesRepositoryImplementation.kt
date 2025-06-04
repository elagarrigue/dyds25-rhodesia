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

    override suspend fun getPopularMovies(): List<Movie> {
        return try{
            cacheMovies.getPopularMoviesFromSource().ifEmpty {
                source.getPopularMoviesFromSource().map {it. toDomainMovie()}.also{
                    cacheMovies.update(it)
                }
            }
        } catch(e: Exception){
            emptyList()
        }
    }

    override suspend fun getMovieDetails(id: Int): Movie? {
        return try {
            source.getMovieDetailsFromSource(id)?.toDomainMovie()
        } catch (e: Exception) {
            null
        }
    }

}