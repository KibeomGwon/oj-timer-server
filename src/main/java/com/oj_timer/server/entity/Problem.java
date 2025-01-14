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
    @Column(name = "problem_title_id", unique = true)
    private String problemTitleId;
    private String level;
    private String site;
    private String link;
    private String title;

    @OneToMany(mappedBy = "problem")
    private List<Submission> submissions = new ArrayList<>();

    public Problem(String problemTitleId, String level, String site, String link, String title) {
        this.problemTitleId = problemTitleId;
        this.level = level;
        this.site = site;
        this.link = link;
        this.title = title;
    }

    // === ddd === //
    public static Problem create(String problemTitleId, String level, String site, String link, String title) {
         return new Problem(problemTitleId, level, site, link, title);
    }
}
