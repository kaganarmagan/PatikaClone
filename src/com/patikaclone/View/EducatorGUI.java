package com.patikaclone.View;

import com.patikaclone.Helper.Config;
import com.patikaclone.Helper.Helper;
import com.patikaclone.Model.Content;
import com.patikaclone.Model.Course;
import com.patikaclone.Model.Educator;
import com.patikaclone.Model.Question;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class EducatorGUI extends JFrame {

    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JLabel fld_welcome;
    private JTabbedPane tab_educator_course;
    private JTable tbl_course_list;
    private JButton btn_logout;
    private JTable tbl_content_list;
    private JButton btn_content_insert;
    private JComboBox cmb_course_list;
    private JButton btn_content_update_delete;
    private JTextField fld_content_id;
    private JPanel pnl_src_user;
    private JButton btn_content_search;
    private JComboBox<String> cmb_search_course;
    private JTextField fld_content_search;
    private JButton btn_question_insert_update;
    private JButton btn_delete_question;
    private JComboBox cmb_content_list;
    private JComboBox cmb_search_content;
    private JTable tbl_question_list;
    private JTextField fld_question_id;
    private JButton btn_search_question;
    private Educator educator;
    private Object[] row_course_list;
    private DefaultTableModel mdl_course_list;
    private Object[] row_content_list;
    private DefaultTableModel mdl_content_list;
    private Object[] row_question_list;
    private DefaultTableModel mdl_question_list;


    public EducatorGUI(Educator educator) {
        this.educator = educator;
        add(wrapper);
        try {
            setIconImage(ImageIO.read(new File("src/com/patikaclone/Image/icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setSize(1024, 576);
        setLocation(Helper.screenCenter("x", getSize()), Helper.screenCenter("y", getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        fld_welcome.setText("Hoşgeldin " + educator.getName());
        setVisible(true);
        getComboBoxCourse();
        getComboBoxSearchContent();
        getComboBoxSearchCourse();
        getComboBoxContentList();

        // course
        mdl_course_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }

        };
        Object[] col_course_list = {"ID", "Patika Adı", "Ders Adı", "Programlama Dili"};
        mdl_course_list.setColumnIdentifiers(col_course_list);
        this.row_course_list = new Object[col_course_list.length];
        getCourseTable();
        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);
        //  ##course

        // content
        mdl_content_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }

        };
        Object[] col_content_list = {"ID", "İçerik Başlığı", "İçerik Bilgisi", "İçerik Youtube Linki",  "Ders"};
        mdl_content_list.setColumnIdentifiers(col_content_list);
        this.row_content_list = new Object[col_content_list.length];
        getContentList();
        tbl_content_list.setModel(mdl_content_list);
        tbl_content_list.getTableHeader().setReorderingAllowed(false);
        tbl_content_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_user_id = tbl_content_list.getValueAt(tbl_content_list.getSelectedRow(), 0).toString();
                fld_content_id.setText(select_user_id);
            }catch(Exception exception){}

        });
        //## content


        //question
        mdl_question_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }

        };
        Object[] col_question_list = {"ID", "Soru", "Cevap 1", "Cevap 2","Cevap 3","Cevap 4","Doğru Cevap","İçerik Adı"};
        mdl_question_list.setColumnIdentifiers(col_question_list);
        this.row_question_list = new Object[col_question_list.length];
        getQuestionList();
        tbl_question_list.setModel(mdl_question_list);
        tbl_question_list.getTableHeader().setReorderingAllowed(false);
        tbl_question_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_user_id = tbl_question_list.getValueAt(tbl_question_list.getSelectedRow(), 0).toString();
                fld_question_id.setText(select_user_id);
            }catch(Exception exception){}

        });
        //## question


        btn_content_update_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Helper.isFieldEmpty(fld_content_id)){
                    Helper.showMsg("fill");
                }else {
                    Content obj = Content.getFetch(Integer.parseInt(fld_content_id.getText()));
                    Course object = Course.getFetch(obj.getCourse_id());
                    ContentCreatorGUI ccgui = new ContentCreatorGUI(obj, object);
                    ccgui.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            getContentList();
                            getComboBoxContentList();
                            getQuestionList();
                            getComboBoxSearchContent();
                        }
                    });
                }
            }
        });

        btn_content_insert.addActionListener(e -> {
            if(cmb_course_list.getSelectedItem().equals(null)){
                Helper.showMsg("null");
            }else {
                Course c=Course.getFetch(cmb_course_list.getSelectedItem().toString());

                ContentCreatorGUI ccgui = new ContentCreatorGUI(c);
                ccgui.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        getContentList();
                        getComboBoxContentList();
                        getQuestionList();
                        getComboBoxSearchContent();
                    }
                });
            }
        });
        btn_logout.addActionListener(e -> {
            LoginGUI logui = new LoginGUI();
            dispose();
        });
        btn_content_search.addActionListener(e -> {
            String content=fld_content_search.getText();
            if(cmb_search_course.getSelectedItem().toString().equals("Hepsi")){
                getSearchedContentList(-1,content);
            }else{
                getSearchedContentList(Course.getFetch(cmb_search_course.getSelectedItem().toString()).getId(),content);
            }
        });

        btn_delete_question.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_question_id)){
                Helper.showMsg("fill");
            }else{
                QuestionGUI qgui=new QuestionGUI(Question.getFetch(Integer.parseInt(fld_question_id.getText())));
                qgui.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        getQuestionList();
                    }
                });
            }
        });
        btn_question_insert_update.addActionListener(e -> {
           if(cmb_content_list.getSelectedItem().equals(null)){//TODO devreye girmiyor!!
               Helper.showMsg("null");
           }else {
               int content_id = Content.getFetch(cmb_content_list.getSelectedItem().toString()).getId();
               QuestionGUI questionGUI = new QuestionGUI(content_id);
               questionGUI.addWindowListener(new WindowAdapter() {
                   @Override
                   public void windowClosed(WindowEvent e) {
                       getQuestionList();
                   }
               });
           }
        });

        btn_search_question.addActionListener(e -> {
            String content=cmb_search_content.getSelectedItem().toString();
            if(cmb_search_content.getSelectedItem().toString().equals("Hepsi")){
                getQuestionList();
            }else{
              getSearchedQuestionList(content);

            }
        });
    }



    private void getSearchedQuestionList(String content) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_question_list.getModel();
        clearModel.setRowCount(0);
        int i = 0;
        for (Question obj : Question.getEducatorList(Content.getFetch(content), this.educator.getId())) {
            i = 0;
            row_question_list[i++] = obj.getId();
            row_question_list[i++] = obj.getQuest();
            row_question_list[i++] = obj.getO1();
            row_question_list[i++] = obj.getO2();
            row_question_list[i++] = obj.getO3();
            row_question_list[i++] = obj.getO4();
            row_question_list[i++] = obj.getAnswer();
            row_question_list[i] = obj.getContent().getTitle();

            mdl_question_list.addRow(row_question_list);
        }
    }

    private void getQuestionList(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_question_list.getModel();
        clearModel.setRowCount(0);
        int i=0;
        for (Question obj : Question.getEducatorList(Content.getList(), this.educator.getId())) {
            i = 0;
            row_question_list[i++] = obj.getId();
            row_question_list[i++] = obj.getQuest();
            row_question_list[i++] = obj.getO1();
            row_question_list[i++] = obj.getO2();
            row_question_list[i++] = obj.getO3();
            row_question_list[i++] = obj.getO4();
            row_question_list[i++] = obj.getAnswer();
            row_question_list[i] = obj.getContent().getTitle();

            mdl_question_list.addRow(row_question_list);

        }
    }


    private void getSearchedContentList(int course_id, String content) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_content_list.getModel();
        clearModel.setRowCount(0);
        int i=0;
        for (Content obj : Content.getSearchedList(course_id,content)) {
            if (this.educator.getId() == obj.getEducator().getId()) {
                i = 0;
                row_content_list[i++] = obj.getId();
                row_content_list[i++] = obj.getTitle();
                row_content_list[i++] = obj.getInfo();
                row_content_list[i++] = obj.getUrl();
                row_content_list[i] = obj.getCourse().getName();
                mdl_content_list.addRow(row_content_list);
            }
        }
    }

    private void getContentList() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_content_list.getModel();
        clearModel.setRowCount(0);
        int i = 0;
        for (Content obj : Content.getList()) {
            if (this.educator.getId() == obj.getEducator().getId()) {
                i = 0;
                row_content_list[i++] = obj.getId();
                row_content_list[i++] = obj.getTitle();
                row_content_list[i++] = obj.getInfo();
                row_content_list[i++] = obj.getUrl();
                row_content_list[i] = obj.getCourse().getName();
                mdl_content_list.addRow(row_content_list);
            }
        }
    }

    public void getCourseTable() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0);
        int i = 0;
        for (Course obj : Course.getList()) {
            if (this.educator.getId() == obj.getEducator().getId()) {
                i = 0;
                row_course_list[i++] = obj.getId();
                row_course_list[i++] = obj.getPatika().getName();
                row_course_list[i++] = obj.getName();
                row_course_list[i] = obj.getLang();
                mdl_course_list.addRow(row_course_list);
            }
        }
    }

    public void getComboBoxCourse(){
        cmb_course_list.removeAllItems();
        for (Course c:Course.getList()) {
            if(c.getEducator().getId()==this.educator.getId()) {
                cmb_course_list.addItem(c.getName());
            }
        }
    }

    public void getComboBoxSearchCourse(){
        cmb_search_course.removeAllItems();
        for (Course c:Course.getList()) {
            if(c.getEducator().getId()==this.educator.getId()) {
                cmb_search_course.addItem(c.getName());
            }
        }
        cmb_search_course.addItem("Hepsi");
    }
    public void getComboBoxSearchContent(){
        cmb_search_content.removeAllItems();
        for (Content c:Content.getList()) {
            if(c.getEducator().getId()==this.educator.getId()) {
                cmb_search_content.addItem(c.getTitle());
            }
        }
        cmb_search_content.addItem("Hepsi");
    }
    public void getComboBoxContentList(){
       cmb_content_list.removeAllItems();
        for (Content c:Content.getList()) {
            if(c.getEducator().getId()==this.educator.getId()) {
                cmb_content_list.addItem(c.getTitle());
            }
        }
    }

}
