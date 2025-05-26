package edu.dyds.movies.domain.usecase

import edu.dyds.movies.domain.entity.QualifiedMovie
import edu.dyds.movies.domain.repository.MoviesRepository

class GetPopularMoviesUseCase(private val repository: MoviesRepository) {
    suspend operator fun invoke(): List<QualifiedMovie>{
        return repository.getPopularMovies().sortedByDescending { it.voteAverage }.map { movie -> QualifiedMovie(movie, movie.voteAverage >= 6.0) }
    }
}
