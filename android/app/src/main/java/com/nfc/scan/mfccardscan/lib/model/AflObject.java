package com.nfc.scan.mfccardscan.lib.model;


public class AflObject {

    private int sfi;
    private int recordNumber;
    private byte[] readCommand;

    public int getSfi() {
        return sfi;
    }

    public void setSfi(int sfi) {
        this.sfi = sfi;
    }

    public int getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }

    public byte[] getReadCommand() {
        return readCommand;
    }

    public void setReadCommand(byte[] readCommand) {
        this.readCommand = readCommand;
    }
}
