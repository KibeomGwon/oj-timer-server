package com.oj_timer.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Member extends EntityDate {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    @Column(length = 11)
    private String phone;
    @OneToMany(mappedBy = "member")
    private List<Submission> submissions = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompleteReview> completeReviews = new ArrayList<>();

    private Member(String email, String password, String phone) {
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    // === ddd === //

    /**
     * 복습 완료 엔티티 생성
     * @param problem
     */
    public void completeReview(Problem problem) {
        CompleteReview completeReview = CompleteReview.create();
        completeReview.completeFrom(this);
        completeReview.completeTo(problem);
        completeReviews.add(completeReview);
    }

    public void needToSolveAgain(Long reviewId) {
        CompleteReview completeReview = completeReviews.stream()
                .filter(review -> review.getId() == reviewId)
                .findFirst().orElse(null);

        if (completeReview != null) {
            completeReviews.remove(completeReview);
        }
    }

    // === 생성 메소드 === //
    public static Member create(String email, String password, String phone) {
        return new Member(email, password, phone);
    }


}
