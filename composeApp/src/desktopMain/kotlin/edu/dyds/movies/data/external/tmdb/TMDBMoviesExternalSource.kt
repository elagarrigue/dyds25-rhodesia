package edu.dyds.movies.data.external.tmdb

import edu.dyds.movies.data.external.DetailedMovieExternalSource
import edu.dyds.movies.data.external.PopularMoviesExternalSource
import edu.dyds.movies.domain.entity.Movie
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class TMDBMoviesExternalSource(private val tmdbHttpClient: HttpClient) : PopularMoviesExternalSource,
    DetailedMovieExternalSource {

    override suspend fun getMovieDetailsFromSource(title : String): Movie =
        getTMDBMovieDetails(title).toDomainMovie()

    override suspend fun getPopularMoviesFromSource(): List<Movie> =
        getTMDBPopularMovies().results.map {it. toDomainMovie()}

    private suspend fun getTMDBMovieDetails(title: String): RemoteMovie =
        tmdbHttpClient.get("/3/movie?query=$title").body()

    private suspend fun getTMDBPopularMovies(): RemoteResult =
        tmdbHttpClient.get("/3/discover/movie?sort_by=popularity.desc").body()

}