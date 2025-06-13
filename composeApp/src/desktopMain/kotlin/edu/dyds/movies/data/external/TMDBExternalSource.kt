package edu.dyds.movies.data.external

import edu.dyds.movies.domain.entity.Movie
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class TMDBExternalSource(private val tmdbHttpClient: HttpClient) : MoviesExternalSource {

    override suspend fun getMovieDetailsFromSource(id: Int): Movie =
        getTMDBMovieDetails(id).toDomainMovie()

    override suspend fun getPopularMoviesFromSource(): List<Movie> =
        getTMDBPopularMovies().results.map {it. toDomainMovie()}

    private suspend fun getTMDBMovieDetails(id: Int): RemoteMovie =
        tmdbHttpClient.get("/3/movie/$id").body()

    private suspend fun getTMDBPopularMovies(): RemoteResult =
        tmdbHttpClient.get("/3/discover/movie?sort_by=popularity.desc").body()

}