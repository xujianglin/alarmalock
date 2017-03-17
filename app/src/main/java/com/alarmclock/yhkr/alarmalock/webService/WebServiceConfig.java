package com.alarmclock.yhkr.alarmalock.webService;

/**
 * Created by dingl on 2016/12/10.
 */

public class WebServiceConfig {
    public final static String nameSpace="http://tempuri.org/";

    public final static String endPoint="http://139.224.198.185/WebService.asmx";

    public final static String loginMethodName="Login";

    public final static String registerMethodName="Register";

    public final static String changePwdMethodName="ChangePwd";

    public final static String registerSubMethodName="RegisterSub";
    public final static String queryMethodName="Query";
    public final static String uploadImageMethodName="UploadImage";
    public final static String addUserInfoMethodName="ChangeUserInfo";
    public final static String changeUserInfoMethodName="changeUserInfo";
    public final static String DeleteMedicalRecordMethodName="DeleteMedicalRecord";
    public final static String AddAlarmClockRecordMethodName="AddAlarmClock";
    public final static String AddMedicalRecordRecordMethodName="AddMedicalRecord ";
    public final static String DeleteAlarmClockRecordMethodName="DeleteAlarmClock";
    public final static String AddMedicalRecord_2RecordMethodName="AddMedicalRecord2 ";

    public final static String addImageMethodName="AddImage";

    public final static String deleteImageMethodName="DeleteImage";

    //请求数据库中一个未被占用的网易云讯账号
    public final static String registerCommunicationMethodName="RegisterCommunication";

    //使用完成之后解锁账号
    public final static String unLockCommunicationMethodName="UnLockCommunication";
}
