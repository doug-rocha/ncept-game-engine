/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.renderEngine.core;

import com.ncept.engine.Properties;
import com.ncept.engine.inputEngine.Input;
import com.ncept.engine.inputEngine.Mouse;
import com.ncept.engine.utils.Debug;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import javax.swing.JComponent;

import javax.swing.JFrame;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public class Window extends JComponent {

    private static final long serialVersionUID = 1L;

    private static Graphics2D g;

    private static JFrame FRAME;
    private int WIDTH, HEIGHT, BUFFER_SIZE;
    private static String TITLE;

    private static GameManager GM;
    private static Drawer DRAWER;

    private static Window win;

    private static Input INPUT = new Input();
    private static Mouse MOUSE = new Mouse();

    int frames, ticks, time;
    private int lastFrames, lastTicks;

    private boolean decorated;

    private transient Thread loop;
    private static boolean isRunning;

    public Window(String title, int buffer_size, GameManager gm) {
        Window.GM = gm;

        TITLE = title;
        WIDTH = Properties.WIDTH;
        HEIGHT = Properties.HEIGHT;
        BUFFER_SIZE = buffer_size;

        setFocusable(true);

        FRAME = new JFrame(TITLE);
        updateSizes(true);
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setLocation(0, 0);
        //FRAME.add(this);
        FRAME.pack();
        FRAME.setResizable(true);
        FRAME.setLocationRelativeTo(null);
        FRAME.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                onResize(e);
            }
        });
        FRAME.addWindowStateListener((WindowEvent e) -> {
            frameWindowStateChanged(e);
        });
    }

    public Window(String title, int width, int height, int buffer_size, GameManager gm) {
        this(title, buffer_size, gm);
        Properties.WIDTH = Properties.ORIGINAL_WIDTH = WIDTH = width;
        Properties.HEIGHT = Properties.ORIGINAL_HEIGHT = HEIGHT = height;
        updateSizes(true);
    }

    public Window(String title, int width, int height, int buffer_size, boolean undecorated, GameManager gm) {
        this(title, width, height, buffer_size, gm);
        FRAME.dispose();
        FRAME.setUndecorated(undecorated);
        FRAME.setVisible(true);
        decorated = !undecorated;
    }

    public void show() {
        isRunning = true;
        FRAME.setVisible(true);
        this.requestFocus();

        DRAWER = new Drawer(this);
        startInputListeners();
        win = this;
        updateSizes(false);

        gameLoop();
    }

    /**
     * Atualiza o buffer exibido
     */
    public void update() {
        if (!isRunning) {
            Debug.LOG_ERROR("WINDOW NOT INITIALIZED");
        }
        int yPos;
        if (decorated) {
            yPos = (FRAME.getHeight() / 2 - GraphicsCore.BUFFER.getHeight() / 2) < 31 ? 31 : (FRAME.getHeight() / 2) - (GraphicsCore.BUFFER.getHeight() / 2);
        } else {
            yPos = FRAME.getHeight() / 2 - GraphicsCore.BUFFER.getHeight() / 2;
        }
        GraphicsCore.G.drawImage(GraphicsCore.BUFFER,
                FRAME.getWidth() / 2 - GraphicsCore.BUFFER.getWidth() / 2,
                yPos,
                GraphicsCore.BUFFER.getWidth(),
                GraphicsCore.BUFFER.getHeight(),
                null);
    }

    public void clear(Color clear_color) {
        if (!isRunning) {
            Debug.LOG_ERROR("WINDOW NOT INITIALIZED");
        }
        DRAWER.clear(clear_color);
    }

    public void close() {
        if (Properties.DEBUG_MODE) {
            Debug.LOG("WINDOW >> CLOSING APPLICATION");
        }
        FRAME.dispose();
        System.exit(0);
    }

    public void setFullscreen(boolean full_screen) {
        if (full_screen) {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice screen = ge.getDefaultScreenDevice();
            if (screen.isFullScreenSupported()) {
                screen.setFullScreenWindow(FRAME);
                FRAME.requestFocus();
            } else {
                Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
                FRAME.setSize((int) screen_size.getWidth(), (int) screen_size.getHeight());
                FRAME.setAlwaysOnTop(true);
            }
        }
        updateSizes(false);
    }

    public boolean isRunning() {
        return isRunning;
    }

    private void gameLoop() {
        loop = new Thread() {
            public void run() {
                double last_time = System.nanoTime();
                double delta = 0, delta_fps = 0;

                double ns, ns_fps;

                double start = System.currentTimeMillis();
                int next = 1;

                while (isRunning()) {
                    ns = 1e9 / Properties.UPDATE_SPEED;
                    ns_fps = 1e9 / Properties.FRAMES_PS;
                    double now_time = System.nanoTime();
                    double now = (System.currentTimeMillis() - start) / 1000;

                    delta += (now_time - last_time) / ns;
                    Properties.DELTA_TIME = (now_time - last_time) / 1000.0;
                    delta_fps += (now_time - last_time) / ns_fps;
                    last_time = now_time;
                    while (delta >= 1) {
                        GM.update();
                        delta--;
                    }
                    while (delta_fps >= 1) {
                        GM.render();
                        delta_fps--;
                    }
                    if (now >= next) {
                        next++;
                        time++;
                        lastFrames = frames;
                        lastTicks = ticks;
                        Debug.LOG("FPS: " + lastFrames + ", TICKS: " + lastTicks);
                        frames = 0;
                        ticks = 0;
                    }
                }
            }
        };
        loop.start();
    }

    public void setTps(int ticks_ps) {
        Properties.UPDATE_SPEED = ticks_ps;
    }

    public void setFps(int frames_ps) {
        Properties.FRAMES_PS = frames_ps;
    }

    public static Drawer getDrawer() {
        return DRAWER;
    }

    private void startInputListeners() {
        FRAME.addKeyListener(INPUT);
        FRAME.addMouseListener(MOUSE);
        FRAME.addMouseMotionListener(MOUSE);
    }

    public static Input getInput() {
        return INPUT;
    }

    public static Mouse getMouse() {
        return MOUSE;
    }

    public static Window getWindow() {
        return win;
    }

    private void updateSizes(boolean frame_too) {
        setPreferredSize(new Dimension(Properties.WIDTH, Properties.HEIGHT));
        setSize(new Dimension(Properties.WIDTH, Properties.HEIGHT));
        if (frame_too) {
            FRAME.setVisible(false);
            FRAME.setSize(Properties.WIDTH, Properties.HEIGHT);
            FRAME.setLocationRelativeTo(null);
            FRAME.setVisible(true);
            Debug.LOG("Size Frame " + FRAME.getWidth() + " | " + FRAME.getHeight());

        }
        GraphicsCore.G = (Graphics2D) FRAME.getGraphics();
        GraphicsCore.G.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        updateDrawer();
    }

    public void setSizes(int width, int height) {
        Properties.WIDTH = width;
        Properties.HEIGHT = height;
        updateSizes(true);
    }

    public void setSizes(int width, int height, boolean newProportion) {
        if (newProportion) {
            Properties.ORIGINAL_WIDTH = width;
            Properties.ORIGINAL_HEIGHT = height;
        }
        setSizes(width, height);
    }

    public JFrame getFrame() {
        return FRAME;
    }

    public static Dimension getScreenDimension() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    public void setIcon(Image img) {
        FRAME.setIconImage(img);
    }

    public void setDecorated(boolean decorated) {
        this.decorated = decorated;
    }

    public boolean isDecorated() {
        return decorated;
    }

    public Dimension getSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    private void onResize(ComponentEvent e) {
        procSizes();
    }

    private void frameWindowStateChanged(WindowEvent e) {
        procSizes();
    }

    private void procSizes() {
        if (FRAME.isVisible()) {
            Properties.WIDTH = WIDTH = FRAME.getWidth();
            Properties.HEIGHT = HEIGHT = FRAME.getHeight();
            updateDrawer();
        }
    }

    private void updateDrawer() {
        DRAWER = new Drawer(this);
        GraphicsCore.calcMods(FRAME.getWidth(), FRAME.getHeight());
        Debug.LOG(GraphicsCore.MODSIZE_X + " <- modsize -> " + GraphicsCore.MODSIZE_Y);
    }
}
