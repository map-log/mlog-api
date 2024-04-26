package com.mlog.travel.detail.service;

import com.mlog.travel.detail.dto.TravelDetailDto;
import com.mlog.travel.detail.entity.TravelDetail;
import com.mlog.travel.detail.repository.TravelDetailMapper;
import com.mlog.travel.photo.service.FileUpload;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class TravelDetailServiceImpl implements TravelDetailService{

    private final TravelDetailMapper travelDetailMapper;
    private final FileUpload fileUpload;

    @Override
    @Transactional
    public TravelDetail save(TravelDetailDto travelDetailDto, MultipartFile multipartFile) throws IOException {
        TravelDetail travelDetail = TravelDetail.builder()
                .seq(travelDetailDto.getSeq())
                .description(travelDetailDto.getDescription())
                .longitude(travelDetailDto.getLongitude())
                .latitude(travelDetailDto.getLatitude())
                .build();
        travelDetailMapper.save(travelDetailDto);

        if(!multipartFile.isEmpty())
            fileUpload.storeFile(multipartFile,travelDetail);

        return travelDetail;
    }
}
