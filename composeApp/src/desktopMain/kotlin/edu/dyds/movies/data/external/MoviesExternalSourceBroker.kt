package edu.dyds.movies.data.external

import edu.dyds.movies.domain.entity.Movie

class MoviesExternalSourceBroker(
    private val tmdbExternalSource: MoviesDetailsExternalSource,
    private val omdbExternalSource: MoviesDetailsExternalSource)
    : MoviesDetailsExternalSource {

    override suspend fun getMovieDetailsFromSource(title: String): Movie? {
        val tmdbMovie = tmdbExternalSource.getMovieDetailsFromSource(title)
        val omdbMovie = omdbExternalSource.getMovieDetailsFromSource(title)

        return if (tmdbMovie != null && omdbMovie != null) buildMovie(tmdbMovie, omdbMovie)
            else tmdbMovie?.copy(overview = "TMDB: ${tmdbMovie.overview}") ?:
                omdbMovie?.copy(overview = "OMDB: ${omdbMovie.overview}")
    }

    private fun buildMovie(tmdbMovie: Movie, omdbMovie: Movie) = Movie(
        id = tmdbMovie.id,
        title = tmdbMovie.title,
        overview = "TMDB:  ${tmdbMovie.overview}\n\nOMDB: ${omdbMovie.overview}",
        releaseDate = tmdbMovie.releaseDate,
        poster = tmdbMovie.poster,
        backdrop = tmdbMovie.backdrop,
        originalTitle = tmdbMovie.originalTitle,
        originalLanguage = tmdbMovie.originalLanguage,
        popularity = (tmdbMovie.popularity + omdbMovie.popularity) / 2.0,
        voteAverage = (tmdbMovie.voteAverage + omdbMovie.voteAverage) / 2.0
    )

}