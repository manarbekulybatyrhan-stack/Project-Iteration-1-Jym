package gym.controller;

import gym.model.Membership;
import gym.repository.MembershipRepository;
import gym.repository.impl.MembershipRepositoryImpl;
import gym.validation.ValidationService;

import java.util.List;

public class MembershipController {
    private MembershipRepository membershipRepository;

    public MembershipController() {
        this.membershipRepository = new MembershipRepositoryImpl();
    }

    public void addMembership(String type, String category, int durationMonths, double price) {
        try {
            ValidationService.getInstance().validateName(type);
            ValidationService.getInstance().validatePrice(price);

            Membership membership = new Membership();
            membership.setType(type);
            membership.setCategory(category);
            membership.setDurationMonths(durationMonths);
            membership.setPrice(price);

            membershipRepository.create(membership);
            System.out.println("Membership added with ID: " + membership.getId());
        } catch (Exception e) {
            System.out.println("Validation Error: " + e.getMessage());
        }
    }

    public Membership getMembership(int id) {
        return membershipRepository.findById(id);
    }

    public void displayAllMemberships() {
        List<Membership> memberships = membershipRepository.findAll();

        if (memberships.isEmpty()) {
            System.out.println("No memberships found.");
            return;
        }

        System.out.println("\nMemberships:");
        System.out.println("ID | Type | Category | Duration (months) | Price");
        System.out.println("------------------------------------------------");

        for (Membership m : memberships) {
            System.out.println(m.getId() + " | " + m.getType() + " | " +
                    m.getCategory() + " | " + m.getDurationMonths() + " | $" + m.getPrice());
        }
    }

    public void updateMembership(int id, String type, String category, int durationMonths, double price) {
        try {
            Membership membership = membershipRepository.findById(id);

            if (membership == null) {
                System.out.println("Membership not found.");
                return;
            }

            ValidationService.getInstance().validateName(type);
            ValidationService.getInstance().validatePrice(price);

            membership.setType(type);
            membership.setCategory(category);
            membership.setDurationMonths(durationMonths);
            membership.setPrice(price);

            membershipRepository.update(membership);
            System.out.println("Membership updated successfully.");
        } catch (Exception e) {
            System.out.println("Validation Error: " + e.getMessage());
        }
    }

    public void deleteMembership(int id) {
        membershipRepository.delete(id);
        System.out.println("Membership deleted successfully.");
    }
}