package gym.controller;

import gym.model.Member;
import gym.repository.MemberRepository;
import gym.repository.impl.MemberRepositoryImpl;
import gym.repository.MembershipRepository;

import java.time.LocalDate;
import java.util.List;

import gym.repository.impl.MembershipRepositoryImpl;
import gym.validation.ValidationService;

import gym.validation.CustomExceptions.*;

public class MemberController {
    private MemberRepository memberRepository;
    private MembershipRepository membershipRepository;

    public MemberController() {
        this.memberRepository = new MemberRepositoryImpl();
        this.membershipRepository = new MembershipRepositoryImpl();
    }

    public void registerMember(String name, String email, String phone, int membershipId) {
        ValidationService validator = ValidationService.getInstance();
        
        try {
            // Validate all fields
            validator.validateName(name);
            validator.validateEmail(email);
            validator.validatePhone(phone);
            
            Member member = new Member(memberRepository.getNextId(), name, email, phone, membershipId);
            memberRepository.add(member);
            System.out.println("✓ Member registered successfully!");
            
        } catch (InvalidNameException | InvalidEmailException | InvalidPhoneException e) {
            System.out.println("✗ Validation Error: " + e.getMessage());
        }
    }

    public Member getMember(int id) {
        return memberRepository.findById(id);
    }

    public void displayAllMembers() {
        List<Member> members = memberRepository.findAll();

        if (members.isEmpty()) {
            System.out.println("No members found.");

            return;
        }

        System.out.println("\nMembers:");
        System.out.println("ID | Name | Email | Phone | Join Date | Expiry Date");
        System.out.println("------------------------------------------------");

        for (Member m : members) {
            System.out.println(m.getId() + " | " + m.getName() + " | " + 
                m.getEmail() + " | " + m.getPhone() + " | " + 
                m.getJoinDate() + " | " + m.getExpiryDate());
        }
    }


    public void displayActiveMembers() {
        List<Member> members = memberRepository.findActiveMembers();

        if (members.isEmpty()) {
            System.out.println("No active members found.");

            return;
        }

        System.out.println("\nActive Members:");
        System.out.println("ID | Name | Email | Expiry Date");
        System.out.println("------------------------------------------------");

        for (Member m : members) {
            System.out.println(m.getId() + " | " + m.getName() + " | " + 
                m.getEmail() + " | " + m.getExpiryDate());
        }
    }


    public boolean checkMembershipValidity(int memberId) {
        Member member = memberRepository.findById(memberId);

        if (member == null) {
            System.out.println("Member not found.");

            return false;
        }

        if (member.getExpiryDate().isBefore(LocalDate.now())) {
            System.out.println("Membership expired on: " + member.getExpiryDate());

            return false;

        } else {
            System.out.println("Membership is active until: " + member.getExpiryDate());

            return true;
        }
    }


    public void updateMember(int id, String name, String email, String phone) {
        Member member = memberRepository.findById(id);

        if (member == null) {
            System.out.println("Member not found.");

            return;
        }

        member.setName(name);
        member.setEmail(email);
        member.setPhone(phone);

        memberRepository.update(member);

        System.out.println("Member updated successfully.");
    }


    public void deleteMember(int id) {
        memberRepository.delete(id);

        System.out.println("Member deleted successfully.");
    }
}