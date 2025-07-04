package edu.dyds.movies.data

import edu.dyds.movies.data.external.MoviesDetailsExternalSource
import edu.dyds.movies.data.external.PopularMoviesExternalSource
import edu.dyds.movies.di.TestDependencyInjector
import edu.dyds.movies.data.local.MoviesLocalSource
import edu.dyds.movies.domain.entity.Movie
import kotlin.test.Test
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals

class MoviesRepositoryImpTest {
    class FakeLocalSource(
        var movies: List<Movie> = emptyList()
    ) : MoviesLocalSource {
        override suspend fun getPopularMoviesFromSource() = movies
        override suspend fun update(popularMovies: List<Movie>) { this.movies = popularMovies }
    }

    class FakeSuccessfulPopularMoviesExternalSource (
        private val movies: List<Movie> = emptyList()
    ): PopularMoviesExternalSource {
        override suspend fun getPopularMoviesFromSource(): List<Movie> = movies
    }

    class FakeFailingPopularMoviesExternalSource : PopularMoviesExternalSource {
        override suspend fun getPopularMoviesFromSource(): List<Movie> {
            throw Exception("Fallo external")
        }
    }

    object MovieDetailsExternalSourceDummy: MoviesDetailsExternalSource {
        override suspend fun getMovieDetailsFromSource(title: String): Movie? = null
    }

    class FakeMovieDetailsExternalSource(private val movie: Movie): MoviesDetailsExternalSource {
        override suspend fun getMovieDetailsFromSource(title: String): Movie? {
            return movie
        }
    }

    @Test
    fun `getPopularMovies returns from local if not empty`() = runTest {
        //arrange
        val moviesList = TestDependencyInjector.getTestMovieList()
        val repo = MoviesRepositoryImp(
            FakeLocalSource(moviesList),
            FakeSuccessfulPopularMoviesExternalSource(),
            MovieDetailsExternalSourceDummy
        )
        //act
        val result = repo.getPopularMovies()
        //assert
        assertEquals(moviesList, result)
    }

    @Test
    fun `getPopularMovies fetches from external if local is empty`() = runTest {
        // arrange
        val moviesList = TestDependencyInjector.getTestMovieList()
        val local = FakeLocalSource()
        val repo = MoviesRepositoryImp(
            local,
            FakeSuccessfulPopularMoviesExternalSource(moviesList),
            MovieDetailsExternalSourceDummy
        )
        // act
        val result = repo.getPopularMovies()
        // assert
        assertEquals(moviesList, result)
        assertEquals(moviesList, local.movies)
    }


    @Test
    fun `getPopularMovies returns emptyList() on exception`() = runTest {
        // arrange
        val repo = MoviesRepositoryImp(
            FakeLocalSource(),
            FakeFailingPopularMoviesExternalSource(),
            MovieDetailsExternalSourceDummy
        )
        // act
        val result = repo.getPopularMovies()
        // assert
        assertEquals(emptyList(),result)
    }

    @Test
    fun `getMovieDetails returns movie from external by title`() = runTest {
        // arrange
        val movie = TestDependencyInjector.getTestMovie()
        val repo = MoviesRepositoryImp(
            FakeLocalSource(),
            FakeSuccessfulPopularMoviesExternalSource(),
            FakeMovieDetailsExternalSource(movie)
        )
        // act
        val result = repo.getMovieDetails(TestDependencyInjector.testTitle)
        // assert
        assertEquals(movie, result)
    }
}