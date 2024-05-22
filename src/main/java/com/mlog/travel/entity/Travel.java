package com.mlog.travel.entity;

import com.mlog.travel.dto.SaveTravelRequest;
import com.mlog.travel.dto.UpdateTravelRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class Travel {
    private Long id;
    private String title;
    private String titleImgUrl;
    private String description;
    private Double rating;
    private String lat;
    private String lng;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;

    public static Travel of(SaveTravelRequest saveTravelRequest, String titleImgUrl ,Long userId) {
        return Travel.builder()
                .title(saveTravelRequest.getTitle())
                .titleImgUrl(titleImgUrl)
                .description(saveTravelRequest.getDescription())
                .rating(saveTravelRequest.getRating())
                .lat(saveTravelRequest.getLat())
                .lng(saveTravelRequest.getLng())
                .startAt(saveTravelRequest.getStartDate())
                .endAt(saveTravelRequest.getEndDate())
                .userId(userId)
                .build();
    }

    public static Travel of(Travel travel, UpdateTravelRequest updateTravelRequest) {
        return Travel.builder()
                .title(updateTravelRequest.getTitle() != null ? updateTravelRequest.getTitle() : travel.getTitle())
                .titleImgUrl(updateTravelRequest.getImage() != null ? updateTravelRequest.getImage() : travel.getTitleImgUrl())
                .description(updateTravelRequest.getDescription() != null ? updateTravelRequest.getDescription() : travel.getDescription())
                .rating(updateTravelRequest.getRating() != null ? updateTravelRequest.getRating() : travel.getRating())
                .lat(updateTravelRequest.getLat() != null ? updateTravelRequest.getLat() : travel.getLat())
                .lng(updateTravelRequest.getLng() != null ? updateTravelRequest.getLng() : travel.getLng())
                .startAt(updateTravelRequest.getStartDate() != null ? updateTravelRequest.getStartDate() : travel.getStartAt())
                .build();
    }
}
