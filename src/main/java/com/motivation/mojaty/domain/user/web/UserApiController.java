package com.motivation.mojaty.domain.user.web;

import com.motivation.mojaty.domain.user.web.dto.request.UserJoinRequestDto;
import com.motivation.mojaty.domain.user.web.dto.request.UserUpdateRequestDto;
import com.motivation.mojaty.domain.user.web.dto.request.UserWithdrawalRequestDto;
import com.motivation.mojaty.domain.user.web.dto.response.UserResponseDto;
import com.motivation.mojaty.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserApiController {

    private final UserService userService;

    @PostMapping("/join")
    public Long join(@RequestBody @Valid UserJoinRequestDto req) {
        return userService.join(req);
    }

    @GetMapping("/{userId}")
    public UserResponseDto getUserById(@PathVariable("userId") Long userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/{userId}/edit")
    public Long updateUser(@PathVariable("userId") Long userId,
                           @RequestBody @Valid UserUpdateRequestDto req) {
        return userService.updateUser(userId, req);
    }

    @PutMapping("/{userId}/edit/password")
    public Long updateUserPassword(@PathVariable("userId") Long userId,
                                   @RequestHeader("newPassword") String newPassword) {
        return userService.updateUserPassword(userId, newPassword);
    }

    @DeleteMapping("/{userId}")
    public Long deleteUser(@PathVariable("userId") Long userId,
                           @RequestBody @Valid UserWithdrawalRequestDto req) {
        return userService.withdrawUser(userId, req);
    }
}
