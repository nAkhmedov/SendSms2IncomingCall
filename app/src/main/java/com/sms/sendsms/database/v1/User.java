package com.sms.sendsms.database.v1;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table user.
 */
public class User {

    private Long id;
    private String email;
    private String password;
    private String messageBody;
    /** Not-null value. */
    private String messageCode;
    private java.util.Date disabledDate;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String email, String password, String messageBody, String messageCode, java.util.Date disabledDate) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.messageBody = messageBody;
        this.messageCode = messageCode;
        this.disabledDate = disabledDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    /** Not-null value. */
    public String getMessageCode() {
        return messageCode;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public java.util.Date getDisabledDate() {
        return disabledDate;
    }

    public void setDisabledDate(java.util.Date disabledDate) {
        this.disabledDate = disabledDate;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
