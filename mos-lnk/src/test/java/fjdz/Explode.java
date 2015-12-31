package fjdz;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Explode {
	int x, y;
	private int img = 0;// 0���ҵ�,, ���һ������img������ʾ���ֵл������㱬ըʱͼƬ�л�
	private WarUI warui;
/**
 * 
 * @param x ��ը�����ĵص�Xֵ
 * @param y ��ը�����ĵص�yֵ
 * @param warui  ����Ķ���
 * @param img  ���һ������img������ʾ���ֵл������㱬ըʱͼƬ�л�
 */
	public Explode(int x, int y, WarUI warui, int img) {
		this.x = x;
		this.y = y;
		this.warui = warui;
		this.img = img;
	}

	ImageIcon diexplode;
/**
 * ����ըЧ��ͼƬ��������ϣ������ػ�̫�죬��ʾЧ������
 * @param g ����
 */
	public void draw(Graphics g) {
			if (img == 1) {
				for (int i = 1; i <= 4; i++) {
					diexplode = new ImageIcon("����\\enemy1_down" + i + ".png");
					g.drawImage(diexplode.getImage(), x, y,50,50 , warui);
				}
			}
			if (img == 2) {
					for (int i = 1; i <=4; i++) {
						diexplode = new ImageIcon("����\\enemy2_down" + i + ".png");
							g.drawImage(diexplode.getImage(), x, y,50,50 , warui);
					}
			}
			if (img == 3) {
				for (int i = 1; i <=6; i++) {
					diexplode = new ImageIcon("����\\enemy2_down" + i + ".png");
						g.drawImage(diexplode.getImage(), x, y,50,50 , warui);
				}
			}
			warui.explodes.remove(this);// ��ը��ɺ��Ƴ��˶���
			return;
		}

	
}
