package com.mlog.travel.entity;

import com.mlog.travel.dto.SaveTravelRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class TravelDetail {
    private Long id;
    private Integer seq;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long travelId;

    public static TravelDetail of(SaveTravelRequest.DetailedScheduleDTO detailedScheduleDTO,
                                  Long travelId) {
        return TravelDetail.builder()
                .seq(detailedScheduleDTO.getSeq())
                .title(detailedScheduleDTO.getTitle())
                .description(detailedScheduleDTO.getDescription())
                .travelId(travelId)
                .build();
    }
}
