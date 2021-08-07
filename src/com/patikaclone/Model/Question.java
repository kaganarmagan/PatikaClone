package com.patikaclone.Model;

import com.patikaclone.Helper.DBConnector;

import java.sql.*;
import java.util.ArrayList;

public class Question {
    private int id;
    private String quest;
    private String o1;
    private String o2;
    private String o3;
    private String o4;
    private int answer;
    private int content_id;
    private Content content;

    public Question(int id, String quest, String o1, String o2, String o3, String o4, int answer, int content_id) {
        this.id = id;
        this.quest = quest;
        this.o1 = o1;
        this.o2 = o2;
        this.o3 = o3;
        this.o4 = o4;
        this.answer = answer;
        this.content_id = content_id;
        this.content=Content.getFetch(content_id);
    }

    public static boolean insertData(String q, String o1, String o2, String o3, String o4,int answer, int course_id) {
        boolean key=false;
        Connection conn=null;
        PreparedStatement prst=null;
        try {
            conn=DBConnector.getInstance();
            prst =conn.prepareStatement("INSERT INTO question(question,o1,o2,o3,o4,answer,content_id) VALUES (?,?,?,?,?,?,?)");
            prst.setString(1,q);
            prst.setString(2,o1);
            prst.setString(3,o2);
            prst.setString(4,o3);
            prst.setString(5,o4);
            prst.setInt(6,answer);
            prst.setInt(7,course_id);
            key=prst.executeUpdate()!=-1;
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

    public static boolean UpdateData(int id, String question, String o1, String o2, String o3, String o4, int answer) {
        boolean key=false;
        Connection conn=null;
        PreparedStatement prst=null;

        try {
            conn=DBConnector.getInstance();
            prst =conn.prepareStatement("UPDATE question SET question=?,o1=?,o2=?,o3=?,o4=?,answer=? WHERE id=?;");
            prst.setString(1,question);
            prst.setString(2,o1);
            prst.setString(3,o2);
            prst.setString(4,o3);
            prst.setString(5,o4);
            prst.setInt(6,answer);
            prst.setInt(7,id);

            key=prst.executeUpdate()!=-1;
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

    public static ArrayList<Question> getList(int content_id) {
        ArrayList<Question> questionArrayList=new ArrayList<>();
        Question obj;
        Connection conn=null;
        PreparedStatement prst=null;
        ResultSet rs=null;
        try {
            conn=DBConnector.getInstance();
            prst=conn.prepareStatement("SELECT * FROM question where content_id=? ORDER BY id;");
            prst.setInt(1,content_id);
            rs = prst.executeQuery();
            while ( rs.next() ) {
                obj =new Question(rs.getInt("id"),rs.getString("question"),rs.getString("o1"),rs.getString("o2"),rs.getString("o3"),rs.getString("o4"),rs.getInt("answer"),rs.getInt("content_id"));
                questionArrayList.add(obj);
            }
            rs.close();
            prst.close();
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

        return questionArrayList;

    }

    public static Question getFetch(int id) {
        Question obj =null;
        String query="Select * from question WHERE id=? ";
        Connection conn=null;
        PreparedStatement prst=null;
        ResultSet rs=null;
        try {
            conn=DBConnector.getInstance();
            prst=conn.prepareStatement(query);
            prst.setInt(1,id);
            rs= prst.executeQuery();
            if(rs.next()){
                String question=rs.getString("question");
                int newID=rs.getInt("id");
                String o1=rs.getString("o1");
                String o2=rs.getString("o2");
                String o3=rs.getString("o3");
                String o4=rs.getString("o4");
                int answer=rs.getInt("answer");
                int content_id=rs.getInt("content_id");

                obj=new Question(newID,question,o1,o2,o3,o4,answer,content_id);

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

    public static boolean deleteData(int id) {
        boolean key=false;
        Connection conn=null;
        PreparedStatement prst=null;

        try {
            conn=DBConnector.getInstance();
            prst =conn.prepareStatement("DELETE FROM question  WHERE id=?;");
            prst.setInt(1,id);

            key=prst.executeUpdate()!=-1;
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

    public static Question getFetch(String q) {
        Question obj =null;
        String query="Select * from question WHERE question=? ";
        Connection conn=null;
        PreparedStatement prst=null;
        ResultSet rs=null;
        try {
            conn=DBConnector.getInstance();
            prst=conn.prepareStatement(query);
            prst.setString(1,q);
            rs= prst.executeQuery();
            if(rs.next()){
                String question=rs.getString("question");
                int newID=rs.getInt("id");
                String o1=rs.getString("o1");
                String o2=rs.getString("o2");
                String o3=rs.getString("o3");
                String o4=rs.getString("o4");
                int answer=rs.getInt("answer");
                int content_id=rs.getInt("content_id");

                obj=new Question(newID,question,o1,o2,o3,o4,answer,content_id);
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

    public static ArrayList<Question> getEducatorList(ArrayList<Content> contents,int educator_id) {
        ArrayList<Question> questionArrayList=new ArrayList<>();
        Question obj;
        Connection conn=null;
        PreparedStatement prst=null;
        ResultSet rs=null;
        for (Content c:contents) {

            if (c.getEducator().getId() == educator_id) {
                int content_id=c.getId();
                try {
                    conn = DBConnector.getInstance();
                    prst = conn.prepareStatement("SELECT * FROM question where content_id=? ORDER BY id;");
                    prst.setInt(1, content_id);
                    rs = prst.executeQuery();
                    while (rs.next()) {
                        obj = new Question(rs.getInt("id"), rs.getString("question"), rs.getString("o1"), rs.getString("o2"), rs.getString("o3"), rs.getString("o4"), rs.getInt("answer"), rs.getInt("content_id"));
                        questionArrayList.add(obj);
                    }
                    rs.close();
                    prst.close();
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

            }
        }
        return questionArrayList;
    }
    public static ArrayList<Question> getEducatorList(Content content,int educator_id) {
        ArrayList<Question> questionArrayList=new ArrayList<>();
        Question obj;
        Connection conn=null;
        PreparedStatement prst=null;
        ResultSet rs=null;
        int content_id=content.getId();
        try {
            conn = DBConnector.getInstance();
            prst = conn.prepareStatement("SELECT * FROM question where content_id=? ORDER BY id;");
            prst.setInt(1, content_id);
            rs = prst.executeQuery();
            while (rs.next()) {
                obj = new Question(rs.getInt("id"), rs.getString("question"), rs.getString("o1"), rs.getString("o2"), rs.getString("o3"), rs.getString("o4"), rs.getInt("answer"), rs.getInt("content_id"));
                questionArrayList.add(obj);
            }
            rs.close();
            prst.close();
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

        return questionArrayList;
    }

    public static void deleteContentData(int id) {
        Connection conn=null;
        PreparedStatement prst=null;

        try {
            conn=DBConnector.getInstance();
            prst =conn.prepareStatement("DELETE FROM question  WHERE content_id=?;");
            prst.setInt(1,id);
            prst.executeUpdate();
            prst.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();

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

    public String getQuest() {
        return quest;
    }

    public void setQuest(String quest) {
        this.quest = quest;
    }

    public String getO1() {
        return o1;
    }

    public void setO1(String o1) {
        this.o1 = o1;
    }

    public String getO2() {
        return o2;
    }

    public void setO2(String o2) {
        this.o2 = o2;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

    public String getO4() {
        return o4;
    }

    public void setO4(String o4) {
        this.o4 = o4;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
