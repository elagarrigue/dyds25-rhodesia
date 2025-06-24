package edu.dyds.movies.domain.usecase

import edu.dyds.movies.di.TestDependencyInjector
import edu.dyds.movies.di.TestDependencyInjector.createMovie
import edu.dyds.movies.domain.entity.QualifiedMovie
import edu.dyds.movies.domain.usecase.PopularMoviesUseCaseImplementation
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PopularMoviesUseCaseTest {

    val repositoryFake = TestDependencyInjector.getRepositoryFake()

    val popularMoviesUseCase = PopularMoviesUseCaseImplementation(
        repositoryFake
    )

    @Test
    fun `popularMoviesUseCase effectively calls repository on execute()`() {
        // Arrange
        // Act
        runTest { popularMoviesUseCase.execute() }

        // Assert
        assertEquals(true, repositoryFake.getPopularMoviesWasCalled)
    }

    @Test
    fun `popularMoviesUseCase correctly returns a popular movies list`() = runTest {
        // Arrange
        val expectedMovieList = listOf(
            QualifiedMovie(createMovie(0, 9.7), true),
            QualifiedMovie(createMovie(0, 6.7), true),
            QualifiedMovie(createMovie(0, 3.6), false),
            QualifiedMovie(createMovie(0, 1.7), false),
        )

        // Act
        val result = popularMoviesUseCase.execute()

        // Assert
        assertEquals(expectedMovieList, result)
    }

}