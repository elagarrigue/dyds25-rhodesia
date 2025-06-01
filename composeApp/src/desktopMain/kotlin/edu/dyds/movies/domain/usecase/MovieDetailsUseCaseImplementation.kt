package edu.dyds.movies.domain.usecase

import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.repository.MoviesRepository

class MovieDetailsUseCaseImplementation(private val repository: MoviesRepository) : MovieDetailsUseCase {
    override suspend fun execute(movieId: Int): Movie? {
        return repository.getMovieDetails(movieId)
    }
}