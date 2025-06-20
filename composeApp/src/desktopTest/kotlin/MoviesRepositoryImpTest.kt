import di.TestDependencyInjector
import edu.dyds.movies.data.MoviesRepositoryImp
import edu.dyds.movies.data.external.MoviesExternalSource
import edu.dyds.movies.data.local.MoviesLocalSource
import edu.dyds.movies.domain.entity.Movie
import kotlin.test.Test
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals

class MoviesRepositoryImpTest {
    class FakeSuccessfulLocalSource(
        var movies: List<Movie> = emptyList()
    ) : MoviesLocalSource {
        override suspend fun getPopularMoviesFromSource() = movies
        override suspend fun update(popularMovies: List<Movie>) { this.movies = popularMovies }
    }

    class FakeSuccessfulExternalSource(
        private val movies: List<Movie> = emptyList(),
        private val movie: Movie? = null
    ) : MoviesExternalSource {
        override suspend fun getPopularMoviesFromSource() = movies
        override suspend fun getMovieDetailsFromSource(id: Int): Movie {
            return movie ?: throw Exception("Movie not found")
        }
    }

    class FakeFailingLocalSource : MoviesLocalSource {
        override suspend fun getPopularMoviesFromSource(): List<Movie> {
            throw Exception("Fallo local")
        }
        override suspend fun update(popularMovies: List<Movie>) { }
    }

    class FakeFailingExternalSource : MoviesExternalSource {
        override suspend fun getPopularMoviesFromSource(): List<Movie> {
            throw Exception("Fallo external")
        }
        override suspend fun getMovieDetailsFromSource(id: Int): Movie {
            throw Exception("Fallo external")
        }
    }

    @Test
    fun `getPopularMovies returns from local if not empty`() = runTest {
        //arrange
        val moviesList = TestDependencyInjector.getTestMovieList()
        val repo = MoviesRepositoryImp(
            FakeSuccessfulLocalSource(moviesList),
            FakeSuccessfulExternalSource()
        )
        //act
        val result = repo.getPopularMovies()
        //assert
        assertEquals(moviesList.size, result.size)
        assertEquals(moviesList.first(), result.first())
    }

    @Test
    fun `getPopularMovies fetches from external if local is empty`() = runTest {
        // arrange
        val moviesList = TestDependencyInjector.getTestMovieList()
        val repo = MoviesRepositoryImp(
            FakeSuccessfulLocalSource(),
            FakeSuccessfulExternalSource(moviesList)
        )
        // act
        val result = repo.getPopularMovies()
        // assert
        assertEquals(moviesList.size, result.size)
        assertEquals(moviesList.first(), result.first())
    }


    @Test
    fun `getPopularMovies returns emptyList() on exception`() = runTest {
        // arrange
        val repo = MoviesRepositoryImp(
            FakeFailingLocalSource(),
            FakeFailingExternalSource()
        )
        // act
        val result = repo.getPopularMovies()
        // assert
        assertEquals(emptyList(),result)
    }

    @Test
    fun `getMovieDetails returns movie from external by id`() = runTest {
        // arrange
        val movie = TestDependencyInjector.getTestMovie()
        val repo = MoviesRepositoryImp(
            FakeSuccessfulLocalSource(),
            FakeSuccessfulExternalSource(movie = movie)
        )
        // act
        val result = repo.getMovieDetails(0)
        // assert
        assertEquals(movie, result)
    }

    @Test
    fun `getMovieDetails returns null on exception`() = runTest {
        // arrange
        val repo = MoviesRepositoryImp(
            FakeSuccessfulLocalSource(),
            FakeFailingExternalSource())
        // act
        val result = repo.getMovieDetails(1)
        // assert
        assertEquals(null,result)
    }
}