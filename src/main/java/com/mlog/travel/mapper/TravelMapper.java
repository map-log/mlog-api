package com.mlog.travel.mapper;

import com.mlog.travel.dto.TravelDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TravelMapper {

    @Select("select * from travel where user_id = #{id}")
    public List<TravelDto> selectAllTravel(Long id);

    public TravelDto selectTravelById(Long id);

    @Insert("insert into travel (title,map_marker_url,longitude,latitude,created_at,updated_at,user_id) " +
            "values (#{travelDto.title},#{travelDto.map_marker_url},#{travelDto.longitude},#{travelDto.latitude},now(),now(),#{id})")
    public int insertTravel(Long id, TravelDto travelDto);

    @Update("update travel set title=#{travelDto.title}, map_marker_url=#{travelDto.map_marker_url}, longitude=#{travelDto.longitude},latitude=#{travelDto.latitude},updated_at=now()" +
            "where id = #{id}")
    public int updateTravel(Long id, TravelDto travelDto);

    @Delete("delete from travel where user_id = #{id}")
    public int deleteTravelById(Long id);
}
