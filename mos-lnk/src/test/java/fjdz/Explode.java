package fjdz;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Explode {
	int x, y;
	private int img = 0;// 0是我的,, 最后一个参数img用来表示哪种敌机，方便爆炸时图片切换
	private WarUI warui;
/**
 * 
 * @param x 爆炸产生的地点X值
 * @param y 爆炸产生的地点y值
 * @param warui  主类的对象
 * @param img  最后一个参数img用来表示哪种敌机，方便爆炸时图片切换
 */
	public Explode(int x, int y, WarUI warui, int img) {
		this.x = x;
		this.y = y;
		this.warui = warui;
		this.img = img;
	}

	ImageIcon diexplode;
/**
 * 将爆炸效果图片画在面板上，由于重绘太快，显示效果不好
 * @param g 画笔
 */
	public void draw(Graphics g) {
			if (img == 1) {
				for (int i = 1; i <= 4; i++) {
					diexplode = new ImageIcon("敌人\\enemy1_down" + i + ".png");
					g.drawImage(diexplode.getImage(), x, y,50,50 , warui);
				}
			}
			if (img == 2) {
					for (int i = 1; i <=4; i++) {
						diexplode = new ImageIcon("敌人\\enemy2_down" + i + ".png");
							g.drawImage(diexplode.getImage(), x, y,50,50 , warui);
					}
			}
			if (img == 3) {
				for (int i = 1; i <=6; i++) {
					diexplode = new ImageIcon("敌人\\enemy2_down" + i + ".png");
						g.drawImage(diexplode.getImage(), x, y,50,50 , warui);
				}
			}
			warui.explodes.remove(this);// 爆炸完成后移除此对象
			return;
		}

	
}
