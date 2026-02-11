package gym.controller;

import gym.model.Trainer;
import gym.repository.TrainerRepository;
import gym.repository.impl.TrainerRepositoryImpl;

import java.util.List;

public class TrainerController {
    private TrainerRepository trainerRepository;

    public TrainerController() {
        this.trainerRepository = new TrainerRepositoryImpl();
    }

    public void addTrainer(String name, String specialization, String email, String phone) {
        Trainer trainer = new Trainer();

        trainer.setName(name);
        trainer.setSpecialization(specialization);
        trainer.setEmail(email);
        trainer.setPhone(phone);

        trainerRepository.create(trainer);

        System.out.println("Trainer added with ID: " + trainer.getId());
    }


    public Trainer getTrainer(int id) {
        return trainerRepository.findById(id);
    }


    public void displayAllTrainers() {
        List<Trainer> trainers = trainerRepository.findAll();

        if (trainers.isEmpty()) {
            System.out.println("No trainers found.");
            return;
        }

        System.out.println("\nTrainers:");
        System.out.println("ID | Name | Specialization | Email | Phone");
        System.out.println("------------------------------------------------");

        for (Trainer t : trainers) {
            System.out.println(t.getId() + " | " + t.getName() + " | " + 
                t.getSpecialization() + " | " + t.getEmail() + " | " + t.getPhone());
        }
    }


    public void updateTrainer(int id, String name, String specialization, String email, String phone) {
        Trainer trainer = trainerRepository.findById(id);

        if (trainer == null) {
            System.out.println("Trainer not found.");
            return;
        }

        trainer.setName(name);
        trainer.setSpecialization(specialization);
        trainer.setEmail(email);
        trainer.setPhone(phone);

        trainerRepository.update(trainer);

        System.out.println("Trainer updated successfully.");
    }


    public void deleteTrainer(int id) {
        trainerRepository.delete(id);

        System.out.println("Trainer deleted successfully.");
    }
}