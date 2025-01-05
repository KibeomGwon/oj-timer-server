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
    private String element_id;
    @Column(name = "submission_time")
    private LocalDateTime time;
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    private Submission(String element_id, LocalDateTime time, String username) {
        this.element_id = element_id;
        this.time = time;
        this.username = username;
    }

    // === 연관관계 메소드 === //
    public void bindingProblem(Problem problem) {
        this.problem = problem;
        problem.getSubmissions().add(this);
    }

    // === ddd === //
    public static Submission create(String element_id, LocalDateTime time, String username, Problem problem) {
        Submission submission = new Submission(element_id, time, username);
        submission.bindingProblem(problem);
        return submission;
    }
}
