package com.example.demo.Enum;

/**
 * Перечисление типов продукции
 */
public enum ProductType {
    APPLE_TYPE_1("Яблоко тип 1"),
    APPLE_TYPE_2("Яблоко тип 2"),
    PEAR_TYPE_1("Груша тип 1"),
    PEAR_TYPE_2("Груша тип 2");

    private final String displayName;

    ProductType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
