package xyz.girudo.jetset.entities;

import io.realm.RealmObject;

/**
 * Created by Novyandi Nurahmad on 2/5/17
 */

public class User extends RealmObject {
    private String firstName;
    private String lastName;
    private String ccNumber;
    private String ccvNumber;
    private int month;
    private int year;
    private boolean statusShipping;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;

    public User(User user) {
        super();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        ccNumber = user.getCcNumber();
        ccvNumber = user.getCcvNumber();
        month = user.getMonth();
        year = user.getYear();
        statusShipping = user.isStatusShipping();
        address1 = user.getAddress1();
        address2 = user.getAddress2();
        city = user.getCity();
        state = user.getState();
        zip = user.getZip();
    }

    public User() {
        super();
        firstName = "";
        lastName = "";
        ccNumber = "";
        ccvNumber = "";
        address1 = "";
        address2 = "";
        city = "";
        state = "";
        zip = "";
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getFirstName()).append(", ");
        builder.append(getLastName()).append(", ");
        builder.append(getCcNumber()).append(", ");
        builder.append(getCcvNumber()).append(", ");
        builder.append(getMonth()).append("/").append(String.valueOf(getYear())).append(", ");
        builder.append("Status Shipping : ").append(String.valueOf(isStatusShipping())).append(", ");
        builder.append(getAddress1()).append(", ");
        builder.append(getAddress2()).append(", ");
        builder.append(getCity()).append(", ");
        builder.append(getState()).append(", ");
        builder.append(getZip());
        return builder.toString();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public String getCcvNumber() {
        return ccvNumber;
    }

    public void setCcvNumber(String ccvNumber) {
        this.ccvNumber = ccvNumber;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isStatusShipping() {
        return statusShipping;
    }

    public void setStatusShipping(boolean statusShipping) {
        this.statusShipping = statusShipping;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}