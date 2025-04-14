package com.oj_timer.server.repository.mybatis.mapper;

import com.oj_timer.server.dto.RecentSubmissionDto;
import com.oj_timer.server.dto.condition.SubmissionSearchCondition;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface SubmissionMapper {
    List<RecentSubmissionDto> findRecentSubmissions(@Param("condition") SubmissionSearchCondition condition, @Param("offset") long offset, @Param("limit") long limit);
    long recentSubmissionsCount(@Param("condition") SubmissionSearchCondition condition);
}
