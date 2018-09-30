package jp.mkserver;

import jp.mkserver.apis.MCMAPI;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.Locale;


public class ConfigFileManager {
    File file;
    GUIUtil gui;
    public ConfigFileManager(GUIUtil gui){
        this.gui = gui;
        try{
            file = new File(getApplicationPath(GUIUtil.class).getParent().toString()+"\\GUIUtil\\config.txt");
            file.getParentFile().mkdir();
            if(file.createNewFile()) {
                MCMAPI.sendLog("[GUIUtil]コンフィグを生成中…");
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true),"utf-8"));
                bw.write("※※余計な文字列を書かないでください。最悪バグります");
                bw.newLine();
                bw.write("※mozi_sizeは文字サイズを変更できます。");
                bw.newLine();
                bw.write("mozi_size=12");
                bw.newLine();
                bw.write("※mozi_fontは文字フォントを変えられます。");
                bw.newLine();
                bw.write("mozi_font=ＭＳ ゴシック");
                bw.close();
            }
            load();
        }catch(IOException | URISyntaxException e){
            MCMAPI.sendLog(e.getMessage());
        }
    }

    public void load() {
        MCMAPI.sendLog("[GUIUtil]コンフィグをロード中…");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String str = br.readLine();
            while (str != null) {
                if (str.startsWith("mozi_size=")) {
                    String boor = str.replace("mozi_size=", "");
                    try{
                        int size = Integer.parseInt(boor);
                        gui.setMoziSize(size);
                        MCMAPI.sendLog("[GUIUtil]文字サイズをロード");
                    }catch (NumberFormatException ee){
                        MCMAPI.sendLog("Error: サイズが数字ではありません！");
                    }
                }else if (str.startsWith("mozi_font=")) {
                    String boor = str.replace("mozi_font=", "");
                    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                    Font fonts[] = ge.getAllFonts();
                    for(Font font : fonts){
                        if(font.getFontName(Locale.JAPAN).equalsIgnoreCase(boor)) {
                            gui.setFont(boor);
                            MCMAPI.sendLog("[GUIUtil]フォントをロード");
                            break;
                        }
                    }
                }
                str = br.readLine();
            }

            br.close();
        } catch (IOException ignored) {

        }
        MCMAPI.sendLog("[GUIUtil}コンフィグのロード完了！");
    }


    public Path getApplicationPath(Class<?> cls) throws URISyntaxException {
        ProtectionDomain pd = cls.getProtectionDomain();
        CodeSource cs = pd.getCodeSource();
        URL location = cs.getLocation();
        URI uri = location.toURI();
        Path path = Paths.get(uri);
        return path;
    }


}
