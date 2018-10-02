package jp.mkserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class SettingPanel extends JPanel {

    GUIUtil gui;
    JTextField text1;
    JTextField text2;

    public SettingPanel(GUIUtil gui){
        this.gui = gui;
        refresh();
    }

    public void refresh(){
        this.removeAll();
        text1 = new JTextField("ここにサイズを入力", 20);
        text2 = new JTextField("ここにフォント名を入力", 20);
        JButton button1 = new JButton("保存して戻る");
        button1.addActionListener(new ClickAction2(this));
        JButton button2 = new JButton("保存せず戻る");
        button2.addActionListener(new ClickAction1(this));
        JPanel j = new JPanel();
        j.setOpaque(false);
        j.add(text1);
        j.add(text2);
        j.add(button1);
        j.add(button2);
        URL urls=getClass().getClassLoader().getResource("guiutil.png");
        Icon title = new ImageIcon(urls);
        JLabel titlelabel = new JLabel(title);
        titlelabel.setOpaque(false);
        titlelabel.setLayout(new BorderLayout());
        titlelabel.add(j,BorderLayout.CENTER);
        add(titlelabel,BorderLayout.CENTER);
    }

    class ClickAction1 implements ActionListener {

        SettingPanel gui;
        public ClickAction1(SettingPanel gui){
            this.gui = gui;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            PluginsGUI.showMainPlugin();
            refresh();
        }
    }

    class ClickAction2 implements ActionListener {

        SettingPanel gui;
        public ClickAction2(SettingPanel gui){
            this.gui = gui;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int size;
            try{
                size = Integer.parseInt(text1.getText());
            }catch (NumberFormatException ee){
                JOptionPane.showMessageDialog(gui,"文字サイズが数字ではありません");
                return;
            }
            gui.gui.config.updateFile(size,text2.getText());
            JOptionPane.showMessageDialog(gui,"コンフィグをアップデートしました。");
            PluginsGUI.showMainPlugin();
            refresh();
        }
    }
}
