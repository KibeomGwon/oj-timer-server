package com.oj_timer.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "problem")
@NoArgsConstructor
@Getter
public class Problem extends EntityDate {
    @Id
    @GeneratedValue
    @Column(name = "problem_id")
    private Long id;
    @Column(name = "problem_title", unique = true)
    private String problemTitle;
    private String level;
    private String site;

    @OneToMany(mappedBy = "problem")
    private List<Submission> submissions = new ArrayList<>();

    public Problem(String problemTitle, String level, String site) {
        this.problemTitle = problemTitle;
        this.level = level;
        this.site = site;
    }

    // === ddd === //
    public static Problem create(String problemTitle, String level, String site) {
         return new Problem(problemTitle, level, site);
    }
}
