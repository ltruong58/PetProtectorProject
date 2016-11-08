package com.example.longtruong.cs273.ltruong58.petprotector;

import android.net.Uri;

/**
 * Model of app
 * Store and process data for a pet
 * Created by Long Truong on 11/3/2016.
 */

public class Pet {
    private int mId;
    private String mName;
    private String mDetails;
    private String mPhone;
    private Uri mImageURI;

    /**
     * Default constructor
     */
    public Pet() {
        this.mId = -1;
        this.mName = "";
        this.mDetails =  "";
        this.mPhone =  "";
        this.mImageURI = null;
    }

    /**
     * Full constructor
     * @param mId
     * @param mName
     * @param mDetails
     * @param mPhone
     * @param mImageURI
     */
    public Pet(int mId, String mName, String mDetails, String mPhone, Uri mImageURI) {
        this.mId = mId;
        this.mName = mName;
        this.mDetails = mDetails;
        this.mPhone = mPhone;
        this.mImageURI = mImageURI;
    }

    /**
     * Second constructor
     * @param mName
     * @param mDetails
     * @param mPhone
     * @param mImageURI
     */
    public Pet(String mName, String mDetails, String mPhone, Uri mImageURI) {
        this.mId = -1;
        this.mName = mName;
        this.mDetails = mDetails;
        this.mPhone = mPhone;
        this.mImageURI = mImageURI;
    }

    /**
     * Get ID of pet
     * @return
     */
    public int getId() {
        return mId;
    }

    /**
     * Get name of pet
     * @return
     */
    public String getName() {
        return mName;
    }

    /**
     * Set name of pet
     * @param mName
     */
    public void setName(String mName) {
        this.mName = mName;
    }

    /**
     * get details of pet
     * @return
     */
    public String getDetails() {
        return mDetails;
    }

    /**
     * set details of pet
     * @param mDetails
     */
    public void setDetails(String mDetails) {
        this.mDetails = mDetails;
    }

    /**
     * get phone to ask about pet
     * @return
     */
    public String getPhone() {
        return mPhone;
    }

    /**
     * set phone to ask about pet
     * @param mPhone
     */
    public void setPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    /**
     * get image uri of pet
     * @return
     */
    public Uri getImageURI() {
        return mImageURI;
    }

    /**
     * set image uri of pet
     * @param mImageURI
     */
    public void setImageURI(Uri mImageURI) {
        this.mImageURI = mImageURI;
    }

    /**
     * get all information of pet
     * @return
     */
    public String toString(){
        return getName() + getDetails() + getPhone();
    }
}
