package com.patikaclone.View;

import com.patikaclone.Helper.Config;
import com.patikaclone.Helper.Helper;
import com.patikaclone.Model.Educator;
import com.patikaclone.Model.Operator;
import com.patikaclone.Model.Student;
import com.patikaclone.Model.Users;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class LoginGUI extends JFrame{
    private JPanel wrapper;
    private JLabel lbl_welcome;
    private JPanel wbottom;
    private JPanel wtop;
    private JTextField fld_login_uname;
    private JPasswordField fld_login_psw;
    private JButton btn_signup;
    private JButton btn_login;

    public  LoginGUI(){
       add(wrapper);
       try {
           setIconImage(ImageIO.read(new File("src/com/patikaclone/Image/icon.png")));
       } catch (IOException e) {
           e.printStackTrace();
       }
       setSize(500,500);
       setLocation(Helper.screenCenter("x",getSize()), Helper.screenCenter("y",getSize()));
       setDefaultCloseOperation(DISPOSE_ON_CLOSE);
       setTitle(Config.PROJECT_TITLE);
       setResizable(true);
       setVisible(true);
       lbl_welcome.setText("Kullanıcı Girişi");
       btn_signup.setIcon(new ImageIcon("src/com/patikaclone/Image/SignUpIcon.png"));
       btn_signup.setText(null);
       btn_signup.setToolTipText("Kayıt ol");
       btn_signup.setVerticalTextPosition(AbstractButton.CENTER);
       btn_signup.setHorizontalTextPosition(AbstractButton.LEADING);
       btn_signup.setMnemonic(KeyEvent.VK_I);
       btn_signup.addActionListener(e -> {
           SignUpGUI sign=new SignUpGUI();
           dispose();
       });


       btn_login.addActionListener(e -> {
           String psw=fld_login_psw.getText();
           String uname=fld_login_uname.getText();

           Users user=Users.login(uname,psw);
           String str;
           try{
               str=user.getTypes();
           }catch (NullPointerException throwables){
               str=null;
           }
           if(str != null) {
               switch (user.getTypes()) {
                   case "operator":
                       OperatorGUI ogui = new OperatorGUI(new Operator(user.getId(), user.getName(), user.getUname(), user.getPsw()));
                       dispose();
                       break;
                   case "educator":
                       EducatorGUI egui =new EducatorGUI(new Educator(user.getId(),user.getName(),user.getUname(),user.getPsw()));
                       dispose();
                       break;
                   case "student":
                       StudentGUI sgui =new StudentGUI(new Student(user.getId(), user.getName(),user.getUname(),user.getPsw()));
                       dispose();
                       break;
                   default:
                       break;
               }
           }else{
               Helper.showMsg("failLogin");
           }
       });
    }
}
