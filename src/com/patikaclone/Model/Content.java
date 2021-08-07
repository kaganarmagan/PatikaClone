package com.patikaclone.Model;

import com.patikaclone.Helper.DBConnector;

import java.sql.*;
import java.util.ArrayList;

public class Content {
   private int id;
   private String title;
   private String info;
   private String url;

   private int course_id;
   private Course course;
   private Users educator;

    public Content(int id, String title, String info, String url,  int course_id) {
        this.id = id;
        this.title = title;
        this.info = info;
        this.url = url;
        this.course_id = course_id;
        this.course=Course.getFetch(course_id);
        this.educator=course.getEducator();
    }

    public static Content getFetch(int content_id) {
        Content content=null;
        Connection conn=null;
        PreparedStatement prst=null;
        ResultSet rs=null;
        try {
            conn=DBConnector.getInstance();
            prst =conn.prepareStatement("SELECT * FROM content WHERE id=?;");
            prst.setInt(1,content_id);
            rs=prst.executeQuery();
            if(rs.next()){

                content=new Content(rs.getInt("id"), rs.getString("title"), rs.getString("info"),rs.getString("url"),rs.getInt("course_id"));
            }
            prst.close();
            rs.close();
        } catch (Exception throwables) {
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

        return content;
    }

    public static Content getFetch(String text) {
        Content content=null;
        Connection conn=null;
        PreparedStatement prst=null;
        ResultSet rs=null;
        try {
            conn=DBConnector.getInstance();
            prst =conn.prepareStatement("SELECT * FROM content WHERE title =?;");
            prst.setString(1,text);
            rs=prst.executeQuery();
            if(rs.next()){

                content=new Content(rs.getInt("id"), rs.getString("title"), rs.getString("info"),rs.getString("url"),rs.getInt("course_id"));
            }
            prst.close();
            rs.close();
        } catch (Exception throwables) {
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

        return content;
    }

    public static boolean insertData(String title, String info, String url,int course_id) {
        boolean key=false;
        Connection conn=null;
        PreparedStatement prst=null;
        try {
            conn=DBConnector.getInstance();
            prst=conn.prepareStatement("Insert into content(title,info,url,course_id) Values (?,?,?,?)");
            prst.setString(1,title);
            prst.setString(2,info);
            prst.setString(3,url);
            prst.setInt(4,course_id);
            key= prst.executeUpdate()!=-1;
            conn.close();
            prst.close();
            return key;
        } catch (SQLException e) {
            e.printStackTrace();
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

    public static boolean updateData(int content_id, String title, String info, String url) {
        boolean key=false;
        Connection conn=null;
        PreparedStatement prst=null;
        try {
            conn=DBConnector.getInstance();
            prst=conn.prepareStatement("update content set title=?,info=?,url=? where id=? ;");
            prst.setString(1,title);
            prst.setString(2,info);
            prst.setString(3,url);
            prst.setInt(4,content_id);
            key= prst.executeUpdate()!=-1;
            prst.close();
            conn.close();
            return key;
        } catch (SQLException e) {
            e.printStackTrace();
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

    public static ArrayList<Content> getList() {
        ArrayList<Content> contentArrayList=new ArrayList<>();
        Statement st=null;
        Connection conn=null;
        Content obj;
        ResultSet rs=null;
        try {
            conn= DBConnector.getInstance();
            st = conn.createStatement();
            rs = st.executeQuery( "SELECT * FROM content ORDER BY id;" );
            while ( rs.next() ) {

                obj =new Content(rs.getInt("id"), rs.getString("title"),rs.getString("info"),rs.getString("url") ,rs.getInt("course_id"));
                contentArrayList.add(obj);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (Exception throwables) {
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

        return contentArrayList;
    }


    public static boolean deleteData(int id) {
        boolean key=false;
        Connection conn=null;
        PreparedStatement prst=null;
        try {
            conn=DBConnector.getInstance();
            prst=conn.prepareStatement("Delete from content where id=? ;");
            prst.setInt(1,id);

            key= prst.executeUpdate()!=-1;
            conn.close();
            prst.close();
            return key;
        } catch (SQLException e) {
            e.printStackTrace();
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

    public static ArrayList<Content> getSearchedList(Integer course_id, String content) {
        String query;
        if(course_id==-1){
            query="SElECT*FROM content WHERE title ilike '%{{string}}%' Order by id; ";
            query=query.replace("{{string}}",content);
        }else{
            query="SElECT*FROM content WHERE title ilike '%{{string}}%' and course_id={{course_id}} Order by id; ";
            query=query.replace("{{string}}",content).replace("{{course_id}}",course_id.toString());
        }
        ArrayList<Content> contentArrayList=new ArrayList<>();
        Content obj;
        Connection conn=null;
        Statement st=null;
        ResultSet rs=null;
        try {
            conn=DBConnector.getInstance();
            st=conn.createStatement();
            rs = st.executeQuery(query);
            while ( rs.next() ) {
                obj =new Content(rs.getInt("id"), rs.getString("title"),rs.getString("info"),rs.getString("url") ,rs.getInt("course_id"));
                contentArrayList.add(obj);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (Exception throwables) {
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

        return contentArrayList;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Users getEducator() {
        return educator;
    }

    public void setEducator(Users educator) {
        this.educator = educator;
    }

}
