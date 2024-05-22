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

    public static TravelDetail of(TravelDetail travelDetail,
                                  UpdateTravelRequest.DetailedScheduleDTO detailedScheduleDTO) {
        return TravelDetail.builder()
                .id(travelDetail.getId())
                .seq(detailedScheduleDTO.getSeq() != null ? detailedScheduleDTO.getSeq() : travelDetail.getSeq())
                .title(detailedScheduleDTO.getTitle() != null ? detailedScheduleDTO.getTitle() : travelDetail.getTitle())
                .description(detailedScheduleDTO.getDescription() != null ? detailedScheduleDTO.getDescription() : travelDetail.getDescription())
                .travelId(travelDetail.getTravelId())
                .build();
    }
}
