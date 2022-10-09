package com.motivation.mojaty.domain.motivation.service;

import com.motivation.mojaty.domain.motivation.domain.Motivation;
import com.motivation.mojaty.domain.motivation.domain.MotivationRepository;
import com.motivation.mojaty.domain.motivation.domain.type.ContentKind;
import com.motivation.mojaty.domain.motivation.domain.type.MotivationKind;
import com.motivation.mojaty.domain.motivation.web.dto.req.MotivationCreateRequestDto;
import com.motivation.mojaty.domain.motivation.web.dto.res.MotivationResponseDto;
import com.motivation.mojaty.global.exception.application.CustomException;
import com.motivation.mojaty.global.exception.application.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MotivationService {

    private final MotivationRepository motivationRepository;

    @Transactional
    public void createMotivation(MotivationCreateRequestDto req) {
        motivationRepository.save(req.toEntity());
    }

    public List<MotivationResponseDto> getMotivationAll(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, 20);
        return motivationRepository.findAll(pageable).stream()
                .map(MotivationResponseDto::new)
                .collect(toList());
    }

    public List<MotivationResponseDto> getMotivationByKind(MotivationKind kind, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, 20);
        return motivationRepository.findAll(pageable).stream()
                .filter(m -> m.getMotivationKind().equals(kind))
                .map(MotivationResponseDto::new)
                .collect(toList());
    }

    public List<MotivationResponseDto> getMotivationByKindAndContent(MotivationKind kind, ContentKind content, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, 20);
        return motivationRepository.findAll(pageable).stream()
                .filter(m -> m.getMotivationKind().equals(kind))
                .filter(m -> m.getContentKind().equals(content))
                .map(MotivationResponseDto::new)
                .collect(toList());
    }

    public List<MotivationResponseDto> getMotivationByContent(ContentKind content, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, 20);
        return motivationRepository.findAll(pageable).stream()
                .filter(m -> m.getContentKind().equals(content))
                .map(MotivationResponseDto::new)
                .collect(toList());
    }
}
