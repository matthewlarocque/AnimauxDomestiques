package com.izaguirre.mascotas;

import androidx.multidex.MultiDexApplication;

public class MiApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
