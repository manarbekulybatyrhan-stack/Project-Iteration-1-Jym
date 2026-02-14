package gym.controller;

import gym.model.Member;
import gym.model.Membership;
import gym.repository.MemberRepository;
import gym.repository.MembershipRepository;
import gym.repository.impl.MemberRepositoryImpl;
import gym.repository.impl.MembershipRepositoryImpl;
import gym.validation.ValidationService;

import java.time.LocalDate;
import java.util.List;

public class MemberController {

    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;
    private final ValidationService validationService;

    public MemberController() {
        this(new MemberRepositoryImpl(), new MembershipRepositoryImpl(), ValidationService.getInstance());
    }

    public MemberController(MemberRepository memberRepository,
                            MembershipRepository membershipRepository,
                            ValidationService validationService) {
        this.memberRepository = memberRepository;
        this.membershipRepository = membershipRepository;
        this.validationService = validationService;
    }

    public void registerMember(String name, String email, String phone, int membershipId) {
        try {
            validationService.validateName(name);
            validationService.validateEmail(email);
            validationService.validatePhone(phone);

            Membership membership = membershipRepository.findById(membershipId);
            if (membership == null) {
                System.out.println("Membership not found.");
                return;
            }

            Member member = new Member();
            member.setName(name);
            member.setEmail(email);
            member.setPhone(phone);
            member.setMembershipId(membershipId);
            member.setJoinDate(LocalDate.now());
            member.setExpiryDate(LocalDate.now().plusMonths(membership.getDurationMonths()));

            memberRepository.create(member);
            System.out.println("Member registered successfully.");
        } catch (Exception e) {
            System.out.println("Validation Error: " + e.getMessage());
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
        try {
            Member member = memberRepository.findById(id);
            if (member == null) {
                System.out.println("Member not found.");
                return;
            }

            validationService.validateName(name);
            validationService.validateEmail(email);
            validationService.validatePhone(phone);

            member.setName(name);
            member.setEmail(email);
            member.setPhone(phone);
            memberRepository.update(member);

            System.out.println("Member updated successfully.");
        } catch (Exception e) {
            System.out.println("Validation Error: " + e.getMessage());
        }
    }

    public void deleteMember(int id) {
        memberRepository.delete(id);
        System.out.println("Member deleted successfully.");
    }
}
