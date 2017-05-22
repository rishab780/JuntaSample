package com.skeleton.constant;

/**
 * Developer: Saurabh Verma
 * Dated: 19-02-2017.
 */

public interface AppConstant {
    String DEVICE_TYPE = "ANDROID";

    //Intent Filter
    String NOTIFICATION_RECEIVED = "notification_received";

    //
    int SESSION_EXPIRED = 401;

    //Request code
    int REQ_CODE_PROFILE_COMPLETENESS = 103;
    int REQ_CODE_OTP = 102;
    int REQ_CODE_EDIT_NUMBER = 501;
    int REQ_CODE_SIGNIN_SIGNUP_ACTIVITY = 101;
    int REQ_CODE_DEFAULT_SETTINGS = 16061;
    int REQ_CODE_PLAY_SERVICES_RESOLUTION = 16061;
    int REQ_CODE_SCREEN_OVERLAY = 10101;
    String KEY_EMAIL = "email";
    String KEY_PASSWORD = "password";
    String KEY_LANGUAGE = "language";
    String KEY_DEVICE_TYPE = "deviceType";
    String KEY_DEVICE_TOKEN = "deviceToken";
    String KEY_APP_VERSION = "appVersion";
    String KEY_FLUSH_SESSIONS= "flushPreviousSessions";

    String VALUE_LANGUAGE = "EN";
    String VALUE_DEVICE_TYPE = "ANDROID";
    String VALUE_DEVICE_TOKEN = "token";
    int VALUE_APP_VERSION = 100;
    String VALUE_FLUSH_SESSION = "true";


    String KEY_FNAME = "firstName";
    String KEY_DOB = "dob";

    String KEY_COUNTRY_CODE = "countryCode";
    String KEY_PHONE = "phoneNo";
    String KEY_GENDER = "gender";
    String KEY_ORIENTATION = "orientation";
    String KEY_PROFILE_PIC = "profilePic";
    String KEY_USER_DETAILS = "userDetails";

    String KEY_OTP = "OTPCode";
    String KEY_NEW_NUMBER = "newNumber";
    String KEY_RELATIONSHIP_HISTORY = "relationshipHistory";

    String KEY_ETHNICITY = "ethnicity";
    String KEY_RELIGION = "religion";

    String KEY_HEIGHT = "height";
    String KEY_BODY_TYPE = "bodyType";
    String KEY_SMOKING = "smoking";
    String KEY_DRINKING = "drinking";
}
