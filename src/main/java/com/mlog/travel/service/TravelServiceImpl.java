package com.mlog.travel.service;

import com.mlog.aws.S3Service;
import com.mlog.travel.dto.SaveTravelRequest;
import com.mlog.travel.dto.TravelDetailResult;
import com.mlog.travel.dto.TravelListResult;
import com.mlog.travel.dto.TravelPhotoListResult;
import com.mlog.travel.entity.Travel;
import com.mlog.travel.entity.TravelDetail;
import com.mlog.travel.entity.TravelPhoto;
import com.mlog.travel.mapper.TravelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TravelServiceImpl implements TravelService {

    private final TravelMapper travelMapper;
    private final S3Service s3Service;

    public boolean saveTravel(Long userId, SaveTravelRequest saveTravelRequest) {
        Travel travel = Travel.of(
                saveTravelRequest,
                s3Service.uploadBase64Image(saveTravelRequest.getImage()),
                userId);
        travelMapper.saveTravel(travel);

        saveTravelRequest
                .getDetailedSchedules()
                .forEach(travelDetailDTO -> {
                    TravelDetail detailDetail = TravelDetail.of(travelDetailDTO, travel.getId());
                    travelMapper.saveTravelDetail(detailDetail);

                    travelDetailDTO.getImages()
                            .forEach(image -> travelMapper.saveTravelPhoto(


                                    TravelPhoto.builder()
                                            .photoUrl(s3Service.uploadBase64Image(image))
                                            .travelDetailId(detailDetail.getId())
                                            .build()
                            ));
                });
        return true;
    }

    public TravelListResult findAllTravel(Long userId) {
        return TravelListResult.of(travelMapper.findTravelByUserId(userId));
    }

    public TravelDetailResult findTravelDetail(Long id) {
        return TravelDetailResult
                .of(travelMapper.findTravelByTravelId(id), travelMapper.findTravelDetailByTravelId(id));
    }

    public TravelPhotoListResult findTravelPhotoList(Long id) {
        return TravelPhotoListResult
                .of(travelMapper.findTravelDetailPhotoByTravelDetailId(id));

    }
}
