package com.oj_timer.server.entity;

import com.oj_timer.server.AppConfig;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
@Import(AppConfig.class)
class SubmissionTest {



}