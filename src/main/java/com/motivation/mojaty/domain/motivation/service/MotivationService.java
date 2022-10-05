package com.motivation.mojaty.domain.motivation.service;

import com.motivation.mojaty.domain.motivation.domain.MotivationRepository;
import com.motivation.mojaty.domain.motivation.domain.type.ContentKind;
import com.motivation.mojaty.domain.motivation.domain.type.MotivationKind;
import com.motivation.mojaty.domain.motivation.web.dto.res.MotivationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MotivationService {

    private final MotivationRepository motivationRepository;

    public List<MotivationResponseDto> getMotivationAll() {
        return motivationRepository.findAll().stream()
                .map(MotivationResponseDto::new)
                .collect(toList());
    }

    public List<MotivationResponseDto> getMotivationByKind(MotivationKind kind) {
        return motivationRepository.findAll().stream()
                .filter(m -> m.getMotivationKind().equals(kind))
                .map(MotivationResponseDto::new)
                .collect(toList());
    }

    public List<MotivationResponseDto> getMotivationByKindAndContent(MotivationKind kind, ContentKind content) {
        return motivationRepository.findAll().stream()
                .filter(m -> m.getMotivationKind().equals(kind))
                .filter(m -> m.getContentKind().equals(content))
                .map(MotivationResponseDto::new)
                .collect(toList());
    }

    public List<MotivationResponseDto> getMotivationByContent(ContentKind content) {
        return motivationRepository.findAll().stream()
                .filter(m -> m.getContentKind().equals(content))
                .map(MotivationResponseDto::new)
                .collect(toList());
    }
}
