package gym.repository;

import gym.config.DatabaseConfig;
import gym.model.Trainer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainerRepository {
    public void create(Trainer trainer) {
        String sql = "INSERT INTO trainers (name, specialization, email, phone) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, trainer.getName());
            stmt.setString(2, trainer.getSpecialization());
            stmt.setString(3, trainer.getEmail());
            stmt.setString(4, trainer.getPhone());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                trainer.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Trainer findById(int id) {
        String sql = "SELECT * FROM trainers WHERE id = ?";
        Trainer trainer = null;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                trainer = new Trainer(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("specialization"),
                    rs.getString("email"),
                    rs.getString("phone")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trainer;
    }


    public List<Trainer> findAll() {
        String sql = "SELECT * FROM trainers";
        List<Trainer> trainers = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Trainer trainer = new Trainer(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("specialization"),
                    rs.getString("email"),
                    rs.getString("phone")
                );
                trainers.add(trainer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trainers;
    }


    public void update(Trainer trainer) {
        String sql = "UPDATE trainers SET name = ?, specialization = ?, email = ?, phone = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, trainer.getName());
            stmt.setString(2, trainer.getSpecialization());
            stmt.setString(3, trainer.getEmail());
            stmt.setString(4, trainer.getPhone());
            stmt.setInt(5, trainer.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void delete(int id) {
        String sql = "DELETE FROM trainers WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}