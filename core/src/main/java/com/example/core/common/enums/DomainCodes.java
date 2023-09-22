package com.example.core.common.enums;

import lombok.Getter;

@Getter
public enum DomainCodes {

    ;

    DomainCodes(String dcd, String message) {
        this.dcd = dcd;
        this.message = message;
    }

    private final String dcd;
    private final String message;

}
