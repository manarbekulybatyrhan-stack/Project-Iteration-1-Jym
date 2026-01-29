package gym.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import gym.config.DatabaseConfig;
import gym.dto.FullTrainingSessionDTO;
import gym.model.Member;
import gym.model.Membership;
import gym.model.Trainer;
import gym.model.TrainingSession;

public class TrainingSessionRepository {
    private List<TrainingSession> sessions = new ArrayList<>();

    public void add(TrainingSession session) {
        create(session);
    }

    public int getNextId() {
        return findAll().stream().mapToInt(TrainingSession::getId).max().orElse(0) + 1;
    }

    public void create(TrainingSession session) {
        String sql = "INSERT INTO training_sessions (member_id, trainer_id, session_date, duration_minutes, type) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, session.getMemberId());
            stmt.setInt(2, session.getTrainerId());
            stmt.setTimestamp(3, Timestamp.valueOf(session.getSessionDate()));
            stmt.setInt(4, session.getDurationMinutes());
            stmt.setString(5, session.getType());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                session.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public TrainingSession findById(int id) {
        String sql = "SELECT * FROM training_sessions WHERE id = ?";

        TrainingSession session = null;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                session = new TrainingSession(
                    rs.getInt("id"),
                    rs.getInt("member_id"),
                    rs.getInt("trainer_id"),
                    rs.getTimestamp("session_date").toLocalDateTime(),
                    rs.getInt("duration_minutes"),
                    rs.getString("type")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return session;
    }


    public List<TrainingSession> findAll() {
        String sql = "SELECT * FROM training_sessions";
        
        List<TrainingSession> sessions = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                TrainingSession session = new TrainingSession(
                    rs.getInt("id"),
                    rs.getInt("member_id"),
                    rs.getInt("trainer_id"),
                    rs.getTimestamp("session_date").toLocalDateTime(),
                    rs.getInt("duration_minutes"),
                    rs.getString("type")
                );

                sessions.add(session);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessions;
    }


    public void update(TrainingSession session) {
        String sql = "UPDATE training_sessions SET member_id = ?, trainer_id = ?, session_date = ?, duration_minutes = ?, type = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, session.getMemberId());
            stmt.setInt(2, session.getTrainerId());
            stmt.setTimestamp(3, Timestamp.valueOf(session.getSessionDate()));
            stmt.setInt(4, session.getDurationMinutes());
            stmt.setString(5, session.getType());
            stmt.setInt(6, session.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void delete(int id) {
        String sql = "DELETE FROM training_sessions WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<TrainingSession> findByMemberId(int memberId) {
        String sql = "SELECT * FROM training_sessions WHERE member_id = ?";

        List<TrainingSession> sessions = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                TrainingSession session = new TrainingSession(
                    rs.getInt("id"),
                    rs.getInt("member_id"),
                    rs.getInt("trainer_id"),
                    rs.getTimestamp("session_date").toLocalDateTime(),
                    rs.getInt("duration_minutes"),
                    rs.getString("type")
                );

                sessions.add(session);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sessions;
    }


    public List<TrainingSession> findByTrainerId(int trainerId) {
        String sql = "SELECT * FROM training_sessions WHERE trainer_id = ?";
        List<TrainingSession> sessions = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, trainerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                TrainingSession session = new TrainingSession(
                    rs.getInt("id"),
                    rs.getInt("member_id"),
                    rs.getInt("trainer_id"),
                    rs.getTimestamp("session_date").toLocalDateTime(),
                    rs.getInt("duration_minutes"),
                    rs.getString("type")
                );
                sessions.add(session);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sessions;
    }


    public FullTrainingSessionDTO getFullSessionDescription(int sessionId) {
        // Find session
        TrainingSession session = sessions.stream()
                .filter(s -> s.getId() == sessionId)
                .findFirst()
                .orElse(null);
        
        if (session == null) return null;
        
        // JOIN 1: Get Member details
        MemberRepository memberRepo = new MemberRepository();
        Member member = memberRepo.findById(session.getMemberId()); 

        // JOIN 2: Get Trainer details
        TrainerRepository trainerRepo = new TrainerRepository();
        Trainer trainer = trainerRepo.findById(session.getTrainerId());
        
        // JOIN 3: Get Membership details
        MembershipRepository membershipRepo = new MembershipRepository();
        Membership membership = membershipRepo.findById(member.getMembershipId());
        
        // Combine all 4 tables into DTO
        return new FullTrainingSessionDTO(
            session.getId(),
            member.getId(), member.getName(), member.getEmail(),
            trainer.getId(), trainer.getName(), trainer.getSpecialization(),
            membership.getId(), membership.getType(),
            session.getDateTime(), session.getDuration(), session.getType()
        );
    }

    // Lambda: Get sessions by type with filter
    public List<TrainingSession> getSessionsByType(String type) {
        return sessions.stream()
            .filter(s -> s.getType().equalsIgnoreCase(type))
            .sorted((s1, s2) -> s1.getDateTime().compareTo(s2.getDateTime()))
            .collect(Collectors.toList());
    }
}
