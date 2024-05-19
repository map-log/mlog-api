package com.mlog.travel.dto;

import com.mlog.travel.entity.TravelPhoto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class TravelPhotoListResult {
    private final List<TravelPhotoInfo> travelPhotoList;

    private TravelPhotoListResult(final List<TravelPhotoInfo> travelPhotoList) {
        this.travelPhotoList = travelPhotoList;
    }

    public static TravelPhotoListResult of(List<TravelPhoto> travelPhotos) {
        return new TravelPhotoListResult(travelPhotos.stream()
                .map(TravelPhotoInfo::new)
                .toList());
    }

    @Getter
    @Setter
    @ToString
    public static class TravelPhotoInfo {
        private final String imageUrl;

        public TravelPhotoInfo(TravelPhoto photo) {
            this.imageUrl = photo.getPhotoUrl();
        }
    }
}
