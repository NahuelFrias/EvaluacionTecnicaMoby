package com.test.backend.developer.test_backend_nahuel.models.enums;

public enum DocumentType {
    DNI("DNI"),LC("LC"),LE("LE");

    private final String dt;

    DocumentType(final String dt) {
        this.dt = dt;
    }

    public String getDt() {
        return dt;
    }
}
