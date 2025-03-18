package tech.abhranilnxt.kokorolistbackend.entity;

import java.time.LocalDateTime;

public class WatchlistItemDTO {

    private String watchlistId;
    private String title;
    private String imageUrl;
    private String studio;
    private Integer personalRating;
    private LocalDateTime startedWatching;
    private LocalDateTime finishedWatching;

    public WatchlistItemDTO(String watchlistId, String title, String imageUrl, String studio,
                            Integer personalRating, LocalDateTime startedWatching, LocalDateTime finishedWatching) {
        this.watchlistId = watchlistId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.studio = studio;
        this.personalRating = personalRating;
        this.startedWatching = startedWatching;
        this.finishedWatching = finishedWatching;
    }

    // Getters and Setters
    public String getWatchlistId() {
        return watchlistId;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getStudio() {
        return studio;
    }

    public Integer getPersonalRating() {
        return personalRating;
    }

    public LocalDateTime getStartedWatching() {
        return startedWatching;
    }

    public LocalDateTime getFinishedWatching() {
        return finishedWatching;
    }

    @Override
    public String toString() {
        return "WatchlistItemDTO{" +
                "watchlistId='" + watchlistId + '\'' +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", studio='" + studio + '\'' +
                ", personalRating=" + personalRating +
                ", startedWatching=" + startedWatching +
                ", finishedWatching=" + finishedWatching +
                '}';
    }
}


