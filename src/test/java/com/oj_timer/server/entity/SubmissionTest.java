package com.oj_timer.server.entity;

import com.oj_timer.server.config.AppConfig;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
@Import(AppConfig.class)
class SubmissionTest {
}