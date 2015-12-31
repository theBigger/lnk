package fjdz;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.ImageIcon;

public class Bullet {
	/**
	 * 这是子弹类，管理所有子弹，速度，大学，方向
	 * 子弹先设定为只有上下方向
	 */
	private static int yspeed = -2;
	public static final int WIDTH = 8;
	public static final int HEIGHT =15 ;
	public int x,y;
	public WarUI warui;
	private int img=0;//0表示我自己的飞机
	public boolean good;
	private boolean live=true;
	/**
	 * 本机的构造方法
	 * @param x2 x位置
	 * @param y2 y位置
	 * @param warui2  主类传过来的对象
	 * @param good 好坏
	 */
	public Bullet(int x,int y,WarUI warui,boolean good){
		this.x=x;
		this.y=y;
		this.good=good;
		this.warui=warui;
	}
	/**
	 * 敌机的构造方法
	 * @param x2 x位置
	 * @param y2 y位置
	 * @param warui2  主类传过来的对象
	 * @param good 好坏
	 * @param img  图片编号，确定后面爆炸时候重画的图片
	 */
	public Bullet(int x2, int y2, WarUI warui2, boolean good, int img) {
		this.x=x2;
		this.y=y2;
		this.good=good;
		this.warui=warui;
		this.img=img;
	
	}
	/**
	 * 画子弹的方法
	 */
	ImageIcon imgp3 = new ImageIcon("英雄\\zidan.jpg");
	ImageIcon dimgp = new ImageIcon("敌人\\bullet2.png");
	public void draw(Graphics g){ 
		if(good==true&&live==true){
		g.drawImage(imgp3.getImage(), x+30-3, y,WIDTH,HEIGHT , warui);
		}
		if(good==false&&live==true){
			g.drawImage(dimgp.getImage(), x+30-3, y+30,WIDTH,HEIGHT , warui);
		}
	} 
	/**
	 * 子弹的移动以及子弹出界问题
	 */
	public void move(){
		if(warui.score>5000){
			 yspeed = -3;
		}else if(warui.score>10000){
			yspeed=-4;
		}
		
		if(good==true){
		y=y+yspeed;
		}else{
			y=y-yspeed;
		}
		if(y<0||y>warui.HEIGHT){
			WarUI.bullets.remove(this);
		}
	}
	
/**
 * 得到外围小方块,(碰撞检测辅助类Rectangle)
 * @return
 */
	public Rectangle getRect(){
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	
	/**
	 * 	// 添加爆炸对象到列表，射击类
	 * @param t
	 * @return
	 */
		public boolean hitplane(Panle p) {
			if (this.live&&this.getRect().intersects(p.getRect()) && p.isLive() == true&&this.good!=p.isGood()) {// 检测是否碰撞,碰撞之后，还得判断坦克是生还是死，否则子弹在那个地方还是会消失	
				Explode e = new Explode(x, y, warui,p.img);// 最后一个参数img用来表示哪种敌机，方便爆炸时图片切换
				warui.explodes.add(e);
				this.live = false;
				p.setLive(false);
				WarUI.planes.remove(p);
				return true;
			}
			return false;
		}
	/**
	 * 重载hitplane方法
	 * 	对一个数组的坏飞机进行射击类添加，实际上还是要调用上面参数为Panle的hitplane方法
	 * @param planes  遍历所有list里面的敌机
	 * @return
 */
		public boolean hitplane(List<Panle> planes) {
			for (int i = 0; i < planes.size(); i++) {
				if (hitplane(planes.get(i))) {
					warui.score+=100;
					return true;
				}
			}
			return false;
		}	
}
