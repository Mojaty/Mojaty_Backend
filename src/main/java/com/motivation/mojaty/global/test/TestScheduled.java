package com.motivation.mojaty.global.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class TestScheduled {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss:SSS");

    @Scheduled(cron = "0 14 3 * * ?")
    public void cornExpression() {
        log.info("현재시간 - {}", formatter.format(LocalDateTime.now()));
    }
}
