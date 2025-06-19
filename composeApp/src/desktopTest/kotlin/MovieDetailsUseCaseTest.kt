import di.TestDependencyInjector
import edu.dyds.movies.domain.usecase.MovieDetailsUseCaseImplementation
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class MovieDetailsUseCaseTest {

    val repositoryFake = TestDependencyInjector.getRepositoryFake()

    val movieDetailsUseCase = MovieDetailsUseCaseImplementation(
        repositoryFake
    )

    @Test
    fun `movieDetailsUseCase effectively calls repository on execute()`() {
        //arrange
        //act
        runTest { movieDetailsUseCase.execute(1) }

        //assert
        assertEquals(true, repositoryFake.getMovieDetailsWasCalled)
    }

}