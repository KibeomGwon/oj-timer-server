package com.oj_timer.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "problem")
@NoArgsConstructor
@Getter
@ToString(exclude = {"submissions", "completeReviews"})
public class Problem extends EntityDate {
    @Id
    @GeneratedValue
    @Column(name = "problem_id")
    private Long id;
    @Column(name = "problem_title_id")
    private String problemTitleId;
    private String level;
    private String site;
    private String link;
    private String title;

    @OneToMany(mappedBy = "problem")
    private List<Submission> submissions = new ArrayList<>();

    @OneToMany(mappedBy = "problem")
    private List<CompleteReview> completeReviews = new ArrayList<>();

    public Problem(String problemTitleId, String level, String site, String link, String title) {
        this.problemTitleId = problemTitleId;
        this.level = level;
        this.site = site;
        this.link = link;
        this.title = title;
    }

    // === 연관관계 메소드 === //
    public void completeFrom(CompleteReview completeReview) {
        completeReview.completeTo(this);
        if (!completeReviews.contains(completeReview)) {
            completeReviews.add(completeReview);
        }
    }

    // === ddd === //
    public static Problem create(String problemTitleId, String level, String site, String link, String title) {
         return new Problem(problemTitleId, level, site, link, title);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProblemTitleId(String problemTitleId) {
        this.problemTitleId = problemTitleId;
    }
}
