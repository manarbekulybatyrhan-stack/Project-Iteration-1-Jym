package gym.repository.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import gym.config.DatabaseConfig;
import gym.model.Member;
import gym.repository.MemberRepository;

public class MemberRepositoryImpl implements MemberRepository {

    @Override
    public void add(Member member) {
        create(member);
    }

    @Override
    public int getNextId() {
        return findAll().stream().mapToInt(Member::getId).max().orElse(0) + 1;
    }

    @Override
    public void create(Member member) {
        String sql = "INSERT INTO members (name, email, phone, membership_id, join_date, expiry_date) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, member.getName());
            stmt.setString(2, member.getEmail());
            stmt.setString(3, member.getPhone());
            stmt.setInt(4, member.getMembershipId());
            stmt.setDate(5, Date.valueOf(member.getJoinDate()));
            stmt.setDate(6, Date.valueOf(member.getExpiryDate()));

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    member.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Member findById(int id) {
        String sql = "SELECT * FROM members WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Member(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getInt("membership_id"),
                            rs.getDate("join_date").toLocalDate(),
                            rs.getDate("expiry_date").toLocalDate()
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Member> findAll() {
        String sql = "SELECT * FROM members";
        List<Member> members = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                members.add(new Member(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getInt("membership_id"),
                        rs.getDate("join_date").toLocalDate(),
                        rs.getDate("expiry_date").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }

    @Override
    public void update(Member member) {
        String sql = "UPDATE members SET name = ?, email = ?, phone = ?, membership_id = ?, join_date = ?, expiry_date = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, member.getName());
            stmt.setString(2, member.getEmail());
            stmt.setString(3, member.getPhone());
            stmt.setInt(4, member.getMembershipId());
            stmt.setDate(5, Date.valueOf(member.getJoinDate()));
            stmt.setDate(6, Date.valueOf(member.getExpiryDate()));
            stmt.setInt(7, member.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM members WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Member> findActiveMembers() {
        String sql = "SELECT * FROM members WHERE expiry_date >= CURRENT_DATE";
        List<Member> members = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                members.add(new Member(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getInt("membership_id"),
                        rs.getDate("join_date").toLocalDate(),
                        rs.getDate("expiry_date").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }

    @Override
    public List<Member> getValidatedMembers() {
        return findAll().stream()
                .filter(m -> {
                    try {
                        return m.isValidEmail() && m.isValidPhone();
                    } catch (Exception e) {
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Member> getActiveMembers() {
        return findAll().stream()
                .filter(Member::isActive)
                .sorted((m1, m2) -> m1.getName().compareTo(m2.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Member findByEmail(String email) {
        String sql = "SELECT * FROM members WHERE LOWER(email) = LOWER(?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Member(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getInt("membership_id"),
                            rs.getDate("join_date").toLocalDate(),
                            rs.getDate("expiry_date").toLocalDate()
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
