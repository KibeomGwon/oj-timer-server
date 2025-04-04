package com.oj_timer.server.dto.condition;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

@Setter
@Getter
@ToString
public class SubmissionSearchCondition {

    private String email;
    private String site;
    private String title;
    private String language;
    private String level;
    private String rangeUnit;
    private Integer rangeNumber;
    private String sortDirection;

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

    public void setRangeNumber(Integer rangeNumber) {
        if (rangeNumber != null)
            this.rangeNumber = -rangeNumber;
    }
}
