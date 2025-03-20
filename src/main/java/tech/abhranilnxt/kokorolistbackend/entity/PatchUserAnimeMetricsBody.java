package tech.abhranilnxt.kokorolistbackend.entity;

import java.time.LocalDateTime;

public class PatchUserAnimeMetricsBody {
    private Integer personalRating;
    private LocalDateTime startedWatching;
    private LocalDateTime finishedWatching;
    private String notes;

    public Integer getPersonalRating() {
        return personalRating;
    }

    public void setPersonalRating(Integer personalRating) {
        this.personalRating = personalRating;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "UpdateUserAnimeMetricsRequest{" +
                "personalRating=" + personalRating +
                ", startedWatching=" + startedWatching +
                ", finishedWatching=" + finishedWatching +
                ", notes='" + notes + '\'' +
                '}';
    }
}
