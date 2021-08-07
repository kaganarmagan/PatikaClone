package com.patikaclone.Helper;

import javax.swing.*;
import java.awt.*;

public abstract  class Helper {

    public static int screenCenter(String axis, Dimension size){
        return switch (axis) {
            case "y" -> (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() - size.getHeight()) / 2);
            case "x" -> (int) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth() - size.getWidth()) / 2);
            default -> 0;
        };

    }

    public static void setLayout(){
        for (UIManager.LookAndFeelInfo info :UIManager.getInstalledLookAndFeels()){
            if("Nimbus".equals(info.getName())){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
    public static boolean isFieldEmpty(JTextField field){
        return field.getText().trim().isEmpty();

    }
    public static void showMsg(String str){
        optionPageTR();
        String msg="";
        String title="";
        switch (str) {
            case "fill" -> {
                msg = "Lütfen boşlukları doldurunuz !";
                title = "Hata";
            }
            case "done" -> {
                msg = "İşlem başarılı";
                title = "Sonuç";
            }case "error" -> {
                msg = "Bir hata oluştu.İşlem başarısız";
                title = "Hata";
            }case "failLogin"->{
                msg ="Kullanıcı adı veya şifre hatalı!";
                title="Giriş başarısız";
            } case "notMatch"->{
                msg="Şifreler birbiriyle eşleşmiyor!";
                title="Hata";
            }case "used"->{
                msg="Bu kullanıcı adı zaten var!";
                title="Hata";
            }case "null"->{
                msg="Uygun ders atanmamış yöneticinizle iletişişme geçin!";
                title="Hata";
            }
            default -> msg = str;
        }
        JOptionPane.showMessageDialog(null,msg,title,JOptionPane.INFORMATION_MESSAGE);
    }
    public static void optionPageTR(){
        UIManager.put("OptionPane.okButtonText","Tamam");
        UIManager.put("OptionPane.yesButtonText","Evet");
        UIManager.put("OptionPane.noButtonText","Hayır");
    }


    public static boolean confirm(String str){
        optionPageTR();
        String msg;
        switch (str){
            case "sure":
                msg="Bu işlemi gerçekleştirmek istediğine emin misin ?";
                break;
            default:
                msg=str;
        }
        return JOptionPane.showConfirmDialog(null,msg,"Onay",JOptionPane.YES_NO_OPTION)==0;


    }

}
