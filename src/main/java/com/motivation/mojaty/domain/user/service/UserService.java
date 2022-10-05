package com.motivation.mojaty.domain.user.service;

import com.motivation.mojaty.domain.user.domain.User;
import com.motivation.mojaty.domain.user.domain.UserRepository;
import com.motivation.mojaty.domain.user.web.dto.request.UserJoinRequestDto;
import com.motivation.mojaty.domain.user.web.dto.request.UserUpdateRequestDto;
import com.motivation.mojaty.domain.user.web.dto.request.UserWithdrawalRequestDto;
import com.motivation.mojaty.domain.user.web.dto.response.UserResponseDto;
import com.motivation.mojaty.global.exception.CustomException;
import com.motivation.mojaty.global.exception.ErrorCode;
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

        user.update(req.getEmail());
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
        return user.getId();
    }
}
