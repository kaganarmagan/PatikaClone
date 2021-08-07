package com.patikaclone.View;

import com.patikaclone.Helper.Config;
import com.patikaclone.Helper.Helper;
import com.patikaclone.Model.Users;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class SignUpGUI extends JFrame{
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JTextField fld_signup_uname;
    private JPasswordField fld_signup_psw;
    private JPasswordField fld_signup_psw_2;
    private JTextField fld_signup_name;
    private JButton btn_sign_up;
    private JButton btn_backward;

    public SignUpGUI(){
        add(wrapper);
        try {
            setIconImage(ImageIO.read(new File("src/com/patikaclone/Image/icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setSize(550,550);
        setLocation(Helper.screenCenter("x",getSize()), Helper.screenCenter("y",getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);
        btn_sign_up.addActionListener(e -> {
           String uname;
            try{
                uname=Users.getUserNameFetch(fld_signup_uname.getText().trim().toLowerCase(Locale.ROOT)).getUname();
            }catch(NullPointerException throwables){
                uname=null;
            }
            if(fld_signup_psw.getText().equals(fld_signup_psw_2.getText())){
                if(uname!=null){
                    Helper.showMsg("used");
                }else{
                    if(Users.insertData(fld_signup_name.getText(),fld_signup_uname.getText(),fld_signup_psw.getText(),"student")){
                        Helper.showMsg("done");
                        LoginGUI login=new LoginGUI();
                        dispose();
                    }else{
                        Helper.showMsg("error");
                    }
                }
            }else{
                Helper.showMsg("notMatch");
                fld_signup_psw.setText(null);
                fld_signup_psw_2.setText(null);
            }
        });
        btn_backward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginGUI login=new LoginGUI();
                dispose();
            }
        });
    }


}
