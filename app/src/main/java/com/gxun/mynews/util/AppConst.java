package com.gxun.mynews.util;

public class AppConst {

    public static final String SERVER_ADDRESS = "http://192.168.1.110:8080/"; // 后端服务地址

    public interface UserInfo{
         String login = SERVER_ADDRESS + "login";
         String register = SERVER_ADDRESS + "register";
         String getUser = SERVER_ADDRESS + "getUser";
         String checkEmailOrTel = SERVER_ADDRESS + "checkEmailOrTel";
         String forgetPassword = SERVER_ADDRESS + "forgetPassword";
         String modifyUserInformation = SERVER_ADDRESS + "modifyUserInformation";
         String changePassword = SERVER_ADDRESS + "changePassword";
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
