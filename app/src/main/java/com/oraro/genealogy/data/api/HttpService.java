package com.oraro.genealogy.data.api;

import com.oraro.genealogy.data.entity.Decision;
import com.oraro.genealogy.data.http.HttpResult;
import com.oraro.genealogy.data.entity.User;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/10/10.
 */
public interface HttpService {
    @POST("user/login/login.action")
    Observable<HttpResult<User>> login(
            @Header("token_info") String token,
            @Query("userName") String username,
            @Query("password") String password,
            @Query("deviceId") String deviceId,
            @Query("appVersion") String appVersion
    );

    @POST("user/resetPassword.action")
    Observable<HttpResult<Object>> resetPassword(
            @Header("token_info") String token,
            @Query("identityNumber") String idCard,
            @Query("userName") String phoneNum,
            @Query("newPassword") String newPassword
    );

    @POST("user/gestureLock/setGesturePwd.action")
    Observable<HttpResult<Object>> uploadLockPattern(
            @Header("token_info") String token,
            @Query("userId") int userId,
            @Query("gestureLockPwd") String lockPatternString
    );

    @POST("user/gestureLock/gestureLogin.action")
    Observable<HttpResult<Object>> checkLockPattern(
            @Header("token_info") String token,
            @Query("userId") int userId,
            @Query("gestureLockPwd") String lockPatternString
    );

    @Multipart
    @POST("form/familyResolution/create.action")
    Observable<HttpResult<Object>> submitVote(
            @Header("token_info") String token,
            @Query("userId") int userId,
            @Query("form_type_id") int formTypeId,
            @Part("form_datas") RequestBody gsonResult,
            @PartMap Map<String, RequestBody> picture
    );

    @POST("form/familyResolution/find/needReviewed.action")
    Observable<HttpResult<List<Decision>>> requestVoteVerifyList(
            @Header("token_info") String token,
            @Query("userId") int userId,
            @Query("page") int page,
            @Query("rows") int rows
    );

    @GET("form/familyResolution/get/reviewedFormData.action")
    Observable<HttpResult<Decision>> requestVoteVerifyDetail(
            @Header("token_info") String token,
            @Query("userId") int userId,
            @Header("formId") int formId
    );

    @POST("form/familyResolution/reviewed.action")
    Observable<HttpResult<Decision>> verifyVote(
            @Header("token_info") String token,
            @Query("userId") int userId,
            @Query("formId") int formId,
            @Query("form_type_id") int formTypeId,
            @Query("reviewedStatus") String reviewedStatus
    );

    @GET("form/familyResolution/GroupMembers/get.action")
    Observable<HttpResult<List<User>>> requestVoteGroup(
            @Header("token_info") String token,
            @Query("userId") int userId,
            @Query("group_type") int groupType,
            @Query("page") int page,
            @Query("rows") int rows
    );
}
