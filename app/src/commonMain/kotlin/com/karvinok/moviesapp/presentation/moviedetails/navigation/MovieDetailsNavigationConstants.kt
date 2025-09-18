package com.karvinok.moviesapp.presentation.moviedetails.navigation

object MovieDetailsNavigationConstants {
    internal const val ROUTE_MOVIE_DETAILS = "movie_details"

    internal const val ARG_MOVIE_ID = "movieId"
    internal const val ARG_MOVIE_TITLE = "movieTitle"
    internal const val ARG_MOVIE_POSTER_PATH = "moviePosterPath"

    internal const val ROUTE_MOVIE_DETAILS_WITH_ARGS =
        "$ROUTE_MOVIE_DETAILS/{$ARG_MOVIE_ID}/{$ARG_MOVIE_TITLE}?$ARG_MOVIE_POSTER_PATH={$ARG_MOVIE_POSTER_PATH}"

    /**
     * TODO improve: creation of the navigation route could be managed by route compositor
     *  which will provide easy-to-use and compact API to declare nav routes with NavArguments
     *
     *  val planSelectionNavigation = ArgumentNavigationCompositor.build {
     *     route = ROUTE_PLAN_SELECTION
     *     arguments = listOf(
     *         ARG_MESSAGE_TYPE to NavType.StringType
     *     )
     * }
     *
     * animatedComposable(planSelectionNavigation) {
     *     PlanSelectionScreen(getViewModel())
     * }
     *
     * navigator.navigateTo(
     *     planSelectionNavigation.buildRoute(
     *         PlanNavigationConstants.ARG_MESSAGE_TYPE with messageType
     *     )
     * )
     *
     */
    fun createMovieDetailsRoute(
        movieId: Int,
        movieTitle: String,
        moviePosterPath: String?
    ): String {
        val encodedTitle = movieTitle.replace("/", "%2F")
        return if (moviePosterPath != null) {
            "$ROUTE_MOVIE_DETAILS/$movieId/$encodedTitle?$ARG_MOVIE_POSTER_PATH=$moviePosterPath"
        } else {
            "$ROUTE_MOVIE_DETAILS/$movieId/$encodedTitle"
        }
    }
}
