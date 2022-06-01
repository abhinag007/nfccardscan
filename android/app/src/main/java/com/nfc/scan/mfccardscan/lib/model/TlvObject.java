package com.nfc.scan.mfccardscan.lib.model;


public class TlvObject {

    private byte[] tlvTag;
    private int tlvTagLength;

    public TlvObject(byte[] tlvTag, int tlvTagLength) {
        this.tlvTag = tlvTag;
        this.tlvTagLength = tlvTagLength;
    }

    public byte[] getTlvTag() {
        return tlvTag;
    }

    public void setTlvTag(byte[] tlvTag) {
        this.tlvTag = tlvTag;
    }

    public int getTlvTagLength() {
        return tlvTagLength;
    }

    public void setTlvTagLength(int tlvTagLength) {
        this.tlvTagLength = tlvTagLength;
    }
}