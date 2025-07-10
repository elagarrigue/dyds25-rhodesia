package edu.dyds.movies.domain.usecase

import edu.dyds.movies.domain.entity.Movie

interface MovieDetailsUseCase {
    suspend fun execute(movieTitle: String): Movie?
}
