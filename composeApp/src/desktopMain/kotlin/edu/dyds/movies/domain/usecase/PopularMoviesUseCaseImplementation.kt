package edu.dyds.movies.domain.usecase

import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.entity.QualifiedMovie
import edu.dyds.movies.domain.repository.MoviesRepository

class PopularMoviesUseCaseImplementation(private val repository: MoviesRepository): PopularMoviesUseCase {
    override suspend fun execute(): List<QualifiedMovie> =
        repository.getPopularMovies().sortAndMap()

    private fun List<Movie>.sortAndMap() =
        this.sortedByDescending { it.voteAverage }.map {
                movie -> QualifiedMovie(movie, movie.voteAverage >= 6.0)
        }
}
