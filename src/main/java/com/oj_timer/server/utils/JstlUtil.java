package com.oj_timer.server.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class JstlUtil {
    public static String getTimeDiff(LocalDateTime submissionTime) {
        String diff = "";

        LocalDateTime now = LocalDateTime.now();

        if (ChronoUnit.YEARS.between(now, submissionTime) != 0) {
            return Long.toString(Math.abs(ChronoUnit.YEARS.between(now, submissionTime))) + "년";
        } else if (ChronoUnit.MONTHS.between(now, submissionTime) != 0) {
            return Long.toString(Math.abs(ChronoUnit.MONTHS.between(now, submissionTime))) + "달";
        } else if (ChronoUnit.DAYS.between(now, submissionTime) != 0) {
            return Long.toString(Math.abs(ChronoUnit.DAYS.between(now, submissionTime))) + "일";
        } else if (ChronoUnit.HOURS.between(now, submissionTime) != 0) {
            return Long.toString(Math.abs(ChronoUnit.HOURS.between(now, submissionTime))) + "시간";
        } else if (ChronoUnit.MINUTES.between(now, submissionTime) != 0) {
            return Long.toString(Math.abs(ChronoUnit.MINUTES.between(now, submissionTime))) + "분";
        } else if (ChronoUnit.SECONDS.between(now, submissionTime) != 0) {
            return Long.toString(Math.abs(ChronoUnit.SECONDS.between(now, submissionTime))) + "초";
        }
        return "";
    }

    public static String siteEnToKr(String site) {
        switch (site) {
            case "baekjoon":
                return "백준";
            case "programmers":
                return "프로그래머스";
            default:
                return "";
        }
    }
}
