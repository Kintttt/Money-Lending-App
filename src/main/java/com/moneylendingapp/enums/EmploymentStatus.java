package com.moneylendingapp.enums;

import com.moneylendingapp.exceptions.BadRequestException;

public enum EmploymentStatus {
    SELF_EMPLOYED,
    UNEMPLOYED,
    CONTRACT,
    FULL_TIME;

    EmploymentStatus() {
    }

    public static EmploymentStatus get(String type) {
        for (EmploymentStatus status : values()) {
            if (status.toString().equalsIgnoreCase(type)) {
                return status;
            }
        }
        throw new BadRequestException("Unable to find any matching status to honor your request for action " + type);
    }
}
