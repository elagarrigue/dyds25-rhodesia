package edu.dyds.movies.data.external.omdb

import edu.dyds.movies.data.external.MoviesDetailsExternalSource
import edu.dyds.movies.domain.entity.Movie
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.get

class OMDBMoviesExternalSource(private val omdbHttpClient: HttpClient) : MoviesDetailsExternalSource {
    override suspend fun getMovieDetailsFromSource(title: String): Movie? =
        try {
            getOMDBMovieDetails(title).OMDBtoDomainMovie()
        } catch (_: Exception) {
            null
        }

    private suspend fun getOMDBMovieDetails(title: String) : OMDBRemoteResult =
        omdbHttpClient.get("/?t=$title").body()

}
