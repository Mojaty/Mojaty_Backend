package com.motivation.mojaty.domain.motivation.web.api;

import com.motivation.mojaty.domain.motivation.domain.type.ContentKind;
import com.motivation.mojaty.domain.motivation.domain.type.MotivationKind;
import com.motivation.mojaty.domain.motivation.service.MotivationService;
import com.motivation.mojaty.domain.motivation.web.dto.res.MotivationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/motivation")
public class MotivationApiController {

    private final MotivationService motivationService;

    @GetMapping("")
    public List<MotivationResponseDto> getMotivationAll() {
        return motivationService.getMotivationAll();
    }

    @GetMapping("/kind")
    public List<MotivationResponseDto> getMotivationByKind(@RequestParam("kind") MotivationKind kind) {
        return motivationService.getMotivationByKind(kind);
    }

    @GetMapping("/kind/content")
    public List<MotivationResponseDto> getMotivationByKindAndContent(@RequestParam("kind") MotivationKind kind,
                                                                     @RequestParam("content")ContentKind content) {
        return motivationService.getMotivationByKindAndContent(kind, content);
    }

    @GetMapping("/content")
    public List<MotivationResponseDto> getMotivationByContent(@RequestParam("content") ContentKind content) {
        return motivationService.getMotivationByContent(content);
    }
}
