package com.mlog.travel.entity;

import com.mlog.travel.dto.SaveTravelRequest;
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
}
