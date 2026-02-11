package gym.model;

import java.time.LocalDateTime;

public class TrainingSession {
    private int id;
    private int memberId;
    private int trainerId;
    private LocalDateTime sessionDate;
    private int durationMinutes;
    private String type;
    private SessionCategory category; 

    public TrainingSession() {}

    // === ВСТАВИТЬ ЭТО В ФАЙЛ TrainingSession.java ===

    public TrainingSession(int id, int memberId, int trainerId, java.time.LocalDateTime sessionDate, int durationMinutes, String type) {
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

    // Алиас для репозитория (чтобы работали старые вызовы)
    public LocalDateTime getDateTime() {
        return this.sessionDate; 
    }

    public LocalDateTime getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(LocalDateTime sessionDate) {
        this.sessionDate = sessionDate;
    }

    // Алиас для репозитория
    public int getDuration() {
        return this.durationMinutes; 
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

    public SessionCategory getCategory() {
        return category;
    }

    public void setCategory(SessionCategory category) {
        this.category = category;
    }
}