package com.example.test.ui_models;

public class ReceiptModel {
    private int trans_id;
    private boolean isSuccess;
    private String transTitle;
    private String transDesc;
    private String transAmt;
    private ReceiverUserModel receiverUserModel;
    private String dateTime;
    private String transRef;

    private ReceiptModel(int trans_id, boolean isSuccess, String transTitle,
                        String transDesc, String transAmt, ReceiverUserModel receiverUserModel,
                        String dateTime, String transRef) {
        this.trans_id = trans_id;
        this.isSuccess = isSuccess;
        this.transTitle = transTitle;
        this.transDesc = transDesc;
        this.transAmt = transAmt;
        this.receiverUserModel = receiverUserModel;
        this.dateTime = dateTime;
        this.transRef = transRef;
    }

    public int getTrans_id() {
        return trans_id;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getTransTitle() {
        return transTitle;
    }

    public String getTransDesc() {
        return transDesc;
    }

    public String getTransAmt() {
        return transAmt;
    }

    public ReceiverUserModel getReceipenttModel() {
        return receiverUserModel;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getTransRef() {
        return transRef;
    }

    public static class Builder {
        private int transId;
        private boolean isSuccess;
        private String transTitle;
        private String transDesc;
        private String transAmt;
        private ReceiverUserModel receiverUserModel;
        private String dateTime;
        private String transRef;

        public Builder setTransId(int trans_id) {
            this.transId = trans_id;
            return this;
        }

        public Builder setSuccess(boolean success) {
            isSuccess = success;
            return this;
        }

        public Builder setTransTitle(String transTitle) {
            this.transTitle = transTitle;
            return this;
        }

        public Builder setTransDesc(String transDesc) {
            this.transDesc = transDesc;
            return this;
        }

        public Builder setTransAmt(String transAmt) {
            this.transAmt = transAmt;
            return this;
        }

        public Builder setReceiptModel(ReceiverUserModel receiverUserModel) {
            this.receiverUserModel = receiverUserModel;
            return this;
        }

        public Builder setDateTime(String dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder setTransRef(String transRef) {
            this.transRef = transRef;
            return this;
        }

        public ReceiptModel build() {
            return new ReceiptModel(transId, isSuccess, transTitle, transDesc, transAmt, receiverUserModel, dateTime, transRef);
        }
    }
}
