package com.karvinok.moviesapp.presentation.movies

import com.karvinok.moviesapp.core.base.BaseRouter
import com.karvinok.moviesapp.core.navigation.Navigator
import com.karvinok.moviesapp.domain.movies.model.Movie
import com.karvinok.moviesapp.presentation.moviedetails.navigation.MovieDetailsNavKey

internal class MoviesRouter(
    private val navigator: Navigator
) : BaseRouter(navigator) {

    fun navigateToMovieDetails(movie: Movie) {
        navigator.navigate(
            MovieDetailsNavKey(
                movieId = movie.id,
                movieTitle = movie.title,
                moviePosterPath = movie.posterPath
            )
        )
    }
}
