package com.motivation.mojaty.domain.notification.domain;

import com.motivation.mojaty.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Optional<Notification> findByUser(User user);
}
