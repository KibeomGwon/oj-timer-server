package com.oj_timer.server.dto;

import com.oj_timer.server.entity.Problem;
import com.oj_timer.server.entity.Submission;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaekjoonSubmissionDto implements BaseSubmitDto {

    private String elementId;
    private String language;
    private String problemId;
    private LocalDateTime submissionTime;
    private String username;
    private String level;
    private String link;
    private String site;

    @Override
    public Problem toProblem() {
        Problem problem = Problem.create(site + problemId, level, site, link);
        return problem;
    }

    @Override
    public Submission toSubmission(Problem problem) {
        Submission submission = Submission.create(elementId, submissionTime, username, problem);
        return submission;
    }
}
