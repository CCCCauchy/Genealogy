package com.oraro.genealogy.constant;

/**
 * Created by Administrator on 2016/7/1.
 */
public class Constant {

    public static String  ERROR_TITLE="网络连接异常";
    public static String  ERROR_CONTEXT="请检查网络后重试";
    public static String  ERROR_BUTTON="重试";

    public static String EMPTY_TITLE="没有找到数据";
    public static String EMPTY_CONTEXT="换个条件试试吧";

    public static final String SHARED_PREFERENCE_NAME ="Genealogy";

    //服务器表单类型
    public static final int FAMILYDECISION = 1;

    //用户角色
    public static final int NORMAL = 4;//普通
    public static final int ELDER = 3;//长老
    public static final int SECRETARY = 2;//族长秘书
    public static final int PATRIARCH = 1;//族长



    //投票审核参数
    public static final String APPROVE = "OK";//通过
    public static final String REJECT = "NOK";//拒绝


    //决议状态
    public static final int NEED_APPROVE = 0;//未审核
    public static final int IN_PROGRESS = 1;//进行中
    public static final int CLOSED = 2;//已关闭
    public static final int NOT_APPROVE = 3;//已通过

    //服务器路径
//    public static final String API_SERVER = "http://192.168.1.103:8080";
    public static final String API_SERVER = "http://172.22.105.1:8080/genealogy";


    //设缓存有效期为7天
    public static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
    //查询缓存的Cache-Control设置，使用缓存
    public static final String CACHE_CONTROL_CACHE = "max-age=" + CACHE_STALE_SEC;
}
