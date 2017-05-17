package com.skeleton.retrofit;


import com.skeleton.model.Response;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;

import static com.skeleton.constant.ApiKeyConstant.AUTHORIZATION;
import static com.skeleton.retrofit.ApiInterface.USER_APP_CONSTANTS;

/**
 * Developer: Saurabh Verma
 * Dated: 27-09-2016.
 */
public interface ApiInterface {
    String UPDATE_LOCATION = "api/v1/user/updateLocation";
    String USER_SIGNUP = "api/user/register";
    String USER_LOGIN = "api/user/login";
    String USER_GET_PROFILE = "/api/user/getProfile";
    String USER_RESEND_OTP = "/api/user/resendOTP";
    String USER_VERIFY_OTP = "/api/user/verifyOTP";
    String USER_EDIT_PROFILE = "/api/user/updateProfile";
    String USER_APP_CONSTANTS = "/api/profile/constants";


    /**
     *
     * @param map hasmap
     * @return Response
     */
    @Multipart
    @POST(USER_SIGNUP)
    Call<com.skeleton.model.Response> userRegister(@PartMap HashMap<String, RequestBody> map);
    /**
     * Update location call.
     *
     * @param authorization the authorization
     * @param map           the map
     * @return the call
     */

    @POST(USER_LOGIN)
    Call<CommonResponse> userLogin(@Header(AUTHORIZATION) String authorization,
                                   @Body HashMap<String, String> map);

    /**
     * call to get profile
     * @param mAccessToken access token to get the profile
     * @return allowed or not
     */
    @GET(USER_GET_PROFILE)
    Call<Response> userProfile(@Header(AUTHORIZATION) String mAccessToken);



//    /**
//     * @param map
//     * @return
//     */
//    @Multipart
//    @POST("api/v1/user/createUser")
//    Call<LoginResponse> register(@PartMap HashMap<String, RequestBody> map);
//
//
//    /**
//     * @param map
//     * @return
//     */
//    @FormUrlEncoded
//    @PUT("api/v1/user/socialLogin")
//    Call<LoginResponse> socialLogin(@FieldMap HashMap<String, String> map);
//
//    /**
//     * @param authorization
//     * @param map
//     * @return
//     */
//    @FormUrlEncoded
//    @PUT("api/v1/user/loginToken")
//    Call<LoginResponse> accessTokenLogin(@Header(AUTHORIZATION) String authorization,
//                                         @FieldMap HashMap<String, String> map);
//
//
//    /**
//     * @param email
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("api/v1/user/forgotpassword")
//    Call<CommonResponse> forgotPassword(@Field("email") String email);
//
//    /**
//     * @param map
//     * @return
//     */
//    @FormUrlEncoded
//    @PUT("api/v1/user/loginCredential")
//    Call<LoginResponse> login(@FieldMap HashMap<String, String> map);


    /**
     * Update location call.
     *
     * @param authorization the authorization
     * @param map           the map
     * @return the call
     */
    @FormUrlEncoded
    @POST(UPDATE_LOCATION)
    Call<CommonParams> updateLocation(@Header(AUTHORIZATION) String authorization,
                                      @FieldMap HashMap<String, String> map);

    /**
     * @param mAccessToken token to send key
     * @return resend otp
     */
    @PUT(USER_RESEND_OTP)
    Call<CommonResponse> userResendOTP(@Header(AUTHORIZATION) String mAccessToken);

    /**
     *  to check verify
     * @param authorization access token
     * @param map Hash map
     * @return returns response
     */
    @PUT(USER_VERIFY_OTP)
    Call<CommonResponse> userVerifyOTP(@Header(AUTHORIZATION) String authorization,
                                       @Body HashMap<String, String> map);

    /**
     *  to change profile
     * @param authorization access token
     * @param map map to change
     * @return response
     */
    @Multipart
    @PUT(USER_EDIT_PROFILE)
    Call<Response> userUpdateProfile(@Header(AUTHORIZATION) String authorization,
                                           @PartMap HashMap<String, RequestBody> map);

    @GET(USER_APP_CONSTANTS)
    Call<Response> getConstants();

}

