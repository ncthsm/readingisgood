package com.readingisgood.bookordermanagement.model.enums;

public enum OrderStatus {
    BASKET("BASKET"),DONE("DONE");

    private final String val;

    private OrderStatus(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return val;
    }
}
