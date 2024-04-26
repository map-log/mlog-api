package com.mlog.travel.controller;

import com.mlog.travel.dto.TravelDto;
import com.mlog.travel.service.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mlog.util.ApiUtils.ApiResult;
import static com.mlog.util.ApiUtils.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/travel")
public class TravelController {

    private final TravelService travelService;

    @GetMapping
    public ApiResult<String> hello() {
        return success("hello travel");
    }

    @GetMapping("/{id}")
    public ApiResult<List<TravelDto>> getId(@PathVariable Long id) {
        return success(travelService.selectAllTravel(id));
    }

    @PostMapping("/{id}")
    public ApiResult<Boolean> saveTravel(@PathVariable Long id, @RequestBody TravelDto travelDto) {
        return success(travelService.insertTravel(id, travelDto));
    }

    @PutMapping("/{id}")
    public ApiResult<Boolean> updateTravel(@PathVariable Long id, @RequestBody TravelDto travelDto) {
        return success(travelService.updateTravel(id, travelDto));
    }

    @DeleteMapping("/{id}")
    public ApiResult<Boolean> deleteTravel(@PathVariable Long id) {
        return success(travelService.deleteTravelById(id));
    }

}
