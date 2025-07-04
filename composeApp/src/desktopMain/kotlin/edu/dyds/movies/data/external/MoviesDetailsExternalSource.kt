package edu.dyds.movies.data.external

import edu.dyds.movies.domain.entity.Movie

interface MoviesDetailsExternalSource{
    suspend fun getMovieDetailsFromSource(title : String): Movie
}