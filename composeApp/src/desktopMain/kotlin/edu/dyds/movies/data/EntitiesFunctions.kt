package edu.dyds.movies.data

import edu.dyds.movies.domain.entity.Movie

fun RemoteMovie.toDomainMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        poster = "https://image.tmdb.org/t/p/w185$posterPath",
        backdrop = backdropPath.let { "https://image.tmdb.org/t/p/w780$it" },
        originalTitle = originalTitle,
        originalLanguage = originalLanguage,
        popularity = popularity,
        voteAverage = voteAverage
    )
}