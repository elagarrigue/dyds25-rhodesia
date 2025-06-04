package edu.dyds.movies.data.external

interface ExternalSource {
    suspend fun getMovieDetailsFromSource(id: Int): RemoteMovie?
    suspend fun getPopularMoviesFromSource(): List<RemoteMovie>
}