package com.motivation.mojaty.domain.motivation.web.api;

import com.motivation.mojaty.domain.motivation.service.MotivationService;
import com.motivation.mojaty.domain.motivation.web.dto.req.MotivationImageRequestDto;
import com.motivation.mojaty.domain.motivation.web.dto.req.MotivationCreateRequestDto;
import com.motivation.mojaty.domain.motivation.web.dto.res.MotivationResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/motivation")
public class MotivationApiController {

    private final MotivationService motivationService;

    @PostMapping("/new")
    public void createMotivation(@RequestBody MotivationCreateRequestDto req) {
        motivationService.createMotivation(req);
    }

    @PostMapping("/new/image")
    public void createImageMotivation(MotivationImageRequestDto req) throws IOException {
        motivationService.createImageMotivation(req);
    }

    @GetMapping("")
    public List<MotivationResponseDto> getMotivationAll() {
        return motivationService.getMotivationAll();
    }

    @GetMapping("/{motivationId}")
    public MotivationResponseDto getMotivationById(@PathVariable("motivationId") Long motivationId) {
        return motivationService.getMotivationById(motivationId);
    }

    @PutMapping("/{motivationId}/edit")
    public void updateMotivation(@PathVariable("motivationId") Long motivationId,
                                 MotivationImageRequestDto req) throws IOException {
        motivationService.updateMotivation(motivationId, req);
    }

    @DeleteMapping("/{motivationId}")
    public void deleteMotivation(@PathVariable("motivationId") Long motivationId) {
        motivationService.deleteMotivation(motivationId);
    }
}
