package com.mlog.travel.photo.repository;

import com.mlog.travel.photo.entity.TravelPhoto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TravelPhotoMapper {
//    @Insert("insert into travel_photo(photo_url,store_url,create_at,travel_detail_id) value(#{photoUrl},#{storeUrl},now(),#{travelDetailId})")
//    TravelPhoto save(TravelPhoto travelPhoto, Long travelDetailId);


    @Insert("insert into travel_photo(original_file_name,store_url,created_at,travel_detail_id) value(#{originalFileName},#{storeUrl},now(),1)")
    void save(TravelPhoto travelPhoto);
}
