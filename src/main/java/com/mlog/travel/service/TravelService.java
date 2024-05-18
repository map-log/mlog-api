package com.mlog.travel.service;

import com.mlog.travel.dto.SaveTravelRequest;
import com.mlog.travel.dto.TravelDetailResult;
import com.mlog.travel.dto.TravelListResult;

public interface TravelService {
    boolean saveTravel(Long id, SaveTravelRequest saveTravelRequest);
    TravelListResult findAllTravel(Long userId);
    TravelDetailResult findTravelDetail(Long id);
}
