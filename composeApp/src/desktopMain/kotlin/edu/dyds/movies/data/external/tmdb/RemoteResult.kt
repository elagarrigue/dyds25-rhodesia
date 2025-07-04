package edu.dyds.movies.data.external.tmdb

import edu.dyds.movies.domain.entity.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TMDBRemoteResult(
    val page: Int,
    val results: List<RemoteMovie>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)


