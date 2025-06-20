package fakes

import di.TestDependencyInjector
import edu.dyds.movies.domain.entity.Movie
import edu.dyds.movies.domain.repository.MoviesRepository

class MoviesRepositoryFake: MoviesRepository  {
    var getPopularMoviesWasCalled = false
    var getMovieDetailsWasCalled = false

    override suspend fun getPopularMovies(): List<Movie> {
        getPopularMoviesWasCalled = true

        return TestDependencyInjector.getTestMovieList()
    }

    override suspend fun getMovieDetails(id: Int): Movie? {
        getMovieDetailsWasCalled = true
        return null
    }
}