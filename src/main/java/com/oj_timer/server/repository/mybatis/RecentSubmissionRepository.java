package com.oj_timer.server.repository.mybatis;

import com.oj_timer.server.dto.condition.SubmissionSearchCondition;
import com.oj_timer.server.dto.test.MyBatisSubmissionDto;
import com.oj_timer.server.repository.mybatis.mapper.SubmissionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecentSubmissionRepository {

    private final SubmissionMapper mapper;

    public Page<MyBatisSubmissionDto> findRecentSubmissions(SubmissionSearchCondition condition, Pageable pageable) {
        long offset = pageable.getOffset(), size = pageable.getPageSize();
        if (offset == 0) {
            offset++;
        }
        offset = (offset - 1) * size;
        List<MyBatisSubmissionDto> content = mapper.findRecentSubmissions(condition, offset, size);
        long count = mapper.recentSubmissionsCount(condition);

        return new PageImpl<>(content, pageable, count);
    }
}
