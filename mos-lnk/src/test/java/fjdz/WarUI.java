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
    public static List<Panle> planes = new ArrayList<Panle>();// ���˷ɻ�����
    public static List<Bullet> bullets = new ArrayList<Bullet>();// �ӵ�����
    public static List<Explode> explodes = new ArrayList<Explode>();// ��ը����
    public static int score = 0;// �Ʒ�

    private Image offScreenImage = null;// ����ʵ��ʹ��˫���壬��һ�����廭��
    public Panle myplane = new Panle(150, 500, warui, 60, true);
    private Random random = new Random();

    public void lauchFrame() {
        setTitle("�ɻ���ս");
        ImageIcon Icon = new ImageIcon("����\\Icon.jpg");
        setIconImage(Icon.getImage());
        setBounds(380, 100, WIDTH, HEIGHT);
        // �����ڲ��࣬�̣����漰��������չ
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        img = new ImageIcon("����\\back.jpg");// �ӿڣ�getImage��������һ��Image����,repaint���������paint����
        img2 = new ImageIcon("����\\back2.jpg");// �ӿڣ�getImage��������һ��Image����,repaint���������paint����
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
     * ������˸��ʹ��˫���� �߳��ػ����Ӿ��ȣ����ܿ����ػ����ٶȡ������ػ����ܽ���ӵ��Զ����е����⣻ ÿ���ػ����repaint����ʱ���ض����ȵ���updateȻ��paint����
     */

    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(WIDTH, HEIGHT);
        }
        // �õ�ͼƬ�ϵĻ���
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.blue);
        gOffScreen.fillRect(0, 0, WIDTH, HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);// ���ڱ���ͼƬ��
        g.drawImage(offScreenImage, 0, 0, null);// ������Ļ��
    }

    /**
     * �����߳������repaint�����������ػ���壬�ɻ����ӵ�����ըЧ�����ȴ����������滭������
     */
    public void paint(Graphics g) {
        // ���ݲ�ͬ�ķ������л�����ͼƬ
        if (score > 5000) {
            g.drawImage(img2.getImage(), 0, 0, warui);
        } else {
            g.drawImage(img.getImage(), 0, 0, warui);
        }
        if (!myplane.isLive()) {
            g.setColor(Color.red);
            Font f = g.getFont();
            g.setFont(new Font("����", Font.BOLD, 60));
            g.drawString("GAME  OVER!!!", 20, 300);
            g.setFont(f);
            g.drawString("���B�����¿�ʼ��C������", 22, 340);
            p = false;

        }
        myplane.draw(g);
        myplane.move();

        // ����
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
        g.drawString("�� �� ��  �� ��" + bullets.size(), 20, 50);
        g.drawString("���ֵл�����" + planes.size(), 20, 70);
        g.drawString("��÷�����" + score, 20, 90);
        g.setColor(Color.BLACK);
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b1 = bullets.get(i);
            b1.draw(g);
            b1.hitplane(planes);
            b1.hitplane(myplane);
            b1.move();
        }
        /**
         * ������ը����
         */
        for (int i = 0; i < explodes.size(); i++) {
            Explode e = explodes.get(i);
            e.draw(g);
        }

    }

    /**
     * // �ڲ��࣬������ķ��ʰ�װ��ķ����������㹫����
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
     * �����¼���ֱ�����������ϣ�Ȼ��Էɻ���X,Y�ٶȽ��п���
     * 
     * @author yan
     *
     */
    class MyKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case 37: // ��
                    myplane.left();
                    break;
                case 38:// ��
                    myplane.up();
                    break;
                case 39:// ��
                    myplane.right();
                    break;
                case 40:// ��
                    myplane.down();
                    break;
                case 65:// A��
                    if (myplane.isLive()) {
                        myplane.fire();
                    }
                    break;
                case 66:// B���������ӵ�
                    p = true;
                    myplane = new Panle(150, 500, warui, 60, true);
                    bullets.removeAll(bullets);
                    break;
                case 67:// C�����¿�ʼ
                    p = true;
                    myplane = new Panle(150, 500, warui, 60, true);
                    bullets.removeAll(bullets);
                    planes.removeAll(planes);
                    break;
                case 80:// P��,��ͣ
                    if (p == false) {
                        p = true;
                    } else {
                        p = false;
                    }
                    break;
            }

        }

        /**
         * ̧���ʱ�����ٶ�Ϊ0
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
