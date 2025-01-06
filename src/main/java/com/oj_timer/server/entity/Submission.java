package com.oj_timer.server.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "submission")
@NoArgsConstructor
@Getter
public class Submission extends EntityDate {
    @Id
    @GeneratedValue
    @Column(name = "submission_id")
    private Long id;
    @Column(name = "element_id", unique = true)
    private String elementId;
    @Column(name = "submission_time")
    private LocalDateTime submissionTime;
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    private Submission(String elementId, LocalDateTime submissionTime, String username) {
        this.elementId = elementId;
        this.submissionTime = submissionTime;
        this.username = username;
    }

    // === 연관관계 메소드 === //
    public void bindingProblem(Problem problem) {
        this.problem = problem;
        problem.getSubmissions().add(this);
    }

    // === ddd === //
    public static Submission create(String elementId, LocalDateTime submissionTime, String username, Problem problem) {
        Submission submission = new Submission(elementId, submissionTime, username);
        submission.bindingProblem(problem);
        return submission;
    }
}
