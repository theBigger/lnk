package fjdz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

public class WarUI extends Frame {
    private static final long serialVersionUID = -1857763531643677817L;
    public static final int WIDTH = 410;
    public static final int HEIGHT = 600;
    public ImageIcon img;
    public ImageIcon img2;
    public static WarUI warui;
    public boolean p = true;
    public static List<Panle> planes = new ArrayList<Panle>();// 敌人飞机容器
    public static List<Bullet> bullets = new ArrayList<Bullet>();// 子弹容器
    public static List<Explode> explodes = new ArrayList<Explode>();// 爆炸对象
    public static int score = 0;// 计分

    private Image offScreenImage = null;// 用来实现使用双缓冲，画一个缓冲画布
    public Panle myplane = new Panle(150, 500, warui, 60, true);
    private Random random = new Random();

    public void lauchFrame() {
        setTitle("飞机大战");
        ImageIcon Icon = new ImageIcon("背景\\Icon.jpg");
        setIconImage(Icon.getImage());
        setBounds(380, 100, WIDTH, HEIGHT);
        // 匿名内部类，短，不涉及将来的扩展
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        img = new ImageIcon("背景\\back.jpg");// 接口，getImage方法返回一个Image对象,repaint方法会调用paint方法
        img2 = new ImageIcon("背景\\back2.jpg");// 接口，getImage方法返回一个Image对象,repaint方法会调用paint方法
        setResizable(false);
        setVisible(true);
        MyKeyListener mkl = new MyKeyListener();
        this.addKeyListener(mkl);
        new Thread(new PaintThread()).start();
    }

    public static void main(String[] args) {
        warui = new WarUI();
        warui.lauchFrame();

    }

    /**
     * 消除闪烁，使用双缓冲 线程重画更加均匀，更能控制重化的速度。按键重画不能解决子弹自动飞行的问题； 每次重绘调用repaint方法时，必定会先调用update然后paint方法
     */

    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(WIDTH, HEIGHT);
        }
        // 拿到图片上的画笔
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.blue);
        gOffScreen.fillRect(0, 0, WIDTH, HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);// 画在背后图片上
        g.drawImage(offScreenImage, 0, 0, null);// 画在屏幕上
    }

    /**
     * 根据线程里面的repaint方法，不断重画面板，飞机，子弹，爆炸效果，等待都是在里面画出来的
     */
    public void paint(Graphics g) {
        // 根据不同的分数层切换背景图片
        if (score > 5000) {
            g.drawImage(img2.getImage(), 0, 0, warui);
        } else {
            g.drawImage(img.getImage(), 0, 0, warui);
        }
        if (!myplane.isLive()) {
            g.setColor(Color.red);
            Font f = g.getFont();
            g.setFont(new Font("宋体", Font.BOLD, 60));
            g.drawString("GAME  OVER!!!", 20, 300);
            g.setFont(f);
            g.drawString("复活按B，重新开始按C！！！", 22, 340);
            p = false;

        }
        myplane.draw(g);
        myplane.move();

        // 敌人
        if (planes.size() < 3) {
            for (int j = 0; j < 3; j++) {
                Panle p = new Panle(false, warui);
                planes.add(p);
            }
        }
        if (planes.size() != 0) {
            for (int i = 0; i < planes.size(); i++) {
                Panle diren = planes.get(i);
                diren.draw(g);
                diren.dmove();
                int r1 = random.nextInt(200);
                if (r1 == 20)
                    diren.dfire();
            }
        }
        g.setColor(Color.BLUE);
        g.drawString("子 弹 个  数 ：" + bullets.size(), 20, 50);
        g.drawString("入侵敌机数：" + planes.size(), 20, 70);
        g.drawString("获得分数：" + score, 20, 90);
        g.setColor(Color.BLACK);
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b1 = bullets.get(i);
            b1.draw(g);
            b1.hitplane(planes);
            b1.hitplane(myplane);
            b1.move();
        }
        /**
         * 创建爆炸对象
         */
        for (int i = 0; i < explodes.size(); i++) {
            Explode e = explodes.get(i);
            e.draw(g);
        }

    }

    /**
     * // 内部类，，方便的访问包装类的方法，不方便公开，
     * 
     * @author yan
     *
     */
    private class PaintThread implements Runnable {
        public void run() {
            while (true) {
                if (p == true) {
                    repaint();
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 键盘事件，直接添加在面板上，然后对飞机的X,Y速度进行控制
     * 
     * @author yan
     *
     */
    class MyKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case 37: // 左
                    myplane.left();
                    break;
                case 38:// 上
                    myplane.up();
                    break;
                case 39:// 右
                    myplane.right();
                    break;
                case 40:// 下
                    myplane.down();
                    break;
                case 65:// A键
                    if (myplane.isLive()) {
                        myplane.fire();
                    }
                    break;
                case 66:// B建复活，清空子弹
                    p = true;
                    myplane = new Panle(150, 500, warui, 60, true);
                    bullets.removeAll(bullets);
                    break;
                case 67:// C建重新开始
                    p = true;
                    myplane = new Panle(150, 500, warui, 60, true);
                    bullets.removeAll(bullets);
                    planes.removeAll(planes);
                    break;
                case 80:// P键,暂停
                    if (p == false) {
                        p = true;
                    } else {
                        p = false;
                    }
                    break;
            }

        }

        /**
         * 抬起键时，把速度为0
         */
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == 37 || e.getKeyCode() == 39) {
                myplane.initXc();
            } else if (e.getKeyCode() == 38 || e.getKeyCode() == 40) {
                myplane.initYc();
            }

        }
    }

}
