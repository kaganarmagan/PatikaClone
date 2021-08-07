package com.patikaclone.View;

import com.patikaclone.Helper.Config;
import com.patikaclone.Helper.Helper;
import com.patikaclone.Model.Course;
import com.patikaclone.Model.Operator;
import com.patikaclone.Model.Patika;
import com.patikaclone.Model.Users;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;


public class OperatorGUI extends JFrame {
    private final Operator operator;
    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JPanel pnl_user_list;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private JPanel pnl_user_form;
    private JTextField fld_user_name;
    private JTextField fld_user_uname;
    private JTextField fld_user_psw;
    private JComboBox cmb_user_type;
    private JButton btn_user_insert;
    private JButton btn_user_delete;
    private JTextField fld_user_ID;
    private JTextField fld_sh_user_name;
    private JTextField fld_sh_user_uname;
    private JComboBox cmb_sh_user_type;
    private JButton btn_user_search;
    private JPanel pnl_patika_list;
    private JPanel pnl_src_user;
    private JTable tbl_patika_list;
    private JScrollPane scrl_patika_list;
    private JTextField fld_patika_name;
    private JButton btn_insert_patika;
    private JPanel pnl_course_list;
    private JScrollPane scrl_course_list;
    private JTable tbl_course_list;
    private JPanel pnl_course_right;
    private JTextField fld_course_name;
    private JTextField fld_course_lang;
    private JComboBox<String> cmb_course_patika;
    private JComboBox<String> cmb_course_educator;
    private JButton btn_course_insert;
    private JTable tbl_question_list;
    private JButton btn_question_insert_update;
    private JTextField fld_question_id;
    private JButton btn_search_question;
    private JComboBox cmb_search_content;
    private JButton btn_delete_question;
    private JComboBox cmb_content_list;
    private JTable tbl_content_list;
    private JButton btn_content_insert;
    private JComboBox cmb_course_list;
    private JButton btn_content_update_delete;
    private JTextField fld_content_id;
    private JComboBox cmb_search_course;
    private JTextField fld_content_search;
    private JButton btn_content_search;
    private JTextField fld_patika_id;
    private DefaultTableModel mdl_user_list;
    private DefaultTableModel mdl_patika_list;
    private DefaultTableModel mdl_course_list;
    private Object[] row_user_list;
    private final  JComboBox<String> cmb_types=new JComboBox<>();
    private  Object[] row_patika_list;
    private JPopupMenu patika_menu;
    private Object[] row_course_list;





    public OperatorGUI(Operator operator){
        this.operator = operator;
        Helper.setLayout();
        add(wrapper);
        setSize(1024,576);
        setLocation(Helper.screenCenter("x",getSize()), Helper.screenCenter("y",getSize()));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        try {
            setIconImage(ImageIO.read(new File("src/com/patikaclone/Image/icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setVisible(true);

        lbl_welcome.setText("Hoşgeldin "+operator.getName());

        mdl_user_list=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column==0){
                    return false;
                }
                return super.isCellEditable(row, column);
            }

        };
        Object[] col_user_list={"ID","Ad Soyad","Kullancı Adı","Şifre","Üyelik Tipi"};
        mdl_user_list.setColumnIdentifiers(col_user_list);
        this.row_user_list=new Object[col_user_list.length];

        cmb_types.addItem("operator");
        cmb_types.addItem("educator");
        cmb_types.addItem("student");
        getUserTable();
        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);
        tbl_user_list.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(cmb_types));

        tbl_user_list.getSelectionModel().addListSelectionListener(e -> {
           try {
               String select_user_id = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString();
               fld_user_ID.setText(select_user_id);
           }catch(Exception exception){}

        });
        tbl_user_list.getModel().addTableModelListener(e -> {
             if(e.getType()==TableModelEvent.UPDATE){
                int user_id=Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),0).toString());
                String user_name=tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),1).toString();
                String user_uname=tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),2).toString();
                String user_psw=tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),3).toString();
                String user_type=tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),4).toString();

                 if (user_name.isEmpty()||user_uname.isEmpty()||user_psw.isEmpty()||user_type.isEmpty()){
                     Helper.showMsg("fill");
                 }else{
                     if(Users.updateData(user_id,user_name,user_uname,user_psw,user_type)){

                         Helper.showMsg("done");
                     }else{
                         Helper.showMsg("error");
                     }
                     getUserTable();
                     educatorComboBox();
                     getCourseTable();

                 }

             }
        });
        mdl_patika_list=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column==0){
                    return false;
                }
                return super.isCellEditable(row, column);
            }

        };
        patika_menu=new JPopupMenu();
        JMenuItem updateMenu=new JMenuItem("Güncelle");
        JMenuItem deleteMenu=new JMenuItem("Sil");
        patika_menu.add(updateMenu);
        patika_menu.add(deleteMenu);
        updateMenu.addActionListener(e->{
            int select_id=Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(),0).toString());
            Patika obj=Patika.getFetch(select_id);
            PatikaUpdateGUI pgui=new PatikaUpdateGUI(obj);
            pgui.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    getCourseTable();
                    getPatikaTable();
                    patikaComboBox();
                }
            });
        });
        deleteMenu.addActionListener(e -> {
            int select_id=Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(),0).toString());
            Patika obj=Patika.getFetch(select_id);
            PatikaDeleteGUI pgui=new PatikaDeleteGUI(obj);
            pgui.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    getCourseTable();
                    getPatikaTable();
                    patikaComboBox();

                }
            });
        });
        Object[] col_patika_list={"ID","Patika ismi"};
        mdl_patika_list.setColumnIdentifiers(col_patika_list);
        row_patika_list=new Object[col_patika_list.length];
        getPatikaTable();
        tbl_patika_list.setModel(mdl_patika_list);
        tbl_patika_list.setComponentPopupMenu(patika_menu);
        tbl_patika_list.getTableHeader().setReorderingAllowed(false);
        tbl_patika_list.getColumnModel().getColumn(0).setMaxWidth(50);

        tbl_patika_list.addMouseListener(new MouseAdapter() {


            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow=tbl_patika_list.rowAtPoint(e.getPoint());
                tbl_patika_list.setRowSelectionInterval(selectedRow,selectedRow);
            }
        });

        tbl_patika_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_patika_id = tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(), 0).toString();
                fld_patika_id.setText(select_patika_id);
            }catch(Exception exception){}

        });
        //##Patika
       // Course
        mdl_course_list=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column==0){
                    return false;
                }
                return super.isCellEditable(row, column);
            }

        };

        Object[] col_course_list={"ID","Eğitmen","Patika","Ders Adı","Programlama dili"};
        mdl_course_list.setColumnIdentifiers(col_course_list);
        this.row_course_list=new Object[col_course_list.length];
        getCourseTable();
        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(50);
        patikaComboBox();
        educatorComboBox();



        btn_user_insert.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_name)||Helper.isFieldEmpty(fld_user_uname)||Helper.isFieldEmpty(fld_user_psw)){
                Helper.showMsg("fill");
            }else{
                if(Users.insertData( fld_user_name.getText(), fld_user_uname.getText(), fld_user_psw.getText(), cmb_user_type.getSelectedItem().toString().toLowerCase(Locale.ROOT))){
                    fld_user_psw.setText(null);
                    fld_user_uname.setText(null);
                    fld_user_name.setText(null);
                    Helper.showMsg("done");
                    getUserTable();
                    educatorComboBox();

                }else{
                    Helper.showMsg("error");
                }


            }
        });
        btn_user_delete.addActionListener(e->{

            if (Helper.isFieldEmpty(fld_user_ID)){
                Helper.showMsg("fill");
            }else{
                Users.deleteRelatedCourse(Integer.parseInt(fld_user_ID.getText()));
                if(Users.deleteData(Integer.parseInt(fld_user_ID.getText()))){
                    fld_user_ID.setText(null);
                    Helper.showMsg("done");
                    getCourseTable();
                    getUserTable();
                    educatorComboBox();

                }else{
                    Helper.showMsg("error");
                }
            }
        });
        btn_user_search.addActionListener(e -> {
            String name=fld_sh_user_name.getText();
            String uname=fld_sh_user_uname.getText();
            String type=cmb_sh_user_type.getSelectedItem().toString();
            String query=Users.searchQuery(name,uname);
            if(!type.equals("hepsi")){
                query=query.replace("ORDER BY id;","");
                query+="AND types='{{type}}'::types ORDER BY id;".replace("{{type}}",type);
            }
            getUserTable(Users.searchUserList(query));

        });
        btn_logout.addActionListener(e -> {
            LoginGUI logui=new LoginGUI();
            dispose();
        });


        btn_insert_patika.addActionListener(e -> {
           if(Helper.isFieldEmpty(fld_patika_name)) {
                Helper.showMsg("fill");
           }else{
               String name=fld_patika_name.getText();
               if(Patika.insertData(name)){
                   Helper.showMsg("done");
                   fld_patika_name.setText(null);
               }else{
                Helper.showMsg("error");
               }
           }
           getPatikaTable();
           patikaComboBox();
           getCourseTable();
        });

        btn_course_insert.addActionListener(e -> {
            String name=fld_course_name.getText();
            String lang=fld_course_lang.getText();
            int patika_id=Patika.getFetch(cmb_course_patika.getSelectedItem().toString()).getId();
            int user_id=Users.getFetch(cmb_course_educator.getSelectedItem().toString()).getId();
            if(Helper.isFieldEmpty(fld_course_name)||Helper.isFieldEmpty(fld_course_lang)){
                      Helper.showMsg("fill");
            }else{
                if(Course.insertData(user_id,patika_id,name,lang)){
                    Helper.showMsg("done");
                    getCourseTable();
                }else{
                    Helper.showMsg("error");
                }
            }

        });
        //TODO İÇERİK VE SORU TABLOLARINI BAĞLA;
    }

    public void getPatikaTable() {
        DefaultTableModel clearModel= (DefaultTableModel) tbl_patika_list.getModel();
        clearModel.setRowCount(0);
        int i=0;
        for(Patika obj : Patika.getList()){
            i=0;
            row_patika_list[i++]=obj.getId();
            row_patika_list[i]=obj.getName();
            mdl_patika_list.addRow(row_patika_list);
        }
    }

    public void getCourseTable(){
        DefaultTableModel clearModel= (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0);
        int i=0;
        for(Course obj:Course.getList()){
            i=0;
            row_course_list[i++]=obj.getId();
            row_course_list[i++]=obj.getEducator().getName();
            row_course_list[i++]=obj.getPatika().getName();
            row_course_list[i++]=obj.getName();
            row_course_list[i]=obj.getLang();
            mdl_course_list.addRow(row_course_list);

        }
    }
    public void getUserTable(ArrayList<Users> usersArrayList){
        DefaultTableModel clearModel= (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);
        int i=0;
        for(Users obj :usersArrayList){

            i=0;
            row_user_list[i++]=obj.getId();
            row_user_list[i++]=obj.getName();
            row_user_list[i++]=obj.getUname();
            row_user_list[i++]=obj.getPsw();
            row_user_list[i]=obj.getTypes();
            mdl_user_list.addRow(row_user_list);

        }


    }
    public void getUserTable(){
        DefaultTableModel clearModel= (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);
        int i=0;
        for(Users obj :Users.getList()){
            i=0;
            row_user_list[i++]=obj.getId();
            row_user_list[i++]=obj.getName();
            row_user_list[i++]=obj.getUname();
            row_user_list[i++]=obj.getPsw();
            row_user_list[i]=obj.getTypes();
            mdl_user_list.addRow(row_user_list);

        }


    }
    public void educatorComboBox(){
        cmb_course_educator.removeAllItems();
        for (Users user:Users.getEducatorList()
        ) {
            cmb_course_educator.addItem(user.getName());
        }
    }
    public void patikaComboBox(){
        cmb_course_patika.removeAllItems();
        for (Patika pat:Patika.getList()
        ) {
            cmb_course_patika.addItem(pat.getName());
        }
    }
}
