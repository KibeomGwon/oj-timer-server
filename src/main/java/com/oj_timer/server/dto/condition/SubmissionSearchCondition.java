package com.oj_timer.server.dto.condition;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SubmissionSearchCondition {
    private String username;
    private String site;
    private String email;
}
