package edu.dyds.movies.domain.usecase

import edu.dyds.movies.di.TestDependencyInjector
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNull

class MovieDetailsUseCaseTest {

    val repositoryFake = TestDependencyInjector.getRepositoryFake()

    val movieDetailsUseCase = MovieDetailsUseCaseImplementation(
        repositoryFake
    )

    @Test
    fun `movieDetailsUseCase effectively calls repository on execute()`() = runTest {
        //assert
        assertNull(movieDetailsUseCase.execute(""))
    }

}