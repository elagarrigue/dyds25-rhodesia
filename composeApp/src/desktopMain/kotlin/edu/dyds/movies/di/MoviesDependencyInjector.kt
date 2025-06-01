package edu.dyds.movies.di

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.dyds.movies.data.MoviesRepositoryImplementation
import edu.dyds.movies.domain.repository.MoviesRepository
import edu.dyds.movies.domain.usecase.MovieDetailsUseCaseImplementation
import edu.dyds.movies.domain.usecase.MovieDetailsUseCase
import edu.dyds.movies.domain.usecase.PopularMoviesUseCase
import edu.dyds.movies.domain.usecase.PopularMoviesUseCaseImplementation
import edu.dyds.movies.presentation.home.HomeViewModel
import edu.dyds.movies.presentation.detail.DetailViewModel

object MoviesDependencyInjector {

    private val repository: MoviesRepository = MoviesRepositoryImplementation()
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