package di

import edu.dyds.movies.domain.entity.Movie
import fakes.MoviesRepositoryFake

object TestDependencyInjector {

    fun getRepositoryFake() = MoviesRepositoryFake()

    fun getTestMovieList(): List<Movie> = listOf(
        createMovie(0, 6.7),
        createMovie(0, 1.7),
        createMovie(0, 3.6),
        createMovie(0, 9.7),
    )

    fun getTestMovie() = createMovie()

    fun createMovie(id: Int = 0, voteAverage: Double = 0.0) = Movie(
        id = id,
        title = "title",
        overview = "overview",
        releaseDate = "releaseDate",
        poster = "poster",
        backdrop = "backdrop",
        originalTitle = "original",
        originalLanguage = "originalLanguage",
        popularity = 0.0,
        voteAverage = voteAverage
    )

}