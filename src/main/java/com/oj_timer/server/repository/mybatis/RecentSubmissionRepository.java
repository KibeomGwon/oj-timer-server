package com.oj_timer.server.repository.mybatis;

import com.oj_timer.server.dto.RecentSubmissionDto;
import com.oj_timer.server.dto.condition.SubmissionSearchCondition;
import com.oj_timer.server.repository.mybatis.mapper.SubmissionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RecentSubmissionRepository {

    private final SubmissionMapper mapper;

    public Page<RecentSubmissionDto> findRecentSubmissions(SubmissionSearchCondition condition, Pageable pageable) {
        log.info("CONDITION {}", condition.toString());
        log.info("PAGE NUMBER {}", pageable.getPageNumber());

        long offset = pageable.getOffset(), size = pageable.getPageSize();

        List<RecentSubmissionDto> content = mapper.findRecentSubmissions(condition, offset, size);
        long count = mapper.recentSubmissionsCount(condition);

        return new PageImpl<>(content, pageable, count);
    }
}
