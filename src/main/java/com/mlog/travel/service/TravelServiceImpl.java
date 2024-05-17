package com.mlog.travel.service;

import com.mlog.aws.S3Service;
import com.mlog.travel.dto.SaveTravelRequest;
import com.mlog.travel.entity.Travel;
import com.mlog.travel.entity.TravelDetail;
import com.mlog.travel.entity.TravelPhoto;
import com.mlog.travel.mapper.TravelMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TravelServiceImpl implements TravelService {

    private final TravelMapper travelMapper;
    private final S3Service s3Service;

    @Transactional
    public boolean saveTravel(Long userId, SaveTravelRequest saveTravelRequest) {

        Travel travel = Travel.of(
                saveTravelRequest,
                s3Service.uploadBase64Image(saveTravelRequest.getImage()),
                userId);
        travelMapper.saveTravel(travel);

        saveTravelRequest
                .getDetailedSchedules().stream()
                .forEach(travelDetailDTO -> {

                    TravelDetail detailDetail = TravelDetail.of(travelDetailDTO, travel.getId());
                    travelMapper.saveTravelDetail(detailDetail);

                    travelDetailDTO.getImages().stream()
                            .forEach(image -> travelMapper.saveTravelPhoto(

                                    TravelPhoto.builder()
                                            .photoUrl(s3Service.uploadBase64Image(image))
                                            .travelDetailId(detailDetail.getTravelId())
                                            .build()
                            ));
                });
        return true;
    }
}
