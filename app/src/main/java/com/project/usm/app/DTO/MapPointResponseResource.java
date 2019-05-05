package com.project.usm.app.DTO;

import lombok.Getter;
import lombok.Setter;

enum PointType {
    CLIENT_POINT,
    USER_POINT
}
@Getter
@Setter
public class MapPointResponseResource {
    private PointType pointType;
    private String title;
    private String address;
    private Double latitude;
    private Double longitude;
    private String imageUrl;

    @Override
    public String toString() {
        return "MapPointResponseResource{" +
                "pointType=" + pointType +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    public MapPointResponseResource(PointType pointType, String title, String address, Double latitude, Double longitude, String imageUrl) {
        this.pointType = pointType;
        this.title = title;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageUrl = imageUrl;
    }
}
