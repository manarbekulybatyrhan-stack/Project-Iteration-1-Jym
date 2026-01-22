package gym.model;

import java.time.LocalDateTime;

public class TrainingSession {
    private int id;
    private int memberId;
    private int trainerId;
    private LocalDateTime sessionDate;
    private int durationMinutes;
    private String type;

    public TrainingSession() {}

    public TrainingSession(int id, int memberId, int trainerId, LocalDateTime sessionDate, int durationMinutes, String type) {
        this.id = id;
        this.memberId = memberId;
        this.trainerId = trainerId;
        this.sessionDate = sessionDate;
        this.durationMinutes = durationMinutes;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    public LocalDateTime getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(LocalDateTime sessionDate) {
        this.sessionDate = sessionDate;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}