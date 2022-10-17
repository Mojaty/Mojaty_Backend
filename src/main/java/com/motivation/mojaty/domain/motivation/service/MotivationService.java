package com.motivation.mojaty.domain.motivation.service;

import com.motivation.mojaty.domain.motivation.domain.Motivation;
import com.motivation.mojaty.domain.motivation.domain.MotivationRepository;
import com.motivation.mojaty.domain.motivation.web.dto.req.MotivationImageRequestDto;
import com.motivation.mojaty.domain.motivation.web.dto.req.MotivationCreateRequestDto;
import com.motivation.mojaty.domain.motivation.web.dto.res.MotivationResponseDto;
import com.motivation.mojaty.domain.user.domain.User;
import com.motivation.mojaty.domain.user.domain.UserRepository;
import com.motivation.mojaty.global.exception.application.CustomException;
import com.motivation.mojaty.global.exception.application.ErrorCode;
import com.motivation.mojaty.global.provider.security.SecurityProvider;
import com.motivation.mojaty.global.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MotivationService {

    private final MotivationRepository motivationRepository;
    private final UserRepository userRepository;
    private final FileService fileService;

    @Transactional
    public void createMotivation(MotivationCreateRequestDto req) {
        Motivation motivation = req.toEntity();
        User user = userRepository.findByEmail(SecurityProvider.getLoginUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.RETRY_LOGIN));
        motivation.confirmUser(user);
        motivationRepository.save(motivation);
    }

    @Transactional
    public void createImageMotivation(MotivationImageRequestDto req) throws IOException {
        Motivation motivation = req.toEntity();
        String imgUrl = fileService.saveFile(req.getFile());
        motivation.updateContent(imgUrl);

        User user = userRepository.findByEmail(SecurityProvider.getLoginUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.RETRY_LOGIN));
        motivation.confirmUser(user);

        motivationRepository.save(motivation);
    }

    public MotivationResponseDto getMotivationById(Long motivationId) {
        return motivationRepository.findById(motivationId)
                .map(MotivationResponseDto::new)
                .orElseThrow(() -> new CustomException(ErrorCode.MOTIVATION_NOT_FOUND));
    }

    @Transactional
    public void updateMotivation(Long motivationId, MotivationImageRequestDto req) throws IOException {
        Motivation motivation = motivationRepository.findById(motivationId)
                .orElseThrow(() -> new CustomException(ErrorCode.MOTIVATION_NOT_FOUND));

        validateMotivationFromUser(motivation);

        fileService.deleteFile(motivation.getContent());
        motivation.updateKinds(req.getMotivationKind(), req.getContentKind());
        motivation.updateContent(fileService.saveFile(req.getFile()));
    }

    public List<MotivationResponseDto> getMotivationAll() {
        return motivationRepository.findAll().stream()
                .map(MotivationResponseDto::new)
                .collect(toList());
    }

    @Transactional
    public void deleteMotivation(Long motivationId) {
        Motivation motivation = motivationRepository.findById(motivationId)
                .orElseThrow(() -> new CustomException(ErrorCode.MOTIVATION_NOT_FOUND));

        validateMotivationFromUser(motivation);

        fileService.deleteFile(motivation.getContent());
        motivationRepository.delete(motivation);
    }

    private void validateMotivationFromUser(Motivation motivation) {
        User user = userRepository.findByEmail(SecurityProvider.getLoginUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.RETRY_LOGIN));

        if(!motivation.getUser().getEmail().equals(user.getEmail())) {
            throw new CustomException(ErrorCode.DIFFERENT_USER);
        }
    }
}
