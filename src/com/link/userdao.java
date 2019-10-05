package com.link;


import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class userdao {
    private static  String DRIVER = "";
    private static  String USER = "";
    private static  String NAME = "";
    private static  String PASSWORD = "";
    private static Connection conn = null;
    private static PreparedStatement sql = null;


    public static void main(String[] args) {
//        user u=new user(3,"小强","789");
//        update(u);
//        user u = new user("小东", "147");
//        add(u);
//        del(6);
        select(1);
    }
    //配置内容
    static {
        Properties p=new Properties();
        try {
            p.load(userdao.class.getResourceAsStream("/配置.properties"));
            DRIVER=p.getProperty("DRIVER");
            USER = p.getProperty("USER");
            NAME=p.getProperty("NAME");
            PASSWORD=p.getProperty("PASSWORD");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //连接数据库
    public static void link() {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(USER, NAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.getErrorCode();
        }
    }

    //添加
    public static void add(user user) {
        link();
        String add = "insert into test(name,password) values(?,?)";
        try {
            sql = conn.prepareCall(add);
            sql.setObject(1, user.getName());
            sql.setObject(2, user.getPassword());
            sql.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                sql.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //删除
    public static void del(Integer a) {
        link();
        String del = "delete from test where id=?";

        try {
            sql = conn.prepareCall(del);
            sql.setObject(1, a);
            sql.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                sql.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //修改
    public static void update(user user) {
        link();
        String update = "update test set name=?,password=? where id=?";
        try {
            sql = conn.prepareCall(update);
            sql.setObject(1, user.getName());
            sql.setObject(2, user.getPassword());
            sql.setObject(3, user.getId());
            sql.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                sql.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //查询
    public static void select(Integer id) {
        link();
        String select = "select * from test where id = ?";
        try {
            sql = conn.prepareCall(select);
            sql.setObject(1, id);
            ResultSet rs = sql.executeQuery();
            while (rs.next()) {
                Integer s = rs.getInt("id");
                String s2 = rs.getString("name");
                Integer s3 = rs.getInt("password");

                System.out.println(s+" "+s2+" "+s3);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
