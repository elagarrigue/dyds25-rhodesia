package edu.dyds.movies.data.external

class MoviesExternalSourceBroker(
    private val moviesTmdbExternalSource: MoviesExternalSource,
    private val moviesOmdbExternalSource: MoviesExternalSource)
    : MoviesExternalSource{

}