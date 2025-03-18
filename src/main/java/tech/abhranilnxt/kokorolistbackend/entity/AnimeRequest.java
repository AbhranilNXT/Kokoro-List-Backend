package tech.abhranilnxt.kokorolistbackend.entity;

public class AnimeRequest {
    private Long malId;
    private String title;
    private String imageUrl;
    private String synopsis;
    private String studio;
    private String year;
    private String genres;
    private String episodes;
    private Double malScore;
    private String status;

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
        return "AnimeRequest{" +
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
