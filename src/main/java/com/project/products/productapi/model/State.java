package com.project.products.productapi.model;

import lombok.Data;
import lombok.Getter;

@Getter
public enum State {
    ACTIVE,DELETED;

    State() {
    }
}
