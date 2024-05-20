package com.mlog.travel.mapper;

import com.mlog.travel.entity.Travel;
import com.mlog.travel.entity.TravelDetail;
import com.mlog.travel.entity.TravelPhoto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TravelMapper {

    @Select("select * from travel where user_id = #{userId} order by created_at desc")
    List<Travel> findTravelByUserId(Long userId);

    @Select("select * from travel where id = #{travelId}")
    Travel findTravelByTravelId(Long travelId);

    @Select("select * from travel_detail where travel_id = #{travelId} order by seq")
    List<TravelDetail> findTravelDetailByTravelId(Long travelId);

    @Select("select * from travel_photo where travel_detail_id = #{travelDetailId}")
    List<TravelPhoto> findTravelDetailPhotoByTravelDetailId(Long travelDetailId);

    @Insert("insert into travel (title, title_img_url, description, rating, lat, lng, start_at, end_at, created_at, updated_at, user_id) " +
            "values (#{title}, #{titleImgUrl}, #{description}, #{rating}, #{lat}, #{lng}, #{startAt}, #{endAt}, now(), now(), #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int saveTravel(Travel travel);

    @Insert("insert into travel_detail (seq, title, description, created_at, updated_at, travel_id) " +
            "values (#{seq}, #{title}, #{description}, now(), now(), #{travelId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int saveTravelDetail(TravelDetail travelDetail);

    @Insert("insert into travel_photo (photo_url, created_at, travel_detail_id) " +
            "values (#{photoUrl}, now(), #{travelDetailId})")
    int saveTravelPhoto(TravelPhoto travelDetailPhoto);
}