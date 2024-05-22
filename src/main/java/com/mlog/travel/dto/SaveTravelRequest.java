package com.mlog.travel.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

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

    public static SaveTravelRequest of(UpdateTravelRequest updateTravelRequest) {
        SaveTravelRequest request = new SaveTravelRequest();
        copyProperties(updateTravelRequest, request);
        request.setDetailedSchedules(updateTravelRequest.getDetailedSchedules()
                .stream()
                .map(DetailedScheduleDTO::of)
                .toList());
        return request;
    }

    @Getter
    @Setter
    @ToString
    public static class DetailedScheduleDTO {
        private Integer seq;
        private String title;
        private String description;
        private List<String> images;

        public static SaveTravelRequest.DetailedScheduleDTO of
                (UpdateTravelRequest.DetailedScheduleDTO detailedScheduleDTO) {
            DetailedScheduleDTO detailedSchedule = new SaveTravelRequest.DetailedScheduleDTO();
            copyProperties(detailedScheduleDTO, detailedSchedule);
            return detailedSchedule;
        }
    }
}
