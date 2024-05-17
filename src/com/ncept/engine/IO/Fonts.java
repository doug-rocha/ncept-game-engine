package com.ncept.engine.IO;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Douglas Rocha de Oliveira
 */
public class Fonts {
    public static Font ARIAL;
    public static Font TIMES;
    public static Font TIMESBD;
    public static Font UBUNTU;
    public static Font LIBEL;
    public static Font RUFA;
    public static Font SQUARED;
    
    public static Font DIALOG;

    public static void loadFonts() {
        try {
            DIALOG = new Font("dialog", Font.PLAIN, 12);
            boolean canContinue = true;
            File folder = new File("fonts");
            if ((!folder.exists()) || (folder.listFiles().length == 0)) {
                folder = new File("res/fonts");
                if ((!folder.exists()) || (folder.listFiles().length == 0)) {
                    canContinue = false;
                }
            }
            if (canContinue) {
                File tmpFont = new File(String.format("%s/arial.ttf", folder.getPath()));
                ARIAL = Font.createFont(Font.TRUETYPE_FONT, tmpFont);

                tmpFont = new File(String.format("%s/times.ttf", folder.getPath()));
                TIMES = Font.createFont(Font.TRUETYPE_FONT, tmpFont);

                tmpFont = new File(String.format("%s/timesbd.ttf", folder.getPath()));
                TIMESBD = Font.createFont(Font.TRUETYPE_FONT, tmpFont);

                tmpFont = new File(String.format("%s/ubuntu-c.ttf", folder.getPath()));
                UBUNTU = Font.createFont(Font.TRUETYPE_FONT, tmpFont);

                tmpFont = new File(String.format("%s/libel-suit-rg.ttf", folder.getPath()));
                LIBEL = Font.createFont(Font.TRUETYPE_FONT, tmpFont);

                tmpFont = new File(String.format("%s/RUFA.ttf", folder.getPath()));
                RUFA = Font.createFont(Font.TRUETYPE_FONT, tmpFont);

                tmpFont = new File(String.format("%s/squared.ttf", folder.getPath()));
                SQUARED = Font.createFont(Font.TRUETYPE_FONT, tmpFont);

            } else {
                ARIAL = TIMES = TIMESBD = UBUNTU = LIBEL = RUFA = SQUARED = DIALOG;
            }
        } catch (FontFormatException | IOException ex) {
        }

    }
}
