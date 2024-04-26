package com.mlog.travel.service;

import com.mlog.travel.dto.TravelDto;
import com.mlog.travel.mapper.TravelMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TravelServiceImpl implements TravelService {

    private final TravelMapper travelMapper;

    public TravelServiceImpl(TravelMapper travelMapper) {
        this.travelMapper = travelMapper;
    }

    public List<TravelDto> selectAllTravel(@Param("id") Long id) {
        return travelMapper.selectAllTravel(id);
    }

    public TravelDto selectTravelById(Long id) {
        return travelMapper.selectTravelById(id);
    }

    public boolean insertTravel(@Param("id") Long id, TravelDto travelDto) {
        if (travelMapper.insertTravel(id, travelDto) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateTravel(@Param("id") Long id, TravelDto travelDto) {
        if (travelMapper.updateTravel(id, travelDto) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteTravelById(@Param("id") Long id) {
        if (travelMapper.deleteTravelById(id) == 1) {
            return true;
        } else {
            return false;
        }
    }
}
