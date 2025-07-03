package edu.dyds.movies.data.external

import edu.dyds.movies.domain.entity.Movie

interface DetailedMovieExternalSource{
    suspend fun getMovieDetailsFromSource(title : String): Movie
}