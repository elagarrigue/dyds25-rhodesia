package edu.dyds.movies.data.external

import edu.dyds.movies.di.TestDependencyInjector
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull

class MoviesExternalSourceBrokerTest {

    object FakeFailingExternalSource: MoviesDetailsExternalSource {
        override suspend fun getMovieDetailsFromSource(title: String) = null
    }

    object FakeSuccessfulTMDBExternalSource: MoviesDetailsExternalSource {
        override suspend fun getMovieDetailsFromSource(title: String) =
            TestDependencyInjector.getTestMovie()
    }

    object FakeSuccessfulOMDBExternalSource: MoviesDetailsExternalSource {
        override suspend fun getMovieDetailsFromSource(title: String) =
            TestDependencyInjector.getTestMovie()
    }

    @Test
    fun `getMovieDetailsFromSource devuelve un mix cuando ambos sources cuando tienen resultados`() = runTest{
        //arrange
        val broker = MoviesExternalSourceBroker(
            FakeSuccessfulTMDBExternalSource,
            FakeSuccessfulOMDBExternalSource
        )
        val expectedMovie = TestDependencyInjector.getTestMovie().copy(
            overview = "TMDB:  ${TestDependencyInjector.getTestMovie().overview}\n\nOMDB: ${TestDependencyInjector.getTestMovie().overview}",
            popularity = TestDependencyInjector.getTestMovie().popularity,
            voteAverage = TestDependencyInjector.getTestMovie().voteAverage
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
        val expectedMovie = TestDependencyInjector.getTestMovie().copy(
            overview = "TMDB: ${TestDependencyInjector.getTestMovie().overview}"
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
        val expectedMovie = TestDependencyInjector.getTestMovie().copy(
            overview = "OMDB: ${TestDependencyInjector.getTestMovie().overview}"
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