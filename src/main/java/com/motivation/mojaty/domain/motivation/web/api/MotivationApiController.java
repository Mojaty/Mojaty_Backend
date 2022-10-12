package com.motivation.mojaty.domain.motivation.web.api;

import com.motivation.mojaty.domain.motivation.domain.type.ContentKind;
import com.motivation.mojaty.domain.motivation.domain.type.MotivationKind;
import com.motivation.mojaty.domain.motivation.service.MotivationService;
import com.motivation.mojaty.domain.motivation.web.dto.req.MotivationCreateRequestDto;
import com.motivation.mojaty.domain.motivation.web.dto.res.MotivationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("")
    public List<MotivationResponseDto> getMotivationAll() {
        return motivationService.getMotivationAll();
    }
}
