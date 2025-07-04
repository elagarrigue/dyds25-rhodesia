package edu.dyds.movies.data.external.omdb

import edu.dyds.movies.domain.entity.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteMovie (
    @SerialName("Title") val title: String,
    @SerialName("Plot") val plot: String,
    @SerialName("Released") val released: String,
    @SerialName("Year") val year: String,
    @SerialName("Poster") val poster: String,
    @SerialName("Language") val language: String,
    @SerialName("Metascore") val metaScore: String,
    val imdbRating: Double
) {
    fun toDomainMovie() : Movie {
        return Movie(
            id = title.hashCode(),
            title = title,
            overview = plot,
            releaseDate = if (released.isNotEmpty() && released != "N/A") released else year,
            poster = poster,
            backdrop = poster,
            originalTitle = title,
            originalLanguage = language,
            popularity = imdbRating,
            voteAverage = if (metaScore.isNotEmpty() && metaScore != "N/A") metaScore.toDouble() else 0.0
        )
    }
}
