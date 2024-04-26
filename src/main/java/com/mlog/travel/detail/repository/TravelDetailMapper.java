package com.mlog.travel.detail.repository;

import com.mlog.travel.detail.dto.TravelDetailDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TravelDetailMapper {

    @Insert("insert into travel_detail(seq,description,location) value(#{seq},#{description},POINT(#{longitude},#{latitude}))")
    void save(TravelDetailDto travelDetailDto);
}
