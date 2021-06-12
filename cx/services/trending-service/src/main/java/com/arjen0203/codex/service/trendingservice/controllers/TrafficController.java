package com.arjen0203.codex.service.trendingservice.controllers;

import com.arjen0203.codex.domain.trending.dto.TrafficDto;
import com.arjen0203.codex.domain.trending.dto.TrendingPostDto;
import com.arjen0203.codex.service.trendingservice.services.TrafficService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/traffic")
@RequiredArgsConstructor
public class TrafficController {
    private final TrafficService trafficService;

    @GetMapping
    public ResponseEntity<Page<TrendingPostDto>> allTraffic(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(trafficService.getAllTrafficCounter(page, size));
    }

    @PostMapping
    public ResponseEntity<TrafficDto> createTraffic() {
        return ResponseEntity.ok(trafficService.createTraffic());
    }
}
