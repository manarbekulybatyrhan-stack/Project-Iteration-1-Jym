package gym.repository.impl;

import gym.config.DatabaseConfig;
import gym.dto.FullTrainingSessionDTO;
import gym.model.Member;
import gym.model.Membership;
import gym.model.SessionCategory;
import gym.model.Trainer;
import gym.model.TrainingSession;
import gym.repository.MemberRepository;
import gym.repository.MembershipRepository;
import gym.repository.TrainerRepository;
import gym.repository.TrainingSessionRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TrainingSessionRepositoryImpl implements TrainingSessionRepository {

    @Override
    public void add(TrainingSession session) {
        create(session);
    }

    @Override
    public int getNextId() {
        String sql = "SELECT MAX(id) FROM training_sessions";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    @Override
    public void create(TrainingSession session) {
        String sql = "INSERT INTO training_sessions (member_id, trainer_id, session_date, duration_minutes, type, category) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, session.getMemberId());
            stmt.setInt(2, session.getTrainerId());
            stmt.setTimestamp(3, Timestamp.valueOf(session.getSessionDate()));
            stmt.setInt(4, session.getDurationMinutes());
            stmt.setString(5, session.getType());
            stmt.setString(6, session.getCategory() == null ? null : session.getCategory().name());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    session.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TrainingSession findById(int id) {
        String sql = "SELECT * FROM training_sessions WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToTrainingSession(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TrainingSession> findAll() {
        String sql = "SELECT * FROM training_sessions";
        List<TrainingSession> sessions = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                sessions.add(mapRowToTrainingSession(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessions;
    }

    @Override
    public void update(TrainingSession session) {
        String sql = "UPDATE training_sessions SET member_id = ?, trainer_id = ?, session_date = ?, duration_minutes = ?, type = ?, category = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, session.getMemberId());
            stmt.setInt(2, session.getTrainerId());
            stmt.setTimestamp(3, Timestamp.valueOf(session.getSessionDate()));
            stmt.setInt(4, session.getDurationMinutes());
            stmt.setString(5, session.getType());
            stmt.setString(6, session.getCategory() == null ? null : session.getCategory().name());
            stmt.setInt(7, session.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
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

    @Override
    public List<TrainingSession> findByMemberId(int memberId) {
        String sql = "SELECT * FROM training_sessions WHERE member_id = ?";
        List<TrainingSession> sessions = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, memberId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    sessions.add(mapRowToTrainingSession(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessions;
    }

    @Override
    public List<TrainingSession> findByTrainerId(int trainerId) {
        String sql = "SELECT * FROM training_sessions WHERE trainer_id = ?";
        List<TrainingSession> sessions = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, trainerId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    sessions.add(mapRowToTrainingSession(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessions;
    }

    @Override
    public FullTrainingSessionDTO getFullSessionDescription(int sessionId) {
        TrainingSession session = findById(sessionId);
        if (session == null) return null;

        MemberRepository memberRepo = new MemberRepositoryImpl();
        Member member = memberRepo.findById(session.getMemberId());
        if (member == null) return null;

        TrainerRepository trainerRepo = new TrainerRepositoryImpl();
        Trainer trainer = trainerRepo.findById(session.getTrainerId());
        if (trainer == null) return null;

        MembershipRepository membershipRepo = new MembershipRepositoryImpl();
        Membership membership = membershipRepo.findById(member.getMembershipId());
        if (membership == null) return null;

        return new FullTrainingSessionDTO(
                session.getId(),
                member.getId(), member.getName(), member.getEmail(),
                trainer.getId(), trainer.getName(), trainer.getSpecialization(),
                membership.getId(), membership.getType(),
                session.getSessionDate(), session.getDurationMinutes(), session.getType()
        );
    }

    @Override
    public List<TrainingSession> getSessionsByType(String type) {
        String sql = "SELECT * FROM training_sessions WHERE type = ? ORDER BY session_date";
        List<TrainingSession> sessions = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, type);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    sessions.add(mapRowToTrainingSession(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessions;
    }

    private TrainingSession mapRowToTrainingSession(ResultSet rs) throws SQLException {
        String categoryStr = rs.getString("category");
        SessionCategory category = categoryStr == null ? null : SessionCategory.valueOf(categoryStr);

        return new TrainingSession(
                rs.getInt("id"),
                rs.getInt("member_id"),
                rs.getInt("trainer_id"),
                rs.getTimestamp("session_date").toLocalDateTime(),
                rs.getInt("duration_minutes"),
                rs.getString("type"),
                category
        );
    }
}
