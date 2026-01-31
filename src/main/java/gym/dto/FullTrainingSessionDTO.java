package gym.dto;

public class FullTrainingSessionDTO {

    private int sessionId;
    private String memberName;
    private String trainerName;
    private String membershipType;
    private String sessionCategory;

    public FullTrainingSessionDTO(int sessionId,
                                  String memberName,
                                  String trainerName,
                                  String membershipType,
                                  String sessionCategory) {
        this.sessionId = sessionId;
        this.memberName = memberName;
        this.trainerName = trainerName;
        this.membershipType = membershipType;
        this.sessionCategory = sessionCategory;
    }

    public int getSessionId() {
        return sessionId;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public String getSessionCategory() {
        return sessionCategory;
    }
}
