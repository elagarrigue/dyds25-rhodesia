package di

import edu.dyds.movies.domain.entity.Movie
import fakes.MoviesRepositoryFake

object TestDependencyInjector {

    fun getRepositoryFake() = MoviesRepositoryFake()

    fun getTestMovieList(): List<Movie> {
        val movieList: MutableList<Movie> = mutableListOf()

        movieList.addLast(Movie(
            id = 0,
            title = "",
            overview = "",
            releaseDate = "",
            poster = "",
            backdrop = "",
            originalTitle = "",
            originalLanguage = "",
            popularity = 0.0,
            voteAverage = 6.7
        ))

        movieList.addLast(Movie(
            id = 0,
            title = "",
            overview = "",
            releaseDate = "",
            poster = "",
            backdrop = "",
            originalTitle = "",
            originalLanguage = "",
            popularity = 0.0,
            voteAverage = 1.7
        ))

        movieList.addLast(Movie(
            id = 0,
            title = "",
            overview = "",
            releaseDate = "",
            poster = "",
            backdrop = "",
            originalTitle = "",
            originalLanguage = "",
            popularity = 0.0,
            voteAverage = 3.6
        ))

        movieList.addLast(Movie(
            id = 0,
            title = "",
            overview = "",
            releaseDate = "",
            poster = "",
            backdrop = "",
            originalTitle = "",
            originalLanguage = "",
            popularity = 0.0,
            voteAverage = 9.7
        ))

        return movieList
    }

    fun getTestMovie() = Movie(
        id = 0,
        title = "",
        overview = "",
        releaseDate = "",
        poster = "",
        backdrop = "",
        originalTitle = "",
        originalLanguage = "",
        popularity = 0.0,
        voteAverage = 0.0
    )


}