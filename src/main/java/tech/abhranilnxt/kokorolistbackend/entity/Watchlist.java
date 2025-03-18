package tech.abhranilnxt.kokorolistbackend.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "watchlist")
public class Watchlist {

    @Id
    @Column(name = "watchlist_id", length = 36, updatable = false, nullable = false)
    private String watchlistId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "mal_id", referencedColumnName = "mal_id", nullable = false)
    private Anime anime;

    @OneToOne(mappedBy = "watchlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserAnimeMetrics userMetrics;

    public Watchlist() {
        this.watchlistId = UUID.randomUUID().toString();
    }

    public Watchlist(User user, Anime anime) {
        this.watchlistId = UUID.randomUUID().toString();
        this.user = user;
        this.anime = anime;
    }

    // Getters and Setters...

    public String getWatchlistId() {
        return watchlistId;
    }

    public void setWatchlistId(String watchlistId) {
        this.watchlistId = watchlistId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Anime getAnime() {
        return anime;
    }

    public void setAnime(Anime anime) {
        this.anime = anime;
    }

    public UserAnimeMetrics getUserMetrics() {
        return userMetrics;
    }

    public void setUserMetrics(UserAnimeMetrics userMetrics) {
        this.userMetrics = userMetrics;
    }

    @Override
    public String toString() {
        return "Watchlist{" +
                "watchlistId='" + watchlistId + '\'' +
                ", user=" + user +
                ", anime=" + anime +
                ", userMetrics=" + userMetrics +
                '}';
    }
}

