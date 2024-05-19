package com.mlog.travel.dto;

import com.mlog.travel.entity.Travel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class TravelListResult {

    private final List<TravelInfo> travelList;

    private TravelListResult(final List<TravelInfo> travelList) {
        this.travelList = travelList;
    }

    public static TravelListResult of(List<Travel> travelList) {
        return new TravelListResult(travelList.stream()
                .map(TravelInfo::new)
                .toList());
    }

    @Getter
    @Setter
    @ToString
    public static class TravelInfo {
        private final Long id;
        private final String title;
        private final String description;
        private final String imageUrl;
        private final String lat;
        private final String lng;

        public TravelInfo(final Travel travel) {
            this.id = travel.getId();
            this.title = travel.getTitle();
            this.description = travel.getDescription();
            this.imageUrl = travel.getTitleImgUrl();
            this.lat = travel.getLat();
            this.lng = travel.getLng();
        }
    }
}
