package edu.dyds.movies.data

import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.repository.MoviesRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val API_KEY = "d18da1b5da16397619c688b0263cd281"

class MoviesRepositoryImplementation(): MoviesRepository {
    private val tmdbHttpClient: HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        install(DefaultRequest) {
            url {
                protocol = URLProtocol.HTTPS
                host = "api.themoviedb.org"
                parameters.append("api_key", API_KEY)
            }
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 5000
        }
    }

    private val cacheMovies: MutableList<Movie> = mutableListOf()

    override suspend fun getPopularMovies(): List<Movie> =
        cacheMovies.ifEmpty {
            try {
                //TODO: derivar el comportamiento en un shell tal vez
                getTMDBPopularMovies().results.map { it.toDomainMovie() }.apply {
                    cacheMovies.clear()
                    cacheMovies.addAll(this)
                }
            } catch (_: Exception) {
                emptyList()
            }
        }


    override suspend fun getMovieDetails(id: Int): Movie? {
        //TODO: shell en un archivo en external que maneje la solicitud (en realidad las funciones de abajo deberían)
        val movieDetails = try {
            getTMDBMovieDetails(id)
        } catch (_: Exception) {
            null
        }

        return movieDetails?.toDomainMovie()
    }

    private suspend fun getTMDBMovieDetails(id: Int): RemoteMovie =
        tmdbHttpClient.get("/3/movie/$id").body()

    private suspend fun getTMDBPopularMovies(): RemoteResult =
        tmdbHttpClient.get("/3/discover/movie?sort_by=popularity.desc").body()


}