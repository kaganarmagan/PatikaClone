package com.patikaclone.Model;

import com.patikaclone.Helper.DBConnector;
import com.patikaclone.Helper.Helper;

import java.sql.*;
import java.util.ArrayList;

public class Patika {
    private int id;
    private String name;

    public Patika(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ArrayList<Patika> getList() {
        ArrayList<Patika> patikaArrayList=new ArrayList<>();
        Statement st=null;
        Patika obj;
        Connection conn=null;
        ResultSet rs=null;
        try {
            conn= DBConnector.getInstance();
            st = conn.createStatement();
            rs = st.executeQuery( "SELECT * FROM patika ORDER BY id;" );
            while ( rs.next() ) {
                obj =new Patika(rs.getInt("id"),rs.getString("name"));
                patikaArrayList.add(obj);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (st != null) {
                    st.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return patikaArrayList;
    }
    public static boolean insertData(String name) {
        String sql = "INSERT INTO patika(name) VALUES(?)";
        boolean key = false;
        Connection conn = null;
        PreparedStatement prst = null;
        try {
            conn = DBConnector.getInstance();
            prst = conn.prepareStatement(sql);
            prst.setString(1, name);
            key = prst.executeUpdate() != -1;

            prst.close();
            conn.close();
            return key;
        } catch (SQLException throwables) {
            throwables.printStackTrace();

            return key;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (prst != null) {
                    prst.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static boolean deleteData(int id) {
        String sql="DELETE FROM patika WHERE id=?;";
        boolean key=false;
        Connection conn=null;
        PreparedStatement prst=null;

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
    public static  boolean updateData(int id,String name){
        String sql="UPDATE patika SET name = ? WHERE id=? ;";
        boolean key=false;
        Connection conn=null;
        PreparedStatement prst=null;

        if(Helper.confirm("sure")) {
            try {
                conn=DBConnector.getInstance();
                prst = conn.prepareStatement(sql);
                prst.setString(1, name);
                prst.setInt(2, id);
                key = prst.executeUpdate() != -1;
                prst.close();
                conn.close();
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
    public static Patika getFetch(int id){
        Patika obj =null;
        String query="Select * from patika WHERE id=? ";
        Connection conn=null;
        PreparedStatement prst=null;
        ResultSet rs=null;
        try {
            conn=DBConnector.getInstance();
            prst=DBConnector.getInstance().prepareStatement(query);
            prst.setInt(1,id);
            rs= prst.executeQuery();
            if(rs.next()){
                String name=rs.getString("name");
                int newID=rs.getInt("id");
                obj=new Patika(newID,name);
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
        return obj;
    }
    public static Patika getFetch(String name){
        Patika obj =null;
        String query="Select * from patika WHERE name=? ";
        Connection conn=null;
        PreparedStatement prst=null;
        ResultSet rs=null;
        try {
            conn=DBConnector.getInstance();
            prst=conn.prepareStatement(query);
            prst.setString(1,name);
            rs= prst.executeQuery();
            if(rs.next()){
                obj=new Patika(rs.getInt("id"),rs.getString("name"));
            }
            rs.close();
            prst.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (prst != null) {
                    prst.close();
                }
                if(rs!=null){rs.close();}
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }
    public static void deleteRelatedCourse(int patika_id){
        Connection conn=null;
        PreparedStatement prst=null;
        ResultSet rs=null;
        try {
            conn=DBConnector.getInstance();
            prst = conn.prepareStatement("delete from course where patika_id = ? ;");
            prst.setInt(1, patika_id);
            prst.executeUpdate();

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
}
