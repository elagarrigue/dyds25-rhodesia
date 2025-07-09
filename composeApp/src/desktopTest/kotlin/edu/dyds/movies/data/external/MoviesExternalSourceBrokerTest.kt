package edu.dyds.movies.data.external

import edu.dyds.movies.di.TestDependencyInjector
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

private val tmdbMovie = TestDependencyInjector.createMovie(voteAverage = 6.0).copy(popularity = 10.0)
private val omdbMovie = TestDependencyInjector.createMovie(voteAverage = 8.0).copy(popularity = 20.0)

class MoviesExternalSourceBrokerTest {

    object FakeFailingExternalSource: MoviesDetailsExternalSource {
        override suspend fun getMovieDetailsFromSource(title: String) = throw Exception(":c")
    }

    object FakeSuccessfulTMDBExternalSource: MoviesDetailsExternalSource {
        override suspend fun getMovieDetailsFromSource(title: String) =
            tmdbMovie
    }

    object FakeSuccessfulOMDBExternalSource: MoviesDetailsExternalSource {
        override suspend fun getMovieDetailsFromSource(title: String) =
            omdbMovie
    }

    @Test
    fun `getMovieDetailsFromSource devuelve un mix cuando ambos sources cuando tienen resultados`() = runTest{
        //arrange
        val broker = MoviesExternalSourceBroker(
            FakeSuccessfulTMDBExternalSource,
            FakeSuccessfulOMDBExternalSource
        )
        val expectedMovie =  TestDependencyInjector.getTestMovie().copy(
            overview = "TMDB:  ${tmdbMovie.overview}\n\nOMDB: ${omdbMovie.overview}",
            popularity = (tmdbMovie.popularity + omdbMovie.popularity) / 2.0,
            voteAverage = (tmdbMovie.voteAverage + omdbMovie.voteAverage) / 2.0
        )

        //act
        val result = broker.getMovieDetailsFromSource("")

        //assert
        assertEquals(expectedMovie, result)
    }

    @Test
    fun `getMovieDetailsFromSource devuelve solo de tmdb cuando omdb no tiene resultados`() = runTest{
        //arrange
        val broker = MoviesExternalSourceBroker(
            FakeSuccessfulTMDBExternalSource,
            FakeFailingExternalSource
        )
        val expectedMovie = tmdbMovie.copy(
            overview = "TMDB: ${tmdbMovie.overview}"
        )

        //act
        val result = broker.getMovieDetailsFromSource("")

        //assert
        assertEquals(expectedMovie, result)
    }

    @Test
    fun `getMovieDetailsFromSource devuelve solo de omdb cuando tmdb no tiene resultados`() = runTest {
        //arrange
        val broker = MoviesExternalSourceBroker(
            FakeFailingExternalSource,
            FakeSuccessfulOMDBExternalSource
        )
        val expectedMovie = omdbMovie.copy(
            overview = "OMDB: ${omdbMovie.overview}"
        )

        //act
        val result = broker.getMovieDetailsFromSource("")

        //assert
        assertEquals(expectedMovie, result)
    }

    @Test
    fun `getMovieDetailsFromSource devuelve null cuando no obtiene resultados`() = runTest {
        //arrange
        val broker = MoviesExternalSourceBroker(
            FakeFailingExternalSource,
            FakeFailingExternalSource
        )

        //act
        val result = broker.getMovieDetailsFromSource("")

        assertNull(result)
    }

}