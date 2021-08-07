package com.patikaclone.Model;

import com.patikaclone.Helper.DBConnector;
import com.patikaclone.Helper.Helper;

import java.sql.*;
import java.util.ArrayList;

public class Course {
    private int id;
    private int user_id;
    private int patika_id;
    private String name;
    private String lang;
    private Patika patika;
    private Users educator;


    public Course(int id, int user_id, int patika_id, String name, String lang) {
        this.id = id;
        this.user_id = user_id;
        this.patika_id = patika_id;
        this.name = name;
        this.lang = lang;
        this.patika=Patika.getFetch(this.patika_id);
        this.educator=Educator.getFetch(this.user_id);
    }

    public static ArrayList<Course> getList() {
        ArrayList<Course> courseArrayList=new ArrayList<>();
        Statement st=null;
        Connection conn= null;
        ResultSet rs=null;
        Course obj;
        try {
            conn= DBConnector.getInstance();
            st = conn.createStatement();
            rs = st.executeQuery( "SELECT * FROM course ORDER BY id;" );
            while ( rs.next() ) {
                obj =new Course(rs.getInt("id"),rs.getInt("user_id"),rs.getInt("patika_id"),rs.getString("name"),rs.getString("lang"));
                courseArrayList.add(obj);
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

        return courseArrayList;
    }
    public static boolean insertData(int user_id,int patika_id,String name,String lang){
        boolean key =false;
        String sql="INSERT INTO course(user_id,patika_id,name,lang) VALUES (?,?,?,?)";
        Connection conn=null;
        PreparedStatement prst=null;
        if(Helper.confirm("sure")) {
            try {
                conn=DBConnector.getInstance();
                prst =conn.prepareStatement(sql);
                prst.setInt(1,user_id);
                prst.setInt(2, patika_id);
                prst.setString(3, name);
                prst.setString(4, lang);
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
        return key;
    }

    public static Course getFetch(int course_id) {
        Course course=null;
        Connection conn=null;
        PreparedStatement prst=null;
        ResultSet rs=null;
        try {
            conn=DBConnector.getInstance();
            prst =conn.prepareStatement("SELECT * FROM COURSE WHERE id=?;");
            prst.setInt(1,course_id);
            rs=prst.executeQuery();
            if(rs.next()){
                course=new Course(rs.getInt("id"),rs.getInt("user_id"),rs.getInt("patika_id"),rs.getString("name"),rs.getString("lang"));
            }
            conn.close();
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

        return course;
    }
    public static Course getFetch(String title) {
        Course course=null;
        Connection conn=null;
        PreparedStatement prst=null;
        ResultSet rs=null;
        try {
            conn=DBConnector.getInstance();
            prst =conn.prepareStatement("SELECT * FROM COURSE WHERE name ilike ?;");
            prst.setString(1,title);
            rs=prst.executeQuery();
            if(rs.next()){
                course=new Course(rs.getInt("id"),rs.getInt("user_id"),rs.getInt("patika_id"),rs.getString("name"),rs.getString("lang"));
            }
            conn.close();
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

        return course;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPatika_id() {
        return patika_id;
    }

    public void setPatika_id(int patika_id) {
        this.patika_id = patika_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Patika getPatika() {
        return patika;
    }

    public void setPatika(Patika patika) {
        this.patika = patika;
    }

    public Users getEducator() {
        return educator;
    }

    public void setEducator(Users educator) {
        this.educator = educator;
    }



}

