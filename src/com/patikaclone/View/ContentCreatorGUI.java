package com.patikaclone.View;

import com.patikaclone.Helper.Config;
import com.patikaclone.Helper.Helper;
import com.patikaclone.Model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class ContentCreatorGUI extends JFrame{
    private JPanel wrapper;
    private JTextField fld_content_title;
    private JTextArea txt_content_info;
    private JTextField fld_content_url;
    private JPanel wtop;
    private JPanel wmid;
    private JButton btn_content_create_delete;
    private JButton btn_question_insert;
    private JButton btn_question_update_delete;
    private JButton btn_content_update;
    private JPanel wbottom;
    private JTable tbl_question_list;
    private JTextField fld_question_id;
    private JButton btn_backward;
    private JLabel lbl_course_name;
    private Content content;
    private Course course;
    private DefaultTableModel mdl_question_list;
    private Object [] row_question_list;

    public ContentCreatorGUI(Course course)  {
        this.course=course;
        add(wrapper);
        setSize(1024,800);
        setLocation(Helper.screenCenter("x",getSize()),Helper.screenCenter("y",getSize()));
        setTitle(Config.PROJECT_TITLE);
        try {
            setIconImage(ImageIO.read(new File("src/com/patikaclone/Image/icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        btn_content_update.setVisible(false);

        mdl_question_list=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column==0){
                    return false;
                }
                return super.isCellEditable(row, column);
            }

        };
        Object[] col_question_list={"ID","Soru","Cevap 1","Cevap 2","Cevap 3","Cevap 4","Doğru Cevap","İçerik ID"};
        mdl_question_list.setColumnIdentifiers(col_question_list);
        this.row_question_list=new Object[col_question_list.length];
        tbl_question_list.setModel(mdl_question_list);
        tbl_question_list.getTableHeader().setReorderingAllowed(false);
        tbl_question_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_user_id = tbl_question_list.getValueAt(tbl_question_list.getSelectedRow(), 0).toString();
                fld_question_id.setText(select_user_id);
            }catch(Exception exception){}

        });

        lbl_course_name.setText("Ders :"+this.course.getName());

        btn_content_create_delete.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_content_title)){
                Helper.showMsg("fill");
            }else{
                String title=fld_content_title.getText();
                String url=fld_content_url.getText();
                String info=txt_content_info.getText();
                if(Content.insertData(title,info,url,course.getId())){
                    Helper.showMsg("done");
                    btn_question_insert.setEnabled(true);
                    btn_question_update_delete.setEnabled(true);
                    btn_content_create_delete.setEnabled(false);
                    btn_content_update.setVisible(true);
                    btn_content_create_delete.setText("Oluşturuldu");
                    fld_content_url.setEnabled(false);
                    fld_content_title.setEnabled(false);
                    txt_content_info.setEnabled(false);
                    this.content=Content.getFetch(title);
                    getQuestionList();
                    btn_content_update.addActionListener(k -> {
                        ContentCreatorGUI ccgui=new ContentCreatorGUI(this.content,this.course);
                        dispose();
                    });
                }else{
                    Helper.showMsg("error");
                }
            }
        });
        btn_question_insert.addActionListener(e -> {
            QuestionGUI qgui =new QuestionGUI(Content.getFetch(fld_content_title.getText()).getId());
            qgui.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    getQuestionList();

                }
            });
        });


        btn_question_update_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuestionGUI qgui=new QuestionGUI(Question.getFetch(Integer.parseInt(fld_question_id.getText())));
                qgui.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        getQuestionList();

                    }
                });
            }
        });
        btn_backward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    public ContentCreatorGUI(Content content,Course course){
        this.content=content;
        this.course=course;
        add(wrapper);
        lbl_course_name.setText("Ders :"+this.course.getName());
        setSize(1024,800);
        setLocation(Helper.screenCenter("x",getSize()),Helper.screenCenter("y",getSize()));
        setTitle(Config.PROJECT_TITLE);
        try {
            setIconImage(ImageIO.read(new File("src/com/patikaclone/Image/icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        btn_content_update.setText("Düzenle");
        btn_content_create_delete.setText("Sil");
        fld_content_url.setText(content.getUrl());
        txt_content_info.setText(content.getInfo());
        btn_question_insert.setEnabled(true);
        btn_question_update_delete.setEnabled(true);
        fld_content_title.setText(content.getTitle());

        mdl_question_list=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column==0){
                    return false;
                }
                return super.isCellEditable(row, column);
            }

        };
        Object[] col_question_list={"ID","Soru","Cevap 1","Cevap 2","Cevap 3","Cevap 4","Doğru Cevap","İçerik ID"};
        mdl_question_list.setColumnIdentifiers(col_question_list);
        this.row_question_list=new Object[col_question_list.length];
        tbl_question_list.setModel(mdl_question_list);
        tbl_question_list.getTableHeader().setReorderingAllowed(false);
        tbl_question_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_user_id = tbl_question_list.getValueAt(tbl_question_list.getSelectedRow(), 0).toString();
                fld_question_id.setText(select_user_id);
            }catch(Exception exception){}

        });

        btn_content_update.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_content_title)){
                Helper.showMsg("fill");
            }else{
                String title=fld_content_title.getText();
                String url=fld_content_url.getText();
                String info=txt_content_info.getText();
                if(Content.updateData(this.content.getId(),title,info,url)){
                    Helper.showMsg("done");

                }else{
                    Helper.showMsg("error");
                }
            }

        });


        btn_content_create_delete.addActionListener(e -> {
            if(Helper.confirm("sure")){
                if(Content.deleteData(this.content.getId())){
                    Helper.showMsg("done");
                    Question.deleteContentData(this.content.getId());
                    dispose();
                }else{
                    Helper.showMsg("error");
                }
            }
        });

        btn_question_insert.addActionListener(e -> {
            QuestionGUI qgui=new QuestionGUI(this.content.getId());
            qgui.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    getQuestionList();

                }
            });
        });
        getQuestionList();
        btn_question_update_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuestionGUI qgui=new QuestionGUI(Question.getFetch(Integer.parseInt(fld_question_id.getText())));
                qgui.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        getQuestionList();

                    }
                });
            }

        });

        btn_backward.addActionListener(e -> {
            dispose();
        });
    }
    public void getQuestionList(){
        DefaultTableModel clearModel= (DefaultTableModel) tbl_question_list.getModel();
        clearModel.setRowCount(0);
        int i=0;
        for(Question obj :Question.getList(this.content.getId())){
            i=0;
            row_question_list[i++]=obj.getId();
            row_question_list[i++]=obj.getQuest();
            row_question_list[i++]=obj.getO1();
            row_question_list[i++]=obj.getO2();
            row_question_list[i++]=obj.getO3();
            row_question_list[i++]=obj.getO4();
            row_question_list[i++]=obj.getAnswer();
            row_question_list[i]=obj.getContent_id();
            mdl_question_list.addRow(row_question_list);

        }
    }
}
