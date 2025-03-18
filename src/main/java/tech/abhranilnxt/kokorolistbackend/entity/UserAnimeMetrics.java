package tech.abhranilnxt.kokorolistbackend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_anime_metrics")
public class UserAnimeMetrics {

    @Id
    @Column(name = "metrics_id", length = 36, updatable = false, nullable = false)
    private String id;

    @OneToOne
    @JoinColumn(name = "watchlist_id", nullable = false, unique = true)
    private Watchlist watchlist;

    @Column(name = "started_watching")
    private LocalDateTime startedWatching;

    @Column(name = "finished_watching")
    private LocalDateTime finishedWatching;

    @Column(name = "personal_rating")
    private Integer personalRating;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    public UserAnimeMetrics() {
        this.id = UUID.randomUUID().toString();
    }

    public UserAnimeMetrics(Watchlist watchlist, LocalDateTime startedWatching, LocalDateTime finishedWatching, Integer personalRating, String notes) {
        this.id = UUID.randomUUID().toString();
        this.watchlist = watchlist;
        this.startedWatching = startedWatching;
        this.finishedWatching = finishedWatching;
        this.personalRating = personalRating;
        this.notes = notes;
    }

    // Getters and Setters...

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Watchlist getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(Watchlist watchlist) {
        this.watchlist = watchlist;
    }

    public LocalDateTime getStartedWatching() {
        return startedWatching;
    }

    public void setStartedWatching(LocalDateTime startedWatching) {
        this.startedWatching = startedWatching;
    }

    public LocalDateTime getFinishedWatching() {
        return finishedWatching;
    }

    public void setFinishedWatching(LocalDateTime finishedWatching) {
        this.finishedWatching = finishedWatching;
    }

    public Integer getPersonalRating() {
        return personalRating;
    }

    public void setPersonalRating(Integer personalRating) {
        this.personalRating = personalRating;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "UserAnimeMetrics{" +
                "id='" + id + '\'' +
                ", watchlist=" + watchlist +
                ", startedWatching=" + startedWatching +
                ", finishedWatching=" + finishedWatching +
                ", personalRating=" + personalRating +
                ", notes='" + notes + '\'' +
                '}';
    }
}

