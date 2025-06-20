import di.TestDependencyInjector
import edu.dyds.movies.data.local.CacheLocalSource
import edu.dyds.movies.domain.entity.Movie
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals


class CacheLocalSourceTest {

    val cacheLocalSource = CacheLocalSource()

    @Test
    fun `cacheLocalSource returns an empty list when no movie was added`() {
        // Arrange
        var moviesList = emptyList<Movie>()
        val expectedList = emptyList<Movie>()

        // Act
        runTest {
            moviesList = cacheLocalSource.getPopularMoviesFromSource()
        }

        // Assert
        assertEquals(expectedList, moviesList)
    }

    @Test
    fun `cacheLocalSource effectively updates when calling update() with a list`() {
        // Arrange
        val expectedListPosUpdate = listOf(
            TestDependencyInjector.getTestMovie()
        )
        var resultMovieList = emptyList<Movie>()

        // Act
        runTest {
            cacheLocalSource.update(expectedListPosUpdate)

            resultMovieList = cacheLocalSource.getPopularMoviesFromSource()
        }

        // Assert
        assertEquals(expectedListPosUpdate, resultMovieList)
    }

}
