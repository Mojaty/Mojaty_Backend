package com.motivation.mojaty.domain.notification.web;

import com.motivation.mojaty.domain.notification.service.NotificationService;
import com.motivation.mojaty.domain.notification.service.ScheduledService;
import com.motivation.mojaty.domain.notification.web.dto.request.NotificationCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationApiController {

    private final NotificationService notificationService;
    private final ScheduledService scheduledService;

    @PostMapping("/new")
    public void saveNotification(@RequestBody NotificationCreateRequestDto req) {
        notificationService.saveNotification(req);
    }
}
