package edu.dyds.movies.data.external

import edu.dyds.movies.data.RemoteMovie

interface ExternalSource {
    suspend fun getMovieDetailsFromSource(id: Int): RemoteMovie?
    suspend fun getPopularMoviesFromSource(): List<RemoteMovie>
}