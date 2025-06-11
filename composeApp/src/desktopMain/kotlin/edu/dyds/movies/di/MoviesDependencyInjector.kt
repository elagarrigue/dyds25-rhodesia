package edu.dyds.movies.di

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.dyds.movies.data.MoviesRepositoryImp
import edu.dyds.movies.data.external.TMDBExternalSource
import edu.dyds.movies.data.local.CacheLocalSource
import edu.dyds.movies.domain.repository.MoviesRepository
import edu.dyds.movies.domain.usecase.MovieDetailsUseCaseImplementation
import edu.dyds.movies.domain.usecase.MovieDetailsUseCase
import edu.dyds.movies.domain.usecase.PopularMoviesUseCase
import edu.dyds.movies.domain.usecase.PopularMoviesUseCaseImplementation
import edu.dyds.movies.presentation.home.HomeViewModel
import edu.dyds.movies.presentation.detail.DetailViewModel

object MoviesDependencyInjector {
    private val cacheMovies = CacheLocalSource()
    private val source = TMDBExternalSource()
    private val repository: MoviesRepository = MoviesRepositoryImp(cacheMovies, source)
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