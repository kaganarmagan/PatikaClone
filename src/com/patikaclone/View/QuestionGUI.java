package com.patikaclone.View;

import com.patikaclone.Helper.Config;
import com.patikaclone.Helper.Helper;
import com.patikaclone.Model.Content;
import com.patikaclone.Model.Question;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class QuestionGUI extends JFrame{
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JTextField fld_question;
    private JTextField fld_option1;
    private JTextField fld_option2;
    private JTextField fld_option3;
    private JTextField fld_option4;
    private JComboBox<String> cmb_quest_tans;
    private JButton btn_action;
    private JLabel fld_main_title;
    private JButton btn_backward;
    private JButton btn_delete;
    private JButton but;

    public QuestionGUI(int content_id){
        add(wrapper);
        try {
            setIconImage(ImageIO.read(new File("src/com/patikaclone/Image/icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        cmb_quest_tans.addItem("1");
        cmb_quest_tans.addItem("2");
        cmb_quest_tans.addItem("3");
        cmb_quest_tans.addItem("4");
        setSize(800,576);
        setLocation(Helper.screenCenter("x",getSize()), Helper.screenCenter("y",getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        fld_main_title.setText("Soru Ekleme Paneli");
        btn_action.setText("Ekle");
        btn_delete.setVisible(false);
        setVisible(true);
        btn_action.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_question)||Helper.isFieldEmpty(fld_option1)||Helper.isFieldEmpty(fld_option2)||Helper.isFieldEmpty(fld_option3)||Helper.isFieldEmpty(fld_option4)){
                Helper.showMsg("fill");
            }else{
                String q=fld_question.getText();
                String o1=fld_option1.getText();
                String o2=fld_option2.getText();
                String o3=fld_option3.getText();
                String o4=fld_option4.getText();
                int answer=(cmb_quest_tans.getSelectedIndex()+1);
                if(Question.insertData(q,o1,o2,o3,o4,answer,content_id)){
                    Helper.showMsg("done");

                    dispose();
                }else{
                    Helper.showMsg("error");
                }
            }
        });
        btn_backward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

    }
    public QuestionGUI(Question q){
        add(wrapper);
        try {
            setIconImage(ImageIO.read(new File("src/com/patikaclone/Image/icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        cmb_quest_tans.addItem("1");
        cmb_quest_tans.addItem("2");
        cmb_quest_tans.addItem("3");
        cmb_quest_tans.addItem("4");
        setSize(800,576);
        setLocation(Helper.screenCenter("x",getSize()), Helper.screenCenter("y",getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        fld_main_title.setText("Soru Güncelleme/Kaldırma Paneli");
        fld_question.setText(q.getQuest());
        fld_option1.setText(q.getO1());
        fld_option2.setText(q.getO2());
        fld_option3.setText(q.getO3());
        fld_option4.setText(q.getO4());
        cmb_quest_tans.setSelectedIndex((q.getAnswer()-1));
        btn_action.setText("Güncelle");
        btn_delete.setText("Kaldır");
        setVisible(true);
        btn_action.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_question)||Helper.isFieldEmpty(fld_option1)||Helper.isFieldEmpty(fld_option2)||Helper.isFieldEmpty(fld_option3)||Helper.isFieldEmpty(fld_option4)){
                Helper.showMsg("fill");
            }else{
                String question=fld_question.getText();
                String o1=fld_option1.getText();
                String o2=fld_option2.getText();
                String o3=fld_option3.getText();
                String o4=fld_option4.getText();
                int answer=(cmb_quest_tans.getSelectedIndex()+1);
                if(Question.UpdateData(q.getId(),question,o1,o2,o3,o4,answer)){
                    Helper.showMsg("done");

                    dispose();
                }else{
                    Helper.showMsg("error");
                }
            }
        });
        btn_backward.addActionListener(e -> dispose());
        btn_delete.addActionListener(e -> {
            if(Helper.confirm("sure")){
                if(Question.deleteData(q.getId())){
                    Helper.showMsg("done");
                    dispose();
                }else{
                    Helper.showMsg("error");
                }
            }

        });
    }

}
