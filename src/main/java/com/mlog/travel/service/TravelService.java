package com.mlog.travel.service;

import com.mlog.travel.dto.*;

public interface TravelService {
    Boolean saveTravel(Long id, SaveTravelRequest saveTravelRequest);
    TravelListResult findAllTravel(Long userId);
    TravelDetailResult findTravelDetail(Long id);
    TravelPhotoListResult findTravelPhotoList(Long id);
    Boolean deleteTravel(Long travelId);
    Boolean updateTravel(Long userId, Long travelId, UpdateTravelRequest updateTravelRequest);
}
