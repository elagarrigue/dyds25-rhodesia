package edu.dyds.movies.data.external
import edu.dyds.movies.domain.entity.Movie

interface ExternalSource {
    suspend fun getMovieDetailsFromSource(id: Int): Movie
    suspend fun getPopularMoviesFromSource(): List<Movie>
}