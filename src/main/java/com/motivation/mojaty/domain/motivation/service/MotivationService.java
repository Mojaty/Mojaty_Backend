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

    public List<MotivationResponseDto> getMotivationAll() {
        return motivationRepository.findAll().stream()
                .map(MotivationResponseDto::new)
                .collect(toList());
    }

}
