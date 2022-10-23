package com.motivation.mojaty.domain.user.service.user;

import com.motivation.mojaty.domain.notification.service.NotificationService;
import com.motivation.mojaty.domain.user.domain.User;
import com.motivation.mojaty.domain.user.domain.UserRepository;
import com.motivation.mojaty.domain.user.web.dto.user.req.UserJoinRequestDto;
import com.motivation.mojaty.domain.user.web.dto.user.req.UserUpdateRequestDto;
import com.motivation.mojaty.domain.user.web.dto.user.req.UserWithdrawalRequestDto;
import com.motivation.mojaty.domain.user.web.dto.user.res.UserResponseDto;
import com.motivation.mojaty.global.exception.application.CustomException;
import com.motivation.mojaty.global.exception.application.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final NotificationService notificationService;

    @Transactional
    public Long join(UserJoinRequestDto req) {
        if(userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_EXISTS_USER);
        }

        User user = userRepository.save(req.toEntity());
        user.addUserAuthority();
        user.encodedPassword(passwordEncoder);
        return user.getId();
    }

    public UserResponseDto getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(UserResponseDto::new)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    @Transactional
    public Long updateUser(Long userId, UserUpdateRequestDto req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.update(req.getEmail(), req.getNickname());
        return user.getId();
    }

    @Transactional
    public Long updateUserPassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.updatePassword(newPassword);
        return user.getId();
    }

    @Transactional
    public Long withdrawUser(Long userId, UserWithdrawalRequestDto req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if(!passwordEncoder.matches(user.getPassword(), req.getPassword())) {
            throw new CustomException(ErrorCode.NOT_MATCH_PASSWORD);
        }

        userRepository.delete(user);
        notificationService.deleteNotification();
        return user.getId();
    }
}
