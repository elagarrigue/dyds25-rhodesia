import di.TestDependencyInjector
import edu.dyds.movies.domain.usecase.MovieDetailsUseCaseImplementation
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
        assertNull(movieDetailsUseCase.execute(1))
    }

}