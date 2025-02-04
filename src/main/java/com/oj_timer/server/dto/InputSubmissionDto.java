package com.oj_timer.server.dto;

import com.oj_timer.server.entity.Problem;
import com.oj_timer.server.entity.Submission;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InputSubmissionDto {

    private String elementId;
    private String language;
    private String problemId;
    private LocalDateTime submissionTime;
    private String username;
    private String level;
    private String link;
    private String site;
    private String title;


    public Problem toProblem() {
        Problem problem = Problem.create(problemId, level, site, link, title);
        return problem;
    }

    public Submission toSubmission() {
        Submission submission = Submission.create(elementId, submissionTime, username, language);
        return submission;
    }
}
