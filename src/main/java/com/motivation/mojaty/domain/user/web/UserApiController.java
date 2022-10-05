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
    public Long join(@RequestBody @Valid UserJoinRequestDto requestDto) {
        return userService.join(requestDto);
    }

    @GetMapping("/{userId}")
    public UserResponseDto getUserById(@PathVariable("userId") Long userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/{userId}/edit")
    public Long updateUser(@PathVariable("userId") Long userId,
                           @RequestBody @Valid UserUpdateRequestDto requestDto) {
        return userService.updateUser(userId, requestDto);
    }

    @DeleteMapping("/{userId}")
    public Long deleteUser(@PathVariable("userId") Long userId,
                           @RequestBody @Valid UserWithdrawalRequestDto requestDto) {
        return userService.withdrawUser(userId, requestDto);
    }
}
