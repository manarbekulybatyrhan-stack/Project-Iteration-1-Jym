package gym.dto;

import java.time.LocalDateTime;


public class FullTrainingSessionDTO {
    private int sessionId;
    private int memberId;
    private String memberName;
    private String memberEmail;
    
    private int trainerId;
    private String trainerName;
    private String trainerSpec;
    
    private int membershipId;
    private String membershipType;
    
    private LocalDateTime sessionDateTime;
    private int duration;
    private String sessionType;

    public FullTrainingSessionDTO(
            int sessionId, 
            int memberId, String memberName, String memberEmail,
            int trainerId, String trainerName, String trainerSpec,
            int membershipId, String membershipType,
            LocalDateTime sessionDateTime, int duration, String sessionType) {
        
        this.sessionId = sessionId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.trainerId = trainerId;
        this.trainerName = trainerName;
        this.trainerSpec = trainerSpec;
        this.membershipId = membershipId;
        this.membershipType = membershipType;
        this.sessionDateTime = sessionDateTime;
        this.duration = duration;
        this.sessionType = sessionType;
    }

    @Override
    public String toString() {
        return String.format(
            "Session ID: %d\n" +
            "Member: %s (%s)\n" +
            "Trainer: %s (Specialization: %s)\n" +
            "Membership: %s\n" +
            "Date/Time: %s\n" +
            "Duration: %d minutes\n" +
            "Type: %s",
            sessionId, memberName, memberEmail,
            trainerName, trainerSpec,
            membershipType,
            sessionDateTime, duration, sessionType
        );
    }

    // Getters
    public int getSessionId() { return sessionId; }
    public int getMemberId() { return memberId; }
    public String getMemberName() { return memberName; }
    public String getMemberEmail() { return memberEmail; }
    public int getTrainerId() { return trainerId; }
    public String getTrainerName() { return trainerName; }
    public String getTrainerSpec() { return trainerSpec; }
    public int getMembershipId() { return membershipId; }
    public String getMembershipType() { return membershipType; }
    public LocalDateTime getSessionDateTime() { return sessionDateTime; }
    public int getDuration() { return duration; }
    public String getSessionType() { return sessionType; }
}
