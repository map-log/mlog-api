package com.mlog.travel.service;

import com.mlog.travel.dto.SaveTravelRequest;

public interface TravelService {
    boolean saveTravel(Long id, SaveTravelRequest saveTravelRequest);
}
