package com.motivation.mojaty.domain.notification.web;

import com.motivation.mojaty.domain.notification.service.NotificationService;
import com.motivation.mojaty.domain.notification.service.ScheduledService;
import com.motivation.mojaty.domain.notification.web.dto.request.NotificationCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationApiController {

    private final NotificationService notificationService;

    @PostMapping("/new")
    public void saveNotification(@RequestBody NotificationCreateRequestDto req) {
        notificationService.saveNotification(req);
    }

    @DeleteMapping("/notification/delete")
    public void deleteNotification() {
        notificationService.deleteNotification();
    }
}
