package com.mlog.travel.controller;

import com.mlog.security.JwtAuthentication;
import com.mlog.travel.dto.SaveTravelRequest;
import com.mlog.travel.dto.TravelListResult;
import com.mlog.travel.dto.TravelDetailResult;
import com.mlog.travel.service.TravelService;
import com.mlog.travel.dto.TravelPhotoListResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.mlog.util.ApiUtils.ApiResult;
import static com.mlog.util.ApiUtils.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/travel")
public class TravelController {

    private final TravelService travelService;

    @PostMapping
    public ApiResult<Boolean> saveTravel(@AuthenticationPrincipal JwtAuthentication authentication,
                                         @RequestBody SaveTravelRequest saveTravelRequest) {
        return success(travelService.saveTravel(authentication.id, saveTravelRequest));
    }

    @GetMapping
    public ApiResult<TravelListResult> travelListResult(@AuthenticationPrincipal JwtAuthentication authentication) {
        return success(travelService.findAllTravel(authentication.id));
    }

    @GetMapping("/{travelId}")
    public ApiResult<TravelDetailResult> travelDetailResult(@PathVariable Long travelId) {
        return success(travelService.findTravelDetail(travelId));
    }

    @GetMapping("/photos/{travelDetailId}")
    public ApiResult<TravelPhotoListResult> travelDetailResultPhoto(@PathVariable Long travelDetailId) {
        return success(travelService.findTravelPhotoList(travelDetailId));
    }
}
