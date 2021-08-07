package com.patikaclone.View;

import com.patikaclone.Helper.Config;
import com.patikaclone.Helper.Helper;
import com.patikaclone.Model.Student;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class StudentGUI extends JFrame {
    private JPanel wrapper;
    private JLabel fld_welcome;
    private JPanel wtop;
    private JPanel wbotom;
    private JTabbedPane tab_student_patika;
    private JTable tbl_patika_list;
    private final Student student;

    public StudentGUI(Student student) {
        this.student=student;
        add(wrapper);
        fld_welcome.setText("Hoşgeldin "+student.getName());
        setSize(1024,576);
        setLocation(Helper.screenCenter("x",getSize()),Helper.screenCenter("y",getSize()));
        try {
            setIconImage(ImageIO.read(new File("src/com/patikaclone/Image/icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);

        //TODO öğrenci paneli yaz


        setVisible(true);

    }
}
