package com.nfc.scan.mfccardscan.lib.enums;

public enum CardType {

    MC("MasterCard"), VISA("Visa"), AMEX("AmericanExpress"), TROY("Troy"), UNKNOWN("N/A");

    private String cardBrand;

    CardType(String cardBrand) {
        this.cardBrand = cardBrand;
    }

    public String getCardBrand() {
        return cardBrand;
    }
}
