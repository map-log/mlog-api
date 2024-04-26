package com.mlog.travel.entity;

import com.mlog.travel.dto.TravelDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Travel extends TravelDto {

    private Long id;
    private String title;
    private String map_marker_url;
    private Double longitude;
    private Double latitude;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private Long userId;
}
