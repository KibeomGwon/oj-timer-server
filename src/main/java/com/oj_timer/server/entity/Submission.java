package com.oj_timer.server.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "submission")
@NoArgsConstructor
@Getter
public class Submission extends EntityDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submission_id")
    private Long id;
    @Column(name = "element_id", unique = true)
    private String elementId;
    @Column(name = "submission_time")
    private LocalDateTime submissionTime;
    private String username;
    @Column(name = "language")
    private String language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    private Submission(String elementId, LocalDateTime submissionTime, String username, String language) {
        this.elementId = elementId;
        this.submissionTime = submissionTime;
        this.username = username;
        this.language = language;
    }

    // === 연관관계 메소드 === //
    public void bindingProblem(Problem problem) {
        this.problem = problem;
        problem.getSubmissions().add(this);
    }

    public void solveFrom(Member member) {
        member.getSubmissions().add(this);
        this.member = member;
    }

    // === 생성 메소드 === //
    public static Submission create(String elementId, LocalDateTime submissionTime, String username, String language) {
        Submission submission = new Submission(elementId, submissionTime, username, language);
        return submission;
    }
}
