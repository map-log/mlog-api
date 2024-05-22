package com.mlog.travel.service;

import com.mlog.travel.dto.SaveTravelRequest;
import com.mlog.travel.dto.TravelDetailResult;
import com.mlog.travel.dto.TravelListResult;
import com.mlog.travel.dto.TravelPhotoListResult;

public interface TravelService {
    Boolean saveTravel(Long id, SaveTravelRequest saveTravelRequest);
    TravelListResult findAllTravel(Long userId);
    TravelDetailResult findTravelDetail(Long id);
    TravelPhotoListResult findTravelPhotoList(Long id);
    Boolean deleteTravel(Long travelId);
}
