package com.mlog.travel.controller;

import com.mlog.travel.dto.SaveTravelRequest;
import com.mlog.travel.service.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.mlog.util.ApiUtils.ApiResult;
import static com.mlog.util.ApiUtils.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/travel")
public class TravelController {

    private final TravelService travelService;

    @PostMapping("/{userId}")
    public ApiResult<Boolean> saveTravel(@PathVariable Long userId,
                                         @RequestBody SaveTravelRequest saveTravelRequest) {
        return success(travelService.saveTravel(userId, saveTravelRequest));
    }
}
