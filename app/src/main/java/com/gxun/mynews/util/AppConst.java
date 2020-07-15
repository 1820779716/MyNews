package com.gxun.mynews.util;

/**
 * user：lqm
 * desc：
 */

public class AppConst {

    public static final String SERVER_ADDRESS = "http://127.0.0.1:8080/";
    //public static final String SERVER_ADDRESS = "http://10.0.2.2:8080"; //模拟器访问本地服务器地址
//	public static final String SERVER_ADDRESS = "http://192.168.0.106:8080"; //手机访问本地服务器地址（本机电脑IP）
//    public static final String SERVER_ADDRESS = "http://外网IP:8080/"; //外网



    public static final String TOKEN_KEY = "token_key";
    public static final String IMAGE_HOST = "http://lqmdemo.oss-cn-beijing.aliyuncs.com/";


    public static final String getStsToken = SERVER_ADDRESS + "/file/getStsToken";


    public interface UserInfo{
         String login = SERVER_ADDRESS+"/login";
        // String logout = SERVER_ADDRESS+"/logout.do";
         String register = SERVER_ADDRESS+"register";
         String getUser = SERVER_ADDRESS+"/getUser";
        String checkEmailOrTel = SERVER_ADDRESS+"/checkEmailOrTel";
         String forgetPassword=SERVER_ADDRESS+"/forgetPassword";
         String modifyUserInformaintion=SERVER_ADDRESS+"/modifyUserInformaintion";
         String changePassword=SERVER_ADDRESS+"/changePassword";
    }


    public interface NewsInfo{

        String getNews = SERVER_ADDRESS+"/getNews";

    }



    public interface History{

        String getHistory = SERVER_ADDRESS+"/getHistory";
        String addHistory = SERVER_ADDRESS+"/addHistory";

        String deleteHistory = SERVER_ADDRESS+"/deleteHistory";
        String deleteAllHistory = SERVER_ADDRESS+"/deleteAllHistory";

    }


    public interface Collect{
        String getCollect = SERVER_ADDRESS+"/getCollect";
        String addCollect = SERVER_ADDRESS+"/addCollect";
        String delCollect = SERVER_ADDRESS+"/delCollect";
        String delAllCollect = SERVER_ADDRESS+"/delAllCollect";
    }



}
