package fjdz;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.ImageIcon;

/**
 * �ɻ��࣬�õĻ��Ķ���һ��ֻ�ǵ��õķ�����ͬ
 * @author yan
 *
 */
public class Panle {
	public  int xspeed =0;
	public  int yspeed = 0;
	public double dspeed=0;
	public int x, y;
	public  int size = 60;
	private WarUI warui;
	public int img=1;//�л�ͼƬ���
	
	private  boolean good=false;
	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	private  boolean live=true;
	
	private Random random = new Random();
	private ImageIcon diren;//���˷ɻ�
	private int[] addr={30,60,80,100,130,150,180,200,230,260,290,320,350};//���ɻ����ֵ�xλ��
	private int[] xspeeds={0,0,-1,0,1,0,1,0,-1,0};
	
/**
 * 
 * @param x ��������xλ��
 * @param y ��������xλ��
 * @param warui
 * @param size ������С
 * @param good �û�
 */
	public Panle(int x, int y, WarUI warui,int size,boolean good) {
		this.x = x;
		this.y = y;
		this.warui = warui;
		this.good=good;
		this.size=size;
		
	}
	/**
	 * ���˳�ʼλ�������
	 * @param good �û�
	 * @param warui �������
	 */
	public Panle(boolean good,WarUI warui) {
		this.good=false;
		this.warui = warui;
		img=random.nextInt(3)+1;
		
		//��÷ɻ���y�����ٶ�
		dspeed=1;	
		
		this.x=addr[random.nextInt(13)];
		this.y=random.nextInt(50)+10;
		//ȷ���ɻ�x������ٶ�
		this.xspeed=xspeeds[random.nextInt(10)];		
	}
	

	/**
	 * // ��ϼ����¼����ƶ�����
	 */
	public void left() {
		xspeed=-5;
	}

	public void right() {
		xspeed=+5;
	}

	public void up() {
		yspeed=-5;
	}

	public void down() {
		yspeed=+5;
	}

	public void initXc() {
		xspeed = 0;
	}

	public void initYc() {
		yspeed = 0;
	}


	
	/**
	 * �ҵĺ÷ɻ��ƶ�����
	 */
	public void move() {
			this.x += xspeed;
			this.y += yspeed;
			//��������
			if(y<=30)y=30;
			if(x<=0){x=WarUI.WIDTH-30;}
			if(x>=WarUI.WIDTH){x=20;}
			//if(x+size>=WarUI.WIDTH){x=WarUI.WIDTH-size;}
			if(y+size>=WarUI.HEIGHT){y=WarUI.HEIGHT-size;}

	}
	//���ɻ�move����
	public void dmove(){
		this.y+=dspeed;
		this.x+=xspeed;
		//��������
		if(y<=30)y=30;
		if(x<=0||x+size>=WarUI.WIDTH){
			xspeed=-xspeed;
			}
		if(y+size>=WarUI.HEIGHT){//��������±߽磬�򽫻��ɻ��Ƴ�
			warui.planes.remove(this);
		}

	}
	ImageIcon mimg1 = new ImageIcon("Ӣ��\\hero1.png");
	ImageIcon mimg2 = new ImageIcon("Ӣ��\\hero2.png");
	/**
	 * �����ɻ�
	 * @param g
	 */
	public void draw(Graphics g) {
		if(good==true&&live==true){//�Լ�
			int i = random.nextInt(100);
			if (i >50) {
				g.drawImage(mimg1.getImage(), x, y, size, size, warui);
			} else {
				g.drawImage(mimg2.getImage(), x, y, size, size, warui);
			
			}
		}
		if(good==false&&live==true){
			 diren = new ImageIcon("����\\enemy"+img+".png");
			g.drawImage(diren.getImage(),x,y,size ,size,warui);
			
		}
		
		
	}

	
	//���𷽷�
	public void fire(){
		Bullet b=new Bullet(x,y,warui,good);
		WarUI.bullets.add(b);
		
	}
	// ���һ������img������ʾ���ֵл������㱬ըʱͼƬ�л�
	 
	public void dfire(){
		Bullet b=new Bullet(x,y,warui,false,this.img);
		WarUI.bullets.add(b);
		
	}
	
	
////�õ���ΧС����,(��ײ��⸨����Rectangle)
	public Rectangle getRect(){
		return new Rectangle(x-30,y-30,size,size);
	}

}
