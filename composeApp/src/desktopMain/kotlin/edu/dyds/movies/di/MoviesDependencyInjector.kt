package edu.dyds.movies.di

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.dyds.movies.data.MoviesRepositoryImp
import edu.dyds.movies.data.external.MoviesExternalSourceBroker
import edu.dyds.movies.data.external.TMDBExternalSource
import edu.dyds.movies.data.external.OMDBExternalSource
import edu.dyds.movies.data.local.CacheLocalSource
import edu.dyds.movies.domain.repository.MoviesRepository
import edu.dyds.movies.domain.usecase.MovieDetailsUseCaseImplementation
import edu.dyds.movies.domain.usecase.MovieDetailsUseCase
import edu.dyds.movies.domain.usecase.PopularMoviesUseCase
import edu.dyds.movies.domain.usecase.PopularMoviesUseCaseImplementation
import edu.dyds.movies.presentation.home.HomeViewModel
import edu.dyds.movies.presentation.detail.DetailViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val TMDB_API_KEY = "d18da1b5da16397619c688b0263cd281"
private const val OMDB_API_KEY = "a96e7f78"

object MoviesDependencyInjector {
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
                parameters.append("api_key", TMDB_API_KEY)
            }
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 5000
        }
    }
    private val omdbHttpClient: HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        install(DefaultRequest) {
            url {
                protocol = URLProtocol.HTTPS
                host = "www.omdbapi.com"
                parameters.append("api_key", OMDB_API_KEY)
            }
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 5000
        }
    }
    private val cacheMovies = CacheLocalSource()
    private val tmdbSource = TMDBExternalSource(tmdbHttpClient)
    private val omdbSource = OMDBExternalSource(omdbHttpClient)
    private val broker = MoviesExternalSourceBroker(tmdbSource, omdbSource)
    private val repository: MoviesRepository = MoviesRepositoryImp(cacheMovies, broker)
    private val popularMoviesUseCase: PopularMoviesUseCase = PopularMoviesUseCaseImplementation(repository)
    private val movieDetailsUseCase: MovieDetailsUseCase = MovieDetailsUseCaseImplementation(repository)

    @Composable
    fun getDetailViewModel(): DetailViewModel {
        return viewModel { DetailViewModel(movieDetailsUseCase) }
    }
    @Composable
    fun getHomeViewModel(): HomeViewModel {
        return viewModel { HomeViewModel(popularMoviesUseCase) }
    }

}