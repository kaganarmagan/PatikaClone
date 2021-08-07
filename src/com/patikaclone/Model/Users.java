package com.patikaclone.Model;

import com.patikaclone.Helper.DBConnector;
import com.patikaclone.Helper.Helper;


import java.sql.*;
import java.util.ArrayList;

public class Users {
    private int id;
    private String name;
    private String uname;
    private String psw;
    private String types;



    public Users(int id, String name, String uname, String psw, String types) {
        this.id = id;
        this.name = name;
        this.uname = uname;
        this.psw = psw;
        this.types = types;
    }

    public static Users getFetch(int id) {
        Users obj =null;
        String query="Select * from users WHERE id=? ";
        Connection conn=null;
        PreparedStatement prst=null;
        ResultSet rs=null;
        try {
            conn=DBConnector.getInstance();
            prst=conn.prepareStatement(query);
            prst.setInt(1,id);
            rs= prst.executeQuery();
            if(rs.next()){
                String name=rs.getString("name");
                int newID=rs.getInt("id");
                String uname=rs.getString("uname");
                String psw=rs.getString("psw");
                String type=rs.getString("types");


                obj=new Users(newID,name,uname,psw,type);
            }
            prst.close();
            rs.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            try {
                if(conn!=null){conn.close();}
                if(prst!=null){prst.close();}
                if(rs!=null){rs.close();}
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }
    public static Users getUserNameFetch(String uname) {
        Users obj =null;
        String query="Select * from users WHERE uname=? ";
        Connection conn=null;
        PreparedStatement prst=null;
        ResultSet rs=null;
        try {
            conn=DBConnector.getInstance();
            prst=conn.prepareStatement(query);
            prst.setString(1,uname);
            rs= prst.executeQuery();
            if(rs.next()){

                obj=new Users(rs.getInt("id"),rs.getString("name"),rs.getString("uname"),rs.getString("psw"),rs.getString("types"));
            }
            prst.close();
            rs.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            try {
                if(conn!=null){conn.close();}
                if(prst!=null){prst.close();}
                if(rs!=null){rs.close();}
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }
    public static Users getFetch(String name) {
        Users obj =null;
        String query="Select * from users WHERE name=? ";
        Connection conn=null;
        PreparedStatement prst=null;
        ResultSet rs=null;
        try {
            conn=DBConnector.getInstance();
            prst=conn.prepareStatement(query);
            prst.setString(1,name);
            rs= prst.executeQuery();
            if(rs.next()){

                obj=new Users(rs.getInt("id"),rs.getString("name"),rs.getString("uname"),rs.getString("psw"),rs.getString("types"));
            }
            prst.close();
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            try {
                if(conn!=null){conn.close();}
                if(prst!=null){prst.close();}
                if(rs!=null){rs.close();}
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }
    public static Users login(String uname, String psw) {
        Users user=null;
        Connection conn=null;
        PreparedStatement prst=null;
        ResultSet rs=null;
        try {
            conn=DBConnector.getInstance();
            prst=conn.prepareStatement("Select * from users where uname =? and psw=?;");
            prst.setString(1,uname);
            prst.setString(2,psw);
            rs= prst.executeQuery();
            if(rs.next()){
                user= new Users(rs.getInt("id"),rs.getString("name"),rs.getString("uname"),rs.getString("psw"),rs.getString("types"));
            }
            prst.close();
            rs.close();
            conn.close();
            return user;
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            return user;
        }finally{
            try {
                if(conn!=null){conn.close();}
                if(prst!=null){prst.close();}
                if(rs!=null){rs.close();}
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static ArrayList<Users> getList(){
        ArrayList<Users> usersArrayList=new ArrayList<>();
        Statement st=null;
        Connection conn= null;
        ResultSet rs =null;
        Users obj;
        try {
            conn= DBConnector.getInstance();
            st = conn.createStatement();
            rs = st.executeQuery( "SELECT * FROM USERS ORDER BY id;" );
            while ( rs.next() ) {
                obj =new Users(rs.getInt("id"),rs.getString("name"),rs.getString("uname"),rs.getString("psw"),rs.getString("types"));
                usersArrayList.add(obj);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            try {
                if(conn!=null){conn.close();}
                if(st!=null){st.close();}
                if(rs!=null){rs.close();}
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return usersArrayList;
    }
    public static ArrayList<Users> getEducatorList(){
        ArrayList<Users> usersArrayList=new ArrayList<>();
        Statement st=null;
        ResultSet rs =null;
        Connection conn=null;
        Users obj;
        try {
            conn= DBConnector.getInstance();
            st = conn.createStatement();
            rs = st.executeQuery( "SELECT * FROM USERS WHERE types ='educator' ORDER BY id;" );
            while ( rs.next() ) {
                obj =new Users(rs.getInt("id"),rs.getString("name"),rs.getString("uname"),rs.getString("psw"),rs.getString("types"));
                usersArrayList.add(obj);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            try {
                if(conn!=null){conn.close();}
                if(st!=null){st.close();}
                if(rs!=null){rs.close();}
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return usersArrayList;
    }
    public static boolean insertData(String name,String uname,String psw,String type){
        Connection conn=null;
        PreparedStatement prst=null;
        String sql="INSERT INTO users(name,uname,psw,types) VALUES (?,?,?,?::types);";
        boolean key=true;
        try {
            conn=DBConnector.getInstance();
            prst=conn.prepareStatement(sql);
            prst.setString(1, name);
            prst.setString(2, uname);
            prst.setString(3, psw);
            prst.setString(4, type);
            key =prst.executeUpdate()!=-1;

            prst.close();
            conn.close();
            return key;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return !key;

        }finally{
            try {
                if(conn!=null){conn.close();}
                if(prst!=null){prst.close();}
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    public static boolean deleteData(int id){
        Connection conn=null;
        String sql="DELETE FROM USERS WHERE ID = ?;";
        PreparedStatement prst=null;
        boolean key=false;
        if(Helper.confirm("sure")) {
            try {
                conn=DBConnector.getInstance();
                prst = conn.prepareStatement(sql);
                prst.setInt(1, id);

                key = prst.executeUpdate() != -1;
                prst.close();
                conn.close();
                return key;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return false;
            }finally{
                try {
                    if(conn!=null){conn.close();}
                    if(prst!=null){prst.close();}
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    public static boolean updateData(int id,String name,String uname,String psw,String type){
        boolean key=false;
        String sql="UPDATE users SET name = ?, uname = ?, psw = ?, types = ?::types where id=?;";
        PreparedStatement prst=null;
        Connection conn=null;
        if(Helper.confirm("sure")) {
            try {
                conn=DBConnector.getInstance();
                prst =conn.prepareStatement(sql);
                prst.setString(1, name);
                prst.setString(2, uname);
                prst.setString(3, psw);
                prst.setString(4, type);
                prst.setInt(5, id);
                key = prst.executeUpdate() != -1;
                prst.close();
                return key;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return key;
            }finally{
                try {
                    if(conn!=null){conn.close();}
                    if(prst!=null){prst.close();}
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return false;
    }
    public static void deleteRelatedCourse(int user_id){
        PreparedStatement prst=null;
        Connection conn=null;
        if(getFetch(user_id).getTypes().equals("educator")) {
            try {
                conn=DBConnector.getInstance();
                prst =conn.prepareStatement("delete from course where user_id = ? ;");
                prst.setInt(1, user_id);
                prst.executeUpdate();
                prst.close();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }finally{
                try {
                    if(conn!=null){conn.close();}
                    if(prst!=null){prst.close();}
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static ArrayList<Users> searchUserList(String query){
        ArrayList<Users> usersArrayList=new ArrayList<>();
        Statement st=null;
        Connection conn=null;
        ResultSet rs=null;
        Users obj;
        try {
            conn=DBConnector.getInstance();
            st = conn.createStatement();
            rs = st.executeQuery( query );
            while ( rs.next() ) {
                obj =new Users(rs.getInt("id"),rs.getString("name"),rs.getString("uname"),rs.getString("psw"),rs.getString("types"));
                usersArrayList.add(obj);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            try {
                if(conn!=null){conn.close();}
                if(st!=null){st.close();}
                if(rs!=null){rs.close();}
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return usersArrayList;
    }
    public static String searchQuery(String name, String uname){
        String query="SELECT * FROM users WHERE uname ILIKE '%{{uname}}%' AND name ILIKE '%{{name}}%' ORDER BY id;";
        query=query.replace("{{uname}}",uname);
        query=query.replace("{{name}}",name);

        return query;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }


}
