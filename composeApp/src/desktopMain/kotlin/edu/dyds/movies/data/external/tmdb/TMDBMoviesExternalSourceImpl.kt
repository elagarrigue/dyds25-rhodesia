package edu.dyds.movies.data.external.tmdb

import edu.dyds.movies.data.external.MoviesDetailsExternalSource
import edu.dyds.movies.data.external.PopularMoviesExternalSource
import edu.dyds.movies.domain.entity.Movie
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class TMDBMoviesExternalSourceImpl(private val tmdbHttpClient: HttpClient) : PopularMoviesExternalSource,
    MoviesDetailsExternalSource {

    override suspend fun getMovieDetailsFromSource(title : String): Movie? =
        try {
            getTMDBMovieDetails(title).results.first().toDomainMovie()
        } catch (_: Exception) {
            null
        }

    override suspend fun getPopularMoviesFromSource(): List<Movie> =
        getTMDBPopularMovies().results.map { it.toDomainMovie() }

    private suspend fun getTMDBMovieDetails(title: String): TMDBRemoteResult =
        tmdbHttpClient.get("/3/search/movie?query=$title").body()

    private suspend fun getTMDBPopularMovies(): TMDBRemoteResult =
        tmdbHttpClient.get("/3/discover/movie?sort_by=popularity.desc").body()

}