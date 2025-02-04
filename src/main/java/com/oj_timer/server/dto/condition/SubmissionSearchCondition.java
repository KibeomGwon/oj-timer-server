package com.oj_timer.server.dto.condition;

import com.querydsl.core.util.StringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SubmissionSearchCondition {

    private String email;
    private String site;
    private String title;
    private String language;
    private String level;

    public String getSite() {
        if (site != null) {
            if (site.equals("백준")) {
                site = "baekjoon";
            } else if (site.equals("프로그래머스")) {
                site = "programmers";
            }
        }
        return site;
    }
}
