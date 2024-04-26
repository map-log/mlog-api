package com.mlog.travel.detail.service;

import com.mlog.travel.detail.dto.TravelDetailDto;
import com.mlog.travel.detail.entity.TravelDetail;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface TravelDetailService {
    TravelDetail save(TravelDetailDto travelDetailDto, MultipartFile multiPartFile) throws IOException;
}
