package com.alphasolutions.patisserie.Enum;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    MONEY(1),CREDITCARD(2),DEBITCARD(3),PIX(4);

    final Integer value;
    PaymentMethod(int value) {
        this.value = value;
    }
}
