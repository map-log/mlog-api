package com.mlog.travel.service;

import com.mlog.travel.dto.TravelDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TravelService {
    public List<TravelDto> selectAllTravel(@Param("id") Long id);

    public TravelDto selectTravelById(Long id);

    public boolean insertTravel(@Param("id") Long id, TravelDto travelDto);

    public boolean updateTravel(@Param("id") Long id, TravelDto travelDto);

    public boolean deleteTravelById(@Param("id") Long id);
}
