package com.mlog.travel.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class TravelPhoto {
    private Long id;
    private String photoUrl;
    private LocalDateTime createdAt;
    private Long travelDetailId;
}
