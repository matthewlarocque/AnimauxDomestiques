package com.izaguirre.mascotas.user;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserOpt {

    private static UserOpt instance;
    private volatile UserBean usuario = new UserBean();
    private volatile boolean suc = false;

    public static UserOpt getInstance() {
        if (instance == null) {
            instance = new UserOpt();
        }
        return instance;
    }

    public UserBean getUser() {
        return usuario;
    }
    public void LogOut() {
        usuario = new UserBean();
    }

    public long DigestId(String name, String password) {
        try {
            MessageDigest hash = MessageDigest.getInstance("MD5");
            byte[] md5sum = hash.digest((name + new StringBuilder(password).reverse()).getBytes());
            long res = 0;
            for (int i = 0; i < md5sum.length; i++) {
                res += md5sum[i];
                if(res > 1e12) break;
                res *= 16;
            }
            return res;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean Register(String name, String password) {
        suc = false;
        Thread th = new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                Connection conn = JDbCUtil.getInstance().getConnection("mascotas", "root", "ZENGandroid1101");
                if (conn == null) {
                    Log.i("UserOpt", "register:conn is null");
                    suc = false;
                } else {
                    String sql = "INSERT INTO user (username, pwd, age, gender, userid, nickname) VALUES (?, ?, '0', '1', ?, ?)";
                    try {
                        PreparedStatement pre = conn.prepareStatement(sql);
                        pre.setString(1, name);
                        pre.setString(2, password);
                        pre.setLong(3, DigestId(name, password));
                        pre.setString(4, "肉球");
                        suc = (pre.executeUpdate() > 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                        suc = false;
                    } finally {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return suc;
    }

    public boolean Login(String name, String password) {
        suc = false;
        Thread th0 = new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                Connection conn = JDbCUtil.getInstance().getConnection("mascotas", "root", "ZENGandroid1101");
                if (conn == null) {
                    Log.i("UserOpt", "register:conn is null");
                    suc = false;
                } else {
                    String sql = "select * from user where username=? and pwd=?";
                    try {
                        PreparedStatement pres = conn.prepareStatement(sql);
                        pres.setString(1, name);
                        pres.setString(2, password);
                        ResultSet res = pres.executeQuery();
                        suc = res.next();
                    } catch (Exception e) {
                        e.printStackTrace();
                        suc = false;
                    } finally {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        th0.start();
        try {
            th0.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return suc;
    }

    public boolean UpdateAge(int ano) {
        suc = false;
        Thread th = new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                Connection conn = JDbCUtil.getInstance().getConnection("mascotas", "root", "ZENGandroid1101");
                if (conn == null) {
                    Log.i("UserOpt", "register:conn is null");
                    suc = false;
                } else {
                    String sql = "update user set age=? where username=? and pwd=? and age=? and gender=? and userid=? and nickname=?";
                    try {
                        PreparedStatement pst = conn.prepareStatement(sql);
                        pst.setInt(1, ano);
                        pst.setString(2, usuario.getUsername());
                        pst.setString(3, usuario.getPwd());
                        pst.setInt(4, usuario.getAge());
                        pst.setBoolean(5, usuario.getGender());
                        pst.setLong(6, usuario.getUserid());
                        pst.setString(7, getUser().getNickname());
                        suc = (pst.executeUpdate() > 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                        suc = false;
                    } finally {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        th.start();
        try {
            th.join();
        } catch (Exception e) {
            e.printStackTrace();
            suc = false;
        }
        return suc;
    }

    public boolean UpdateGender(boolean gen) {
        suc = false;
        Thread th = new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                Connection conn = JDbCUtil.getInstance().getConnection("mascotas", "root", "ZENGandroid1101");
                if (conn == null) {
                    Log.i("UserOpt", "register:conn is null");
                    suc = false;
                } else {
                    String sql = "update user set gender=? where username=? and pwd=? and age=? and gender=? and userid=? and nickname=?";
                    try {
                        PreparedStatement pst = conn.prepareStatement(sql);
                        pst.setBoolean(1, gen);
                        pst.setString(2, usuario.getUsername());
                        pst.setString(3, usuario.getPwd());
                        pst.setInt(4, usuario.getAge());
                        pst.setBoolean(5, usuario.getGender());
                        pst.setLong(6, usuario.getUserid());
                        pst.setString(7, getUser().getNickname());
                        suc = (pst.executeUpdate() > 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                        suc = false;
                    } finally {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        th.start();
        try {
            th.join();
        } catch (Exception e) {
            e.printStackTrace();
            suc = false;
        }
        return suc;
    }

    public boolean UpdateNick(String nk) {
        suc = false;
        Thread th = new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                Connection conn = JDbCUtil.getInstance().getConnection("mascotas", "root", "ZENGandroid1101");
                if (conn == null) {
                    Log.i("UserOpt", "register:conn is null");
                    suc = false;
                } else {
                    String sql = "update user set nickname=? where username=? and pwd=? and age=? and gender=? and userid=? and nickname=?";
                    try {
                        PreparedStatement pst = conn.prepareStatement(sql);
                        pst.setString(1, nk);
                        pst.setString(2, usuario.getUsername());
                        pst.setString(3, usuario.getPwd());
                        pst.setInt(4, usuario.getAge());
                        pst.setBoolean(5, usuario.getGender());
                        pst.setLong(6, usuario.getUserid());
                        pst.setString(7, getUser().getNickname());
                        suc = (pst.executeUpdate() > 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                        suc = false;
                    } finally {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        th.start();
        try {
            th.join();
        } catch (Exception e) {
            e.printStackTrace();
            suc = false;
        }
        return suc;
    }

    public boolean UpdateAvatar(Bitmap bmp) {
        suc = false;
        ByteArrayOutputStream barr = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 90, barr);
        Thread th = new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                Connection conn = JDbCUtil.getInstance().getConnection("mascotas", "root", "ZENGandroid1101");
                if (conn == null) {
                    Log.i("UserOpt", "register:conn is null");
                    suc = false;
                } else {
                    String sql = "update user set avatar=? where username=? and pwd=? and age=? and gender=? and userid=? and nickname=?";
                    try {
                        PreparedStatement pst = conn.prepareStatement(sql);
                        byte[] b0 = barr.toByteArray();
                        InputStream binput = new ByteArrayInputStream(b0);
                        pst.setBlob(1, binput);
                        pst.setString(2, usuario.getUsername());
                        pst.setString(3, usuario.getPwd());
                        pst.setInt(4, usuario.getAge());
                        pst.setBoolean(5, usuario.getGender());
                        pst.setLong(6, usuario.getUserid());
                        pst.setString(7, getUser().getNickname());
                        suc = (pst.executeUpdate() > 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                        suc = false;
                    } finally {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return suc;
    }

    public boolean findUser(String name) {
        suc = false;
        Thread th1 = new Thread(new Runnable() {
            public synchronized void run() {
                Connection conn = JDbCUtil.getInstance().getConnection("mascotas", "root", "ZENGandroid1101");
                if (conn == null) {
                    Log.i("UserOpt", "register:conn is null");
                    suc = false;
                } else {
                    String sql = "select * from user where username = ?";
                    try {
                        PreparedStatement pst = conn.prepareStatement(sql);
                        pst.setString(1, name);
                        ResultSet rs = pst.executeQuery();
                        if (rs.next()){
                            suc = true;
                            String pwd = rs.getString(2);
                            int age = rs.getInt(3);
                            boolean gender = rs.getBoolean(4);
                            long userid = rs.getLong(5);
                            String nick = rs.getString(6);
                            Blob blb = rs.getBlob(7);
                            usuario.setUsername(name);
                            usuario.setPwd(pwd);
                            usuario.setAge(age);
                            usuario.setGender(gender);
                            usuario.setUserid(userid);
                            usuario.setNickname(nick);
                            usuario.setAvatar(blb);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        suc = false;
                    } finally {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        th1.start();
        try {
            th1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return suc;
    }
}
