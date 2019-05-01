package com.project.usm.app.DTO;

import java.time.Instant;

import lombok.Getter;

enum AccountStatus {
    OPEN,
    CLOSED,
    DISABLED
}

enum EducationFormType {
    FULL_TIME,
    CORRESPONDENCE
}

enum PayStatus {
    PAID,
    UNPAID
}
@Getter
public class UserProfileResponseResource {
    private String educationType;
    private String groupId;
    private PayStatus payStatus;
    private EducationFormType educationFormType;
    private String carnetId;
    private AccountStatus status;
    private String idnp;
    private String firstName;
    private String lastName;
    private String sex;
    private String streetAddress;
    private String dateOfBirth;
    private String email;
    private String phoneNumber;
    private String profileImageUrl;
    private String created;
    private String lastUpdated;
    private Boolean smsNotificationActive;
    private Boolean emailNotificationActive;
    private String speciality;
    private String faculty;

    @Override
    public String toString() {
        return "UserProfileResponseResource{" +
                "groupId='" + groupId + '\'' +
                ", payStatus=" + payStatus +
                ", educationFormType=" + educationFormType +
                ", carnetId='" + carnetId + '\'' +
                ", status=" + status +
                ", idnp='" + idnp + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", sex='" + sex + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", created='" + created + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                ", smsNotificationActive=" + smsNotificationActive +
                ", emailNotificationActive=" + emailNotificationActive +
                '}';
    }
}
