package com.patikaclone.View;

import com.patikaclone.Helper.Config;
import com.patikaclone.Helper.Helper;
import com.patikaclone.Model.Patika;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;


public class PatikaDeleteGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_patika_delete;
    private JButton btn_patika_delete;
    private Patika patika;

    public PatikaDeleteGUI(Patika patika)  {
        this.patika = patika;
        add(wrapper);
        setSize(300,150);
        fld_patika_delete.setText(patika.getName());
        setLocation(Helper.screenCenter("x",getSize()), Helper.screenCenter("y",getSize()));
        try {
            setIconImage(ImageIO.read(new File("src/com/patikaclone/Image/icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        Helper.setLayout();
        setVisible(true);
        btn_patika_delete.addActionListener(e -> {
            if(Helper.confirm("sure")) {
                Patika.deleteRelatedCourse(patika.getId());
                if (Patika.deleteData(patika.getId())) {
                    Helper.showMsg("done");
                    dispose();
                } else {
                    Helper.showMsg("error");
                }
            }
        });
    }
}
