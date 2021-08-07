package com.patikaclone.View;

import com.patikaclone.Helper.Config;
import com.patikaclone.Helper.Helper;
import com.patikaclone.Model.Patika;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class PatikaUpdateGUI extends JFrame{
    private JPanel wrapper;
    private JLabel fld_lbl_patika_name;
    private JTextField fld_patika_name;
    private JButton btn_update_patika;
    private Patika patika;
    public PatikaUpdateGUI(Patika patika){
        this.patika=patika;
        add(wrapper);
        setSize(300,150);
        Helper.setLayout();
        setLocation(Helper.screenCenter("x",getSize()), Helper.screenCenter("y",getSize()));
        try {
            setIconImage(ImageIO.read(new File("src/com/patikaclone/Image/icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);

        setVisible(true);

        fld_patika_name.setText(patika.getName());

        btn_update_patika.addActionListener(e -> {
           String name= fld_patika_name.getText();
           if(Helper.isFieldEmpty(fld_patika_name)) {
               Helper.showMsg("fill");
           }else {
               if (Patika.updateData(patika.getId(), name)) {
                   Helper.showMsg("done");
                   dispose();
               }else{
                   Helper.showMsg("error");
               }
           }
        });
    }
}
