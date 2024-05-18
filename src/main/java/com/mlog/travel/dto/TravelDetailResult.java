package com.mlog.travel.dto;

import com.mlog.travel.entity.Travel;
import com.mlog.travel.entity.TravelDetail;
import com.mlog.travel.entity.TravelPhoto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class TravelDetailResult {
    private final String title;
    private final String description;
    private final String imageUrl;
    private final String lat;
    private final String lng;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Double rating;
    private final List<DetailedScheduleDTO> detailedSchedules;

    private TravelDetailResult(Travel travel, List<TravelDetail> travelDetails) {
        this.title = travel.getTitle();
        this.description = travel.getDescription();
        this.imageUrl = travel.getTitleImgUrl();
        this.lat = travel.getLat();
        this.lng = travel.getLng();
        this.startDate = travel.getStartAt();
        this.endDate = travel.getEndAt();
        this.rating = travel.getRating();
        this.detailedSchedules = travelDetails.stream()
                .map(DetailedScheduleDTO::of)
                .toList();
    }

    public static TravelDetailResult of(Travel travel, List<TravelDetail> travelDetails) {
        return new TravelDetailResult(travel, travelDetails);
    }

    @Getter
    @Setter
    @ToString
    public static class DetailedScheduleDTO {
        private final Long id;
        private final Integer seq;
        private final String title;
        private final String description;

        private DetailedScheduleDTO(TravelDetail travelDetail) {
            this.id = travelDetail.getId();
            this.seq = travelDetail.getSeq();
            this.title = travelDetail.getTitle();
            this.description = travelDetail.getDescription();
        }

        public static DetailedScheduleDTO of(TravelDetail travelDetail) {
            return new DetailedScheduleDTO(travelDetail);
        }
    }
}