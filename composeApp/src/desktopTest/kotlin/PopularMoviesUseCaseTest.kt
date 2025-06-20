import di.TestDependencyInjector
import edu.dyds.movies.domain.entity.QualifiedMovie
import edu.dyds.movies.domain.usecase.PopularMoviesUseCaseImplementation
import kotlinx.coroutines.test.runTest
import org.junit.Test
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
    fun `popularMoviesUseCase correctly returns a popular movies list`() {
        // Arrange
        val expectedMovieList = TestDependencyInjector.getTestMovieList().sortedByDescending {
            it.voteAverage
        }.map {
            movie ->
            QualifiedMovie(movie, movie.voteAverage >= 6)
        }
        var result: List<QualifiedMovie> = emptyList()

        // Act
        runTest { result = popularMoviesUseCase.execute() }

        // Assert
        assertEquals(expectedMovieList, result)
    }

}