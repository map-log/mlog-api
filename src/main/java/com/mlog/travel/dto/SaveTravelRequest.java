package com.mlog.travel.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class SaveTravelRequest {
    private String title;
    private String description;
    private String image;
    private String lat;
    private String lng;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double rating;
    private List<DetailedScheduleDTO> detailedSchedules;

    @Getter
    @Setter
    @ToString
    public static class DetailedScheduleDTO {
        private Integer seq;
        private String title;
        private String description;
        private List<String> images;
    }
}
