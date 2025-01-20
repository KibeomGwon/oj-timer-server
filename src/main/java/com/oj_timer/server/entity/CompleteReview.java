package com.oj_timer.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
public class CompleteReview extends EntityDate {
    @Id
    @GeneratedValue
    @Column(name = "complete_review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Problem problem;

    // === 연관관계 메소드 === //
    public void completeFrom(Member member) {
        this.member = member;
        if (!member.getCompleteReviews().contains(member)) {
            member.getCompleteReviews().add(this);
        }
    }

    public void completeTo(Problem problem) {
        this.problem = problem;
        if (!problem.getCompleteReviews().contains(this)) {
            problem.getCompleteReviews().add(this);
        }
    }

    public static CompleteReview create() {
        return new CompleteReview();
    }

}
