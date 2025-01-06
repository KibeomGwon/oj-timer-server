package com.oj_timer.server.dto;

import com.oj_timer.server.entity.Problem;
import com.oj_timer.server.entity.Submission;
import lombok.Data;


public interface BaseSubmitDto {
    Problem toProblem();
    Submission toSubmission(Problem problem);
}
