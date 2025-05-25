package com.alphasolutions.patisserie.Enum;

public enum OrderStatus {

    preparing(1),
    waiting(2),
    ready(3);

    public final int status;
    OrderStatus(int i) {
        this.status = i;
    }
}
