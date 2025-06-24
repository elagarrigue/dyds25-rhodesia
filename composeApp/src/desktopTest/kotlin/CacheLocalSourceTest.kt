import di.TestDependencyInjector
import edu.dyds.movies.data.local.CacheLocalSource
import edu.dyds.movies.domain.entity.Movie
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
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
    fun `cacheLocalSource effectively updates when was empty on update()`() {
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

    @Test
    fun `cacheLocalSource effectively updates when wasn't empty on update()`() = runTest {
        // Arrange
        val expectedListPosUpdate = mutableListOf<Movie>(TestDependencyInjector.getTestMovie())
        expectedListPosUpdate.addAll(TestDependencyInjector.getTestMovieList())

        cacheLocalSource.update(listOf(TestDependencyInjector.getTestMovie()))

        //act
        cacheLocalSource.update(TestDependencyInjector.getTestMovieList())

        // Assert
        assertEquals(expectedListPosUpdate, cacheLocalSource.getPopularMoviesFromSource())
    }

}
