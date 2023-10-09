package com.izaguirre.mascotas.user;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class JDbCUtil {
    private static JDbCUtil instance;

    public static JDbCUtil getInstance() {
        if (instance == null){
            instance = new JDbCUtil();
        }
        return instance;
    }

    public Connection getConnection(String dbName, String name, String password) {
        Connection res = null;
        String url = "jdbc:mysql://sh-cynosdbmysql-grp-f8ulooag.sql.tencentcdb.com:29622/"
                + dbName + "?autoReconnect=true&useSSL=false";
        try {
            res = DriverManager.getConnection(url, name, password);
            if(res == null) Log.e("JDbCUtil", "Fatal Error!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public Connection getConnection(String file) {
        File f = new File(file);
        if(!f.exists()){
            return null;
        } else {
            Properties pro = new Properties();
            try {
                pro.load(new FileInputStream(f));
                String url = pro.getProperty("url");
                String name = pro.getProperty("name");
                String password = pro.getProperty("password");
                return DriverManager.getConnection(url,name,password);
            } catch (Exception e) {
                return null;
            }
        }
    }
}
