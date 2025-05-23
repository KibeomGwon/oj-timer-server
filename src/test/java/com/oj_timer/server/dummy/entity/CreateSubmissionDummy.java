package com.oj_timer.server.dummy.entity;

import com.oj_timer.server.repository.dummy.MemberDummyRepository;
import com.oj_timer.server.repository.dummy.SubmissionDummyRepository;
import com.oj_timer.server.repository.dummy.dto.SubmissionTestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@SpringBootTest
public class CreateSubmissionDummy {

    @Autowired
    SubmissionDummyRepository submissionDummyRepository;


    @Test
    @DisplayName("제출 더미 데이터 생성")
    void submissionCreate() {
        // given
        List<SubmissionTestDto> submissions = getSubmissions();

        Long lastId = submissionDummyRepository.lastInsertId();
        // when
        IntStream.range(0, submissions.size()).forEach(idx -> {
            SubmissionTestDto dto = submissions.get(idx);
            dto.setId(lastId + idx);
        });

        submissionDummyRepository.bulkInsert(submissions);
        // then
    }

    @Test
    @DisplayName("랜덤타임 생성")
    void createRandomTime() {
        // given
        LocalDateTime randomTime = randomTime();
        String formatted = randomTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String pattern = format.toPattern();
        // when
        System.out.println(formatted);
        // then
    }

    List<SubmissionTestDto> getSubmissions() {
        List<SubmissionTestDto> dtos = new ArrayList<>();

        for (int i = 1; i <= 100_000; i++) {
            Long randomMemberId = getRandomMemberId(), randomProblemId = getRandomProblemId();
            String submissionTime = randomTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String submissionTime2 = randomTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            SubmissionTestDto dto = SubmissionTestDto.builder()
                    .memberId(randomMemberId)
                    .problemId(randomProblemId)
                    .elementId(i + randomMemberId + randomProblemId + submissionTime2)
                    .submissionTime(submissionTime)
                    .username("user" + randomMemberId)
                    .language(i % 2 == 0 ? "자바" : "MySql")
                    .build();

            dtos.add(dto);
        }
        return dtos;
    }

    Long getRandomMemberId() {
        return new Random().nextLong(100) + 1;
    }

    Long getRandomProblemId() {
        return new Random().nextLong(10000) + 1;
    }

    LocalDateTime randomTime() {
        Instant now = Instant.now();
        Instant then = now.minus(365, ChronoUnit.DAYS);
        long millisBetween = ChronoUnit.MILLIS.between(then, now);
        Instant randomTime = then.plusMillis(ThreadLocalRandom.current().nextLong(millisBetween));
        return LocalDateTime.ofInstant(randomTime, ZoneOffset.UTC);
    }

    String change(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("YYYYMMDDmmSS"));
    }
}
