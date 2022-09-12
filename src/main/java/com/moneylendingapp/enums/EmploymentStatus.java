package com.moneylendingapp.enums;

import liquibase.repackaged.org.apache.commons.lang3.NotImplementedException;

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
        throw new NotImplementedException("Unable to find any matching status to honor your request for action: " + type);
    }
}
