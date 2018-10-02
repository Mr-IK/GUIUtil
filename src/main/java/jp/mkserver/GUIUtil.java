package jp.mkserver;

import jp.mkserver.apis.MCMAPI;
import jp.mkserver.apis.PluginManager;
import jp.mkserver.apis.event.EventAPI;
import jp.mkserver.apis.event.EventManager;
import jp.mkserver.apis.event.eventdata.Command;

import java.awt.*;
import java.util.Locale;

public class GUIUtil  extends PluginManager implements EventManager {
    ConfigFileManager config;
    SettingPanel set;
    @Override
    public void onEnable() {
        EventAPI.addListener(this);
        gui = MCMAPI.getGUIManager();
        config = new ConfigFileManager(this);
        set = new SettingPanel(this);
        PluginsGUI.addGUI(set,"GUIutil");
        MCMAPI.sendLog("[GUIUtil]起動しました。");
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onCommand(Command c){
        String mm = c.getCmd();
        if(!mm.startsWith("gutil")){
            return;
        }
        c.setCancelled(true);
        if(mm.equalsIgnoreCase("gutil")) {
            MCMAPI.sendLog("[GUIUtil]!gutil font [フォント名] : フォントを設定");
            MCMAPI.sendLog("[GUIUtil]!gutil size [サイズ] : 文字サイズを設定");
        }else if(mm.startsWith("gutil font ")){
            mm = mm.replace("gutil font ","");
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font fonts[] = ge.getAllFonts();
            for(Font font : fonts){
                if(font.getFontName(Locale.JAPAN).equalsIgnoreCase(mm)) {
                    setFont(mm);
                    MCMAPI.sendLog("[GUIUtil]フォントをロード");
                    return;
                }
            }
            MCMAPI.sendLog("[GUIUtil]フォントをロードできなかった");
        }else if(mm.startsWith("gutil size ")){
            mm = mm.replace("gutil size ","");
            try{
                int size = Integer.parseInt(mm);
                setMoziSize(size);
                MCMAPI.sendLog("[GUIUtil]文字サイズをロード");
            }catch (NumberFormatException ee){
                MCMAPI.sendLog("[GUIUtil]Error: サイズが数字ではありません！");
            }
        }
    }


    GUIManager gui;

    public void setMoziSize(int size){
        gui.area.setFont(new Font(gui.area.getFont().getName(), gui.area.getFont().getStyle(), size));
        gui.area.updateUI();
    }

    public void setFont(String font){
        gui.area.setFont(new Font(font, gui.area.getFont().getStyle(), gui.area.getFont().getSize()));
        gui.area.updateUI();
    }


}
