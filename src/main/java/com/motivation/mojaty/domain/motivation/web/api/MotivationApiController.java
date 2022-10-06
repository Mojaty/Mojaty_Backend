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
    public List<MotivationResponseDto> getMotivationAll(@RequestParam(value = "page", defaultValue = "0") int pageNum) {
        return motivationService.getMotivationAll(pageNum);
    }

    @GetMapping("/kind")
    public List<MotivationResponseDto> getMotivationByKind(
            @RequestParam("kind") MotivationKind kind,
            @RequestParam(value = "page", defaultValue = "0") int pageNum) {
        return motivationService.getMotivationByKind(kind, pageNum);
    }

    @GetMapping("/kind/content")
    public List<MotivationResponseDto> getMotivationByKindAndContent
            (@RequestParam("kind") MotivationKind kind,
             @RequestParam("content")ContentKind content,
             @RequestParam(value = "page", defaultValue = "0") int pageNum) {
        return motivationService.getMotivationByKindAndContent(kind, content, pageNum);
    }

    @GetMapping("/content")
    public List<MotivationResponseDto> getMotivationByContent
            (@RequestParam("content") ContentKind content,
             @RequestParam(value = "page", defaultValue = "0") int pageNum) {
        return motivationService.getMotivationByContent(content, pageNum);
    }
}
