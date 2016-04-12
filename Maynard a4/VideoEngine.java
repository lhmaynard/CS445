
package videoengine;

/**
 * This abstract data type is a predictive engine for video ratings in a streaming video system. It
 * stores a set of users, a set of videos, and a set of ratings that users have assigned to videos.
 */
public interface VideoEngine {

    /**
     * Adds a new video to the system. If this video is not already in the system, it will be added.
     * If this video is already in the system, this method will throw an IllegalArgumentException.
     *
     * @param theVideo video to be added to the system
     * @throws IllegalArgumentException if theVideo is already in the system
     * @throws NullPointerException if theVideo is null
     */
    public void addVideo(Video theVideo);

    /**
     * Removes an existing video from the system. If this Video is in the system, it
     * will be removed and appear as though it were never there. If this video is
     * not in the system, this method will throw an IllegalArgumentException.
     *
     * @param theVideo the video to be removed
     * @throws IllegalArgumentException if the given Video is not in the system
     * @throws NullPointerException if theVideo is null
     */
    public void removeVideo(Video theVideo);

    /**
     * Adds an existing television episode to an existing television series. If theEpisode is a TvEpisode
     * in the system and theSeries is a TvSeries in the system, theEpisode will be added to theSeries.
     * An IllegalArgumentException will be thrown if either theEpisode or theSeries are not in the system,
     * or if theEpisode is already part of another TvSeries
     *
     * @param theEpisode the TvEpisode to be added
     * @param theSeries the TvSeries to add theEpisode to
     * @throws IllegalArgumentException if theEpisode or theSeries are not already in the system
     * @throws IllegalArgumentException if theEpisode is already part of another TvSeries
     * @throws NullPointerException if either theEpisode or theSeries are null
     */
    public void addToSeries(TvEpisode theEpisode, TvSeries theSeries);

    /**
     * Removes a television episode from a television series. If theEpisode is part of a TvSeries,
     * it will be removed from that TvSeries. theEpisode will still be in the system, but it will
     * not be part of a TvSeries. An IllegalArgumentException will be thrown if theEpisode is not
     * part of a TvSeries, or if theEpisode is not already in the system.
     *
     * @param theEpisode the TvEpisode to be removed from its TvSeries
     * @throws IllegalArgumentExcepton if theEpisode is not part of a TvSeries
     * @throws IllegalArgumentExcepton if theEpisode is not already in the system
     * @throws NullPointerException if theEpisode is null
     */
    public void removeFromSeries(TvEpisode theEpisode);

    /**
     * Sets a user's rating for a video, as a number of stars from 1 to 5. If the user already has a rating
     * for the video, the old rating will be overwritten with the new one. If the rating given is outside of the
     * required range, or if theUser or theVideo are not in the system, an IllegalArgumentException will be thrown
     *
     * @param theUser the User that is rating theVideo
     * @param theVideo the Video that theUser is rating
     * @param rating an integer rating between 1 and 5 inclusive that theUser is givng theVideo
     * @throws IllegalArgumentException if rating is a number outside of the required range of 1 to 5
     * @throws IllegalArgumentException if theUser or theVideo are not already in the system
     * @throws NullPointerException if either theUser or theVideo are null
     */
    public void rateVideo(User theUser, Video theVideo, int rating);

    /**
     * Clears a user's rating on a video. If this user has rated this video and the rating has not
     * already been cleared, then the rating is cleared and the state will appear as if the rating
     * was never made. If this user has not rated this video, or if the rating has already been
     * cleared, then this method will throw an IllegalArgumentException.
     *
     * @param theUser user whose rating should be cleared
     * @param theVideo video from which the user's rating should be cleared
     * @throws IllegalArgumentException if the user does not currently have a rating on record for
     * the video
     * @throws NullPointerException if either theUser or theVideo are null
     */
    public void clearRating(User theUser, Video theVideo);

    /**
     * Predicts the rating a user will assign to a video that they have not yet rated, as a number
     * of stars from 1 to 5. The rating will be determined by looking at theUser's friends' ratings of theVideo.
     *
     * @param theUser the User to predict a rating for
     * @param theVideo the Video to predict a rating for
     * @return the predicted integer rating from 1 to 5 inclusive
     * @throws NullPointerException if either the user or the video is null
     * @throws IllegalArgumentException if either theUser or theVideo are not in the system
     * @throws IllegalArgumentException if none of theUser's friends have rated theVideo
     *
     */
    public int predictRating(User theUser, Video theVideo);

    /**
     * Suggests a video for a user based on their predicted rating. A video with a high predicted rating
     * will be returned.
     *
     * @param theUser the User to suggest a video for
     * @return the suggested Video
     * @throws NullPointerException if no videos are in the system
     * @throws NullPointerException if theUser is null
     */
    public Video suggestVideo(User theUser);


}

