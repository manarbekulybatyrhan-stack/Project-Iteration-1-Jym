package gym.repository.impl;

import gym.config.DatabaseConfig;
import gym.model.Membership;
import gym.repository.MembershipRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembershipRepositoryImpl implements MembershipRepository {
    @Override
    public void create(Membership membership) {
        String sql = "INSERT INTO memberships (type, duration_months, price) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, membership.getType());
            stmt.setInt(2, membership.getDurationMonths());
            stmt.setDouble(3, membership.getPrice());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                membership.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Membership findById(int id) {
        String sql = "SELECT * FROM memberships WHERE id = ?";

        Membership membership = null;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                membership = new Membership(
                    rs.getInt("id"),
                    rs.getString("type"),
                    rs.getInt("duration_months"),
                    rs.getDouble("price")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return membership;
    }


    @Override
    public List<Membership> findAll() {
        String sql = "SELECT * FROM memberships";

        List<Membership> memberships = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Membership membership = new Membership(
                    rs.getInt("id"),
                    rs.getString("type"),
                    rs.getInt("duration_months"),
                    rs.getDouble("price")
                );

                memberships.add(membership);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return memberships;
    }


    @Override
    public void update(Membership membership) {
        String sql = "UPDATE memberships SET type = ?, duration_months = ?, price = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, membership.getType());
            stmt.setInt(2, membership.getDurationMonths());
            stmt.setDouble(3, membership.getPrice());
            stmt.setInt(4, membership.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void delete(int id) {
        String sql = "DELETE FROM memberships WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}