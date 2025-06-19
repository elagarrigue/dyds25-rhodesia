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

        // Act
        runTest {
            moviesList = cacheLocalSource.getPopularMoviesFromSource()
        }

        // Assert
        assertEquals(emptyList<Movie>(), moviesList)
    }

    @Test
    fun `cacheLocalSource effectively updates when calling update() with a list`() {
        // Arrange
        val expectedListPosUpdate = listOf(
            TestDependencyInjector.getTestMovie()
        )
        var resultMovieList = emptyList<Movie>()
        var preUpdateSize = -1
        var posUpdateSize = -1

        // Act
        runTest {

            preUpdateSize = cacheLocalSource.getPopularMoviesFromSource().size

            cacheLocalSource.update(expectedListPosUpdate)

            resultMovieList = cacheLocalSource.getPopularMoviesFromSource()

            posUpdateSize = resultMovieList.size
        }

        // Assert
        assertEquals(expectedListPosUpdate, resultMovieList)
        assertEquals(0, preUpdateSize)
        assertEquals(1, posUpdateSize)
    }

}
