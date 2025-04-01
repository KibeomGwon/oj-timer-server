package com.oj_timer.server.repository.mybatis.mapper;

import com.oj_timer.server.dto.condition.SubmissionSearchCondition;
import com.oj_timer.server.dto.test.MyBatisSubmissionDto;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Mapper
public interface SubmissionMapper {
    List<MyBatisSubmissionDto> findRecentSubmissions(@Param("condition") SubmissionSearchCondition condition, @Param("offset") long offset, @Param("limit") long limit);

    long recentSubmissionsCount(@Param("condition") SubmissionSearchCondition condition);
}
