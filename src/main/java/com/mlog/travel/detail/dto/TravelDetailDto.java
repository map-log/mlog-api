package com.mlog.travel.detail.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TravelDetailDto {
    private String seq;
    private String description;
    private Double longitude;
    private Double latitude;
}
