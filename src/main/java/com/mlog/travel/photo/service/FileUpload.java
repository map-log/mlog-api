package com.mlog.travel.photo.service;

import com.mlog.travel.detail.entity.TravelDetail;
import com.mlog.travel.photo.entity.TravelPhoto;
import com.mlog.travel.photo.repository.TravelPhotoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileUpload {
     private final TravelPhotoMapper travelPhotoMapper;

    /**\
     * 이미지 업로드
     */
    // 루트 경로 불러오기
    @Value("${image.path}")
    private String storedPath;

    public String getFullPath(String filename) { return storedPath + filename; }

    public TravelPhoto storeFile(MultipartFile multipartFile, TravelDetail travelDetail) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        // 작성자가 업로드한 파일명 -> 서버 내부에서 관리하는 파일명
        // 파일명을 중복되지 않게끔 UUID로 정하고 ".확장자"는 그대로
        String storeFilename = UUID.randomUUID() + "." + extractExt(originalFilename);
        // 파일을 저장하는 부분 -> 파일경로 + storeFilename 에 저장
        multipartFile.transferTo(new File(getFullPath(storeFilename)));

        TravelPhoto travelPhoto = TravelPhoto.builder()
                                        .originalFileName(originalFilename)
                                        .storeUrl(storeFilename)
                                        .build();
        travelPhotoMapper.save(travelPhoto);

        return TravelPhoto.builder()
                .id(travelPhoto.getId())
                .originalFileName(travelPhoto.getOriginalFileName())
                .storeUrl(travelPhoto.getStoreUrl())
                .detail(travelDetail)
                .build();


    }

    // 확장자 추출
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
