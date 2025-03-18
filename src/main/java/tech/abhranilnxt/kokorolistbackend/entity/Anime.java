package tech.abhranilnxt.kokorolistbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "anime")
public class Anime {

    @Id
    @Column(name = "mal_id")
    private Long malId;

    @Column(name = "title")
    private String title;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "synopsis", columnDefinition = "TEXT")
    private String synopsis;

    @Column(name = "studio")
    private String studio;

    @Column(name = "year")
    private String year;

    @Column(name = "genres")
    private String genres;

    @Column(name = "episodes")
    private String episodes;

    @Column(name = "malscore")
    private Double malScore;

    @Column(name = "status")
    private String status;

    public Anime() {
    }

    public Anime(Long malId, String title, String imageUrl, String synopsis, String studio, String year, String genres, String episodes, Double malScore, String status) {
        this.malId = malId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.synopsis = synopsis;
        this.studio = studio;
        this.year = year;
        this.genres = genres;
        this.episodes = episodes;
        this.malScore = malScore;
        this.status = status;
    }

    public Long getMalId() {
        return malId;
    }

    public void setMalId(Long malId) {
        this.malId = malId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getEpisodes() {
        return episodes;
    }

    public void setEpisodes(String episodes) {
        this.episodes = episodes;
    }

    public Double getMalScore() {
        return malScore;
    }

    public void setMalScore(Double malScore) {
        this.malScore = malScore;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Anime{" +
                "malId=" + malId +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", studio='" + studio + '\'' +
                ", year='" + year + '\'' +
                ", genres='" + genres + '\'' +
                ", episodes='" + episodes + '\'' +
                ", malScore=" + malScore +
                ", status='" + status + '\'' +
                '}';
    }
}

