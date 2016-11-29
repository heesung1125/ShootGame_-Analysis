import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;
import java.util.Vector;


public class W_Shooting {

	public static void main(String[] args){
		System.out.println("ストライクウィッチ-ズ Start!");
		W_Shooting_frame wsf=new W_Shooting_frame();
	}

}

class W_Shooting_frame extends Frame implements KeyListener, Runnable
{


	public final static int UP_PRESSED		=0x001;
	public final static int DOWN_PRESSED	=0x002;
	public final static int LEFT_PRESSED	=0x004;
	public final static int RIGHT_PRESSED	=0x008;
	public final static int FIRE_PRESSED	=0x010;
	
	/**
	 * @uml.property  name="gamescreen"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="main:GameScreen"
	 */
	GameScreen gamescreen;

	/**
	 * @uml.property  name="mainwork"
	 */
	Thread mainwork;
	/**
	 * @uml.property  name="roof"
	 */
	boolean roof=true;
	/**
	 * @uml.property  name="rnd"
	 */
	Random rnd = new Random();	 


	/**
	 * @uml.property  name="status"
	 */
	int status;
	/**
	 * @uml.property  name="cnt"
	 */
	int cnt;
	/**
	 * @uml.property  name="delay"
	 */
	int delay;
	/**
	 * @uml.property  name="pretime"
	 */
	long pretime;
	/**
	 * @uml.property  name="keybuff"
	 */
	int keybuff;


	
	/**
	 * @uml.property  name="score"
	 */
	int score;
	/**
	 * @uml.property  name="mylife"
	 */
	int mylife;
	/**
	 * @uml.property  name="gamecnt"
	 */
	int gamecnt;
	/**
	 * @uml.property  name="scrspeed"
	 */
	int scrspeed=16;
	/**
	 * @uml.property  name="level"
	 */
	int level;

	/**
	 * @uml.property  name="myx"
	 */
	int myx;
	/**
	 * @uml.property  name="myy"
	 */
	int myy;
	/**
	 * @uml.property  name="myspeed"
	 */
	int myspeed;
	/**
	 * @uml.property  name="mydegree"
	 */
	int mydegree;

	/**
	 * @uml.property  name="mywidth"
	 */
	int mywidth;
	/**
	 * @uml.property  name="myheight"
	 */
	int myheight;
	/**
	 * @uml.property  name="mymode"
	 */
	int mymode=1;
	/**
	 * @uml.property  name="myimg"
	 */
	int myimg;
	/**
	 * @uml.property  name="mycnt"
	 */
	int mycnt;
	/**
	 * @uml.property  name="myshoot"
	 */
	boolean myshoot=false;
	/**
	 * @uml.property  name="myshield"
	 */
	int myshield;
	/**
	 * @uml.property  name="my_inv"
	 */
	boolean my_inv=false;

	/**
	 * @uml.property  name="gScreenWidth"
	 */
	int gScreenWidth=640;
	/**
	 * @uml.property  name="gScreenHeight"
	 */
	int gScreenHeight=480;

	/**
	 * @uml.property  name="bullets"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="Bullet"
	 */
	Vector bullets=new Vector();
	/**
	 * @uml.property  name="enemies"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="main:Enemy"
	 */
	Vector enemies=new Vector();
	/**
	 * @uml.property  name="effects"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="Effect"
	 */
	Vector effects=new Vector();
	/**
	 * @uml.property  name="items"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="Item"
	 */
	Vector items=new Vector();
	


	
	W_Shooting_frame(){
		
		setIconImage(makeImage("./rsc/icon.png"));
		setBackground(new Color(0xffffff));
		setTitle("ストライクウィッチ-ズ Fan Game");
		setLayout(null);
		setBounds(100,100,gScreenWidth,gScreenHeight);
		setResizable(false);
		setVisible(true);
		
		addKeyListener(this);
		addWindowListener(new MyWindowAdapter());

		gamescreen=new GameScreen(this);
		gamescreen.setBounds(0,0,gScreenWidth,gScreenHeight); 
		add(gamescreen);

		systeminit();
		initialize(); 
	}

	public void systeminit(){

		status=0;
		cnt=0;
		delay=17;
		keybuff=0;

		mainwork=new Thread(this); 
		mainwork.start();	
	}
	public void initialize(){

		Init_TITLE(); 
		gamescreen.repaint(); 
	}

	
	public void run(){
		try 
		{
			while(roof){ 
				pretime=System.currentTimeMillis(); 

				gamescreen.repaint();
				process();
				keyprocess();

				if(System.currentTimeMillis()-pretime<delay) Thread.sleep(delay-System.currentTimeMillis()+pretime);


				if(status!=4) cnt++;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void keyPressed(KeyEvent e) {
		
		if(status==2){
			switch(e.getKeyCode()){
			case KeyEvent.VK_SPACE:
				keybuff|=FIRE_PRESSED;
				break;
			case KeyEvent.VK_LEFT:
				keybuff|=LEFT_PRESSED;
				break;
			case KeyEvent.VK_UP:
				keybuff|=UP_PRESSED;
				break;
			case KeyEvent.VK_RIGHT:
				keybuff|=RIGHT_PRESSED;
				break;
			case KeyEvent.VK_DOWN:
				keybuff|=DOWN_PRESSED;
				break;
			case KeyEvent.VK_1:
				if(myspeed>1) myspeed--;
				break;
			case KeyEvent.VK_2:
				if(myspeed<9) myspeed++;
				break;
			case KeyEvent.VK_3:
				if(status==2) status=4;
				break;

			default:
				break;
			}
		} else if(status!=2) keybuff=e.getKeyCode();
	}
	public void keyReleased(KeyEvent e) {
		
		
			switch(e.getKeyCode()){
			case KeyEvent.VK_SPACE:
				keybuff&=~FIRE_PRESSED;
				myshoot=true;
				break;
			case KeyEvent.VK_LEFT:
				keybuff&=~LEFT_PRESSED;
				break;
			case KeyEvent.VK_UP:
				keybuff&=~UP_PRESSED;
				break;
			case KeyEvent.VK_RIGHT:
				keybuff&=~RIGHT_PRESSED;
				break;
			case KeyEvent.VK_DOWN:
				keybuff&=~DOWN_PRESSED;
				break;
			}
		
		
		
	}
	public void keyTyped(KeyEvent e) {
	}

	
	private void process(){
		switch(status){
		case 0:
			break;
		case 1:
			process_MY();
			if(mymode==2) status=2;
			break;
		case 2:
			process_MY();
			process_ENEMY();
			process_BULLET();
			process_EFFECT();
			process_GAMEFLOW();
			process_ITEM();
			break;
		case 3:
			process_ENEMY();
			process_BULLET();
			process_GAMEFLOW();
			break;
		case 4:
			break;
		default:
			break;
		}
		if(status!=4) gamecnt++;
	}

	
	
	private void keyprocess(){
		switch(status){
		case 0:
			if(keybuff==KeyEvent.VK_SPACE) {
				Init_GAME();
				Init_MY();
				status=1;
			}
			break;
		case 2:
			if(mymode==2||mymode==0){
				switch(keybuff){
				case 0:
					mydegree=-1;
					myimg=0;
					break;
				case FIRE_PRESSED:
					mydegree=-1;
					myimg=6;
					break;
				case UP_PRESSED:
					mydegree=0;
					myimg=2;
					break;
				case UP_PRESSED|FIRE_PRESSED:
					mydegree=0;
					myimg=6;
					break;
				case LEFT_PRESSED:
					mydegree=90;
					myimg=4;
					break;
				case LEFT_PRESSED|FIRE_PRESSED:
					mydegree=90;
					myimg=6;
					break;
				case RIGHT_PRESSED:
					mydegree=270;
					myimg=2;
					break;
				case RIGHT_PRESSED|FIRE_PRESSED:
					mydegree=270;
					myimg=6;
					break;
				case UP_PRESSED|LEFT_PRESSED:
					mydegree=45;
					myimg=4;
					break;
				case UP_PRESSED|LEFT_PRESSED|FIRE_PRESSED:
					mydegree=45;
					myimg=6;
					break;
				case UP_PRESSED|RIGHT_PRESSED:
					mydegree=315;
					myimg=2;
					break;
				case UP_PRESSED|RIGHT_PRESSED|FIRE_PRESSED:
					mydegree=315;
					myimg=6;
					break;
				case DOWN_PRESSED:
					mydegree=180;
					myimg=2;
					break;
				case DOWN_PRESSED|FIRE_PRESSED:
					mydegree=180;
					myimg=6;
					break;
				case DOWN_PRESSED|LEFT_PRESSED:
					mydegree=135;
					myimg=4;
					break;
				case DOWN_PRESSED|LEFT_PRESSED|FIRE_PRESSED:
					mydegree=135;
					myimg=6;
					break;
				case DOWN_PRESSED|RIGHT_PRESSED:
					mydegree=225;
					myimg=2;
					break;
				case DOWN_PRESSED|RIGHT_PRESSED|FIRE_PRESSED:
					mydegree=225;
					myimg=6;
					break;
				default:
					
					keybuff=0;
					mydegree=-1;
					myimg=0;
					break;
				}
			}
			break;
		case 3:
			if(gamecnt++>=200&&keybuff==KeyEvent.VK_SPACE){
				Init_TITLE();
				status=0;
				keybuff=0;
			}
			break;
		case 4:
			if(gamecnt++>=200&&keybuff==KeyEvent.VK_3) status=2;
			break;
		default:
			break;
		}
	}


	
	public void Init_TITLE(){
		


		gamescreen.title=makeImage("./rsc/title.png");	
		gamescreen.title_key=makeImage("./rsc/pushspace.png"); 

		
		
	}
	public void Init_GAME(){
		int i;


		gamescreen.bg=makeImage("./rsc/구름.JPG");
		gamescreen.bgFlip=makeImage("./rsc/cloud_flip.jpg");
		gamescreen.bg_f=makeImage("./rsc/bg_f.png");
		for(i=0;i<4;i++) gamescreen.cloud[i]=makeImage("./rsc/cloud"+i+".png");
		for(i=0;i<4;i++) gamescreen.bullet[i]=makeImage("./rsc/game/bullet_"+i+".png");
		gamescreen.enemy[0]=makeImage("./rsc/game/enemy0.png");
		gamescreen.explo=makeImage("./rsc/game/explode.png");
		gamescreen.item[0]=makeImage("./rsc/game/item0.png");
		gamescreen.item[1]=makeImage("./rsc/game/item1.png");
		gamescreen.item[2]=makeImage("./rsc/game/item2.png");
		gamescreen._start=makeImage("./rsc/game/start.png");
		gamescreen._over=makeImage("./rsc/game/gameover.png");
		gamescreen.shield=makeImage("./rsc/game/shield.png");
		gamescreen.enemy[1]=makeImage("./rsc/game/enemy1.png");
		gamescreen.enemy[2]=makeImage("./rsc/game/enemy2.png");
		
		gamescreen.num=makeImage("./rsc/gnum.png");
		gamescreen.uiUp=makeImage("./rsc/ui_up.png");
		
		keybuff=0;
		bullets.clear();
		enemies.clear();
		effects.clear();
		items.clear();
		level=0;
		gamecnt=0;
	}
	public void Init_MY(){
		for(int i=0;i<9;i++){
			if(i<10)
				gamescreen.chr[i]=makeImage("./rsc/player/my_0"+i+".png");
			else
				gamescreen.chr[i]=makeImage("./rsc/player/my_"+i+".png");
		}
		Init_MYDATA();
	}
	public void Init_MYDATA(){
		score=0;
		myx=0;
		myy=23000;
		myspeed=4;
		mydegree=-1;
		
		mymode=1;
		myimg=2;
		mycnt=0;
		mylife=3;
		keybuff=0;
	}
	public void process_MY(){
		Bullet shoot;
		switch(mymode){
		case 1:
			myx+=200;
			if(myx>20000) mymode=2;
			break;
		case 0:
			if(mycnt--==0) {
				mymode=2;
				myimg=0;
			}
		case 2:
			if(mydegree!=-1&&my_inv) mydegree=(mydegree+180)%360;
			if(mydegree>-1) {
				myx-=(myspeed*Math.sin(Math.toRadians(mydegree))*100);
				myy-=(myspeed*Math.cos(Math.toRadians(mydegree))*100);
			}
			if(myimg==6) {
				myx-=20;
				if(cnt%4==0||myshoot){
					myshoot=false;
					shoot=new Bullet(myx+2500, myy+1500, 0, 0, RAND(245,265), 8);
					bullets.add(shoot);
					shoot=new Bullet(myx+2500, myy+1500, 0, 0, RAND(268,272), 9);
					bullets.add(shoot);
					shoot=new Bullet(myx+2500, myy+1500, 0, 0, RAND(275,295), 8);
					bullets.add(shoot);
				}
				
			}
			break;
		case 3:
			
			myimg=8;
			if(mycnt--==0) {
				mymode=0;
				mycnt=50;
			}
			break;
		}
		if(myx<2000) myx=2000;
		if(myx>62000) myx=62000;
		if(myy<3000) myy=3000;
		if(myy>45000) myy=45000;
	}
	public void process_ENEMY(){
		int i;
		Enemy buff;
		for(i=0;i<enemies.size();i++){
			buff=(Enemy)(enemies.elementAt(i));
			if(!buff.move()) enemies.remove(i);
		}
	}
	public void process_BULLET(){
		Bullet buff;
		Enemy ebuff;
		Effect expl;
		int i,j, dist;
		for(i=0;i<bullets.size();i++){
			buff=(Bullet)(bullets.elementAt(i));
			buff.move();
			if(buff.dis.x<10||buff.dis.x>gScreenWidth+10||buff.dis.y<10||buff.dis.y>gScreenHeight+10) {
				bullets.remove(i);
				continue;
			}
			if(buff.from==0) {
				for(j=0;j<enemies.size();j++){
					ebuff=(Enemy)(enemies.elementAt(j));
					dist=GetDistance(buff.dis.x,buff.dis.y, ebuff.dis.x,ebuff.dis.y);
					
					if(dist<ebuff.hitrange) {
						if(ebuff.life--<=0){
							if(ebuff.kind==1){
								if(gamecnt<2100) gamecnt=2100;
							}
							enemies.remove(j);
							expl=new Effect(0, ebuff.pos.x, buff.pos.y, 0);
							effects.add(expl);
							
							int itemKind=RAND(1,100);
							Item tem;
							if(itemKind<=70)
								tem=new Item(ebuff.pos.x, buff.pos.y,0);
							else if(itemKind<=95)
								tem=new Item(ebuff.pos.x, buff.pos.y,2);
							else
								tem=new Item(ebuff.pos.x, buff.pos.y,1);
							items.add(tem);
						}
						
						expl=new Effect(0, buff.pos.x, buff.pos.y, 0);
						effects.add(expl);
						score++;
						bullets.remove(i);
						break;
					}
				}
			} else {
				if(mymode!=2) continue;
				dist=GetDistance(myx/100,myy/100, buff.dis.x,buff.dis.y);
				if(dist<500) {
					if(myshield==0){
						mymode=3;
						mycnt=30;
						bullets.remove(i);
						expl=new Effect(0, myx-2000, myy, 0);
						effects.add(expl);
						if(--mylife<=0) {
							status=3;
							gamecnt=0;
						}
					} else {
						myshield--;
						bullets.remove(i);
					}
				}
			}
		}
	}
	public void process_EFFECT(){
		int i;
		Effect buff;
		for(i=0;i<effects.size();i++){
			buff=(Effect)(effects.elementAt(i));
			if(cnt%3==0) buff.cnt--;
			if(buff.cnt==0) effects.remove(i);
		}
	}
	public void process_GAMEFLOW(){
		int control=0;
		int newy=0, mode=0;
		
		if(gamescreen.boss){
			
			if(level>1){
				
				
				
				
				if(800<gamecnt&&gamecnt<1000){
					if(gamecnt%60==0) {
						newy=RAND(30,gScreenHeight-30)*100;
						if(newy<24000) mode=0; else mode=1;
						Enemy en=new Enemy(this, 0, gScreenWidth*100, newy, 0,mode);
						enemies.add(en);
					}
				}else
				if(1600<gamecnt&&gamecnt<2200){
					if(gamecnt%30==0) {
						Enemy en;
						newy=RAND(30,gScreenHeight-30)*100;
						if(newy<24000) mode=0; else mode=1;
						if(level>1&&RAND(1,100)<level*10)
							en=new Enemy(this, 2, gScreenWidth*100, newy, 2,0);
						else
							en=new Enemy(this, 0, gScreenWidth*100, newy, 0,mode);
						enemies.add(en);
					}
				}
			}
			if(gamecnt>2210){
				gamescreen.boss=false;
				gamecnt=0;
				System.out.println("보스 타임아웃");
			}
		}else{
			if(gamecnt<500) control=1;
			else if(gamecnt<1000) control=2;
			else if(gamecnt<1300) control=0;
			else if(gamecnt<1700) control=1;
			else if(gamecnt<2000) control=2;
			else if(gamecnt<2400) control=3;
			else {
				
				System.out.println("보스 등장");
				gamescreen.boss=true;
				Enemy en=new Enemy(this, 1, gScreenWidth*100, 24000, 1, 0);
				enemies.add(en);
				gamecnt=0;
				level++;
			}
			if(control>0) {
				newy=RAND(30,gScreenHeight-30)*100;
				if(newy<24000) mode=0; else mode=1;
			}
			Enemy en;
			switch(control){
			case 1:
				if(gamecnt%90==0) {
					if(RAND(1,3)!=3&&level>0)
						en=new Enemy(this, 2, gScreenWidth*100, newy, 2,mode);
					else
						en=new Enemy(this, 0, gScreenWidth*100, newy, 0,mode);
					enemies.add(en);
				}
				break;
			case 2:
				if(gamecnt%50==0) {
					if(RAND(1,3)!=3&&level>0)
						en=new Enemy(this, 2, gScreenWidth*100, newy, 2,mode);
					else
						en=new Enemy(this, 0, gScreenWidth*100, newy, 0,mode);
					enemies.add(en);
				}
				break;
			case 3:
				if(gamecnt%20==0) {
					if(RAND(1,3)!=3&&level>0)
						en=new Enemy(this, 2, gScreenWidth*100, newy, 2,mode);
					else
						en=new Enemy(this, 0, gScreenWidth*100, newy, 0,mode);
					enemies.add(en);
				}
				break;
			}
		}
	}
	public void process_ITEM(){
		int i, dist;
		Item buff;
		for(i=0;i<items.size();i++){
			buff=(Item)(items.elementAt(i));
			dist=GetDistance(myx/100,myy/100, buff.dis.x,buff.dis.y);
			if(dist<1000) {
				switch(buff.kind){
				case 0:
					score+=100;
					break;
				case 1:
					myshield=5;
					break;
				case 2:
					
					
					
					
					int j=enemies.size();
					for(int k=0;k<j;k++){
						Enemy ebuff=(Enemy)(enemies.elementAt(k));
						if(ebuff==null) continue;
						if(ebuff.kind==1) {
							score+=300;
							ebuff.life=ebuff.life/2;
							if(ebuff.life<=1) ebuff.life=1;
							continue;
						}
						Effect expl=new Effect(0, ebuff.pos.x,ebuff.pos.y, 0);
						effects.add(expl);
						ebuff.pos.x=-10000;
						score+=50;
						
					}
					
					
					j=bullets.size();
					for(int k=0;k<j;k++){
						Bullet bbuff=(Bullet)(bullets.elementAt(k));
						if(bbuff.from!=0) {
							bbuff.pos.x=-10000;
							score++;
						}
						
					}
					break;
				}
				items.remove(i);
			} else
				if(buff.move()) items.remove(i);
		}
	}

	public Image makeImage(String furl){
		Image img;
		Toolkit tk=Toolkit.getDefaultToolkit();
		img=tk.getImage(furl);
		try {
			
			MediaTracker mt = new MediaTracker(this);
			mt.addImage(img, 0);
			mt.waitForID(0);
			
		} catch (Exception ee) {
			ee.printStackTrace();
			return null;
		}	
		return img;
	}
	public int GetDistance(int x1,int y1,int x2,int y2){
		return Math.abs((y2-y1)*(y2-y1)+(x2-x1)*(x2-x1));
	}
	public int RAND(int startnum, int endnum) 
	{
		int a, b;
		if(startnum<endnum)
			b = endnum - startnum; 
		else
			b = startnum - endnum;
		a = Math.abs(rnd.nextInt()%(b+1));
		return (a+startnum);
	}
	int getAngle(int sx, int sy, int dx, int dy){
		int vx=dx-sx;
		int vy=dy-sy;
		double rad=Math.atan2(vx,vy);
		int degree=(int)((rad*180)/Math.PI);
		return (degree+180);
	}

	public boolean readGameFlow(String fname){
		String buff;
		try
		{
			BufferedReader fin=new BufferedReader(new FileReader(fname));
			if((buff=fin.readLine())!=null) {
				System.out.println(Integer.parseInt(buff));
			}
			fin.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
}

class GameScreen extends Canvas
{
	
	
	/**
	 * @uml.property  name="main"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="gamescreen:W_Shooting_frame"
	 */
	W_Shooting_frame main;
	/**
	 * @uml.property  name="cnt"
	 */
	int cnt;
	/**
	 * @uml.property  name="gamecnt"
	 */
	int gamecnt;

	
	/**
	 * @uml.property  name="x"
	 */
	int x;
	/**
	 * @uml.property  name="y"
	 */
	int y;

	/**
	 * @uml.property  name="dblbuff"
	 */
	Image dblbuff;
	/**
	 * @uml.property  name="gc"
	 */
	Graphics gc;

	/**
	 * @uml.property  name="bg"
	 */
	Image bg;
	/**
	 * @uml.property  name="bg_f"
	 */
	Image bg_f;
	/**
	 * @uml.property  name="bgFlip"
	 */
	Image bgFlip;
	/**
	 * @uml.property  name="cloud" multiplicity="(0 -1)" dimension="1"
	 */
	Image cloud[]=new Image[5];
	/**
	 * @uml.property  name="title"
	 */
	Image title;
	/**
	 * @uml.property  name="title_key"
	 */
	Image title_key;

	/**
	 * @uml.property  name="chr" multiplicity="(0 -1)" dimension="1"
	 */
	Image chr[]=new Image[9];
	/**
	 * @uml.property  name="enemy" multiplicity="(0 -1)" dimension="1"
	 */
	Image enemy[]=new Image[5];
	/**
	 * @uml.property  name="bullet" multiplicity="(0 -1)" dimension="1"
	 */
	Image bullet[]=new Image[5];
	/**
	 * @uml.property  name="explo"
	 */
	Image explo;
	/**
	 * @uml.property  name="item" multiplicity="(0 -1)" dimension="1"
	 */
	Image item[]=new Image[3];
	
	/**
	 * @uml.property  name="num"
	 */
	Image num;
	/**
	 * @uml.property  name="uiUp"
	 */
	Image uiUp;

	/**
	 * @uml.property  name="_start"
	 */
	Image _start;
	/**
	 * @uml.property  name="_over"
	 */
	Image _over;
	/**
	 * @uml.property  name="shield"
	 */
	Image shield;

	/**
	 * @uml.property  name="font"
	 */
	Font font;

	/**
	 * @uml.property  name="v" multiplicity="(0 -1)" dimension="1"
	 */
	int v[]={-2,-1,0,1,2,1,0,-1};
	/**
	 * @uml.property  name="v2" multiplicity="(0 -1)" dimension="1"
	 */
	int v2[]={-8,-7,-6,-5,-4,-3,-2,-1,0,1,2,3,4,5,6,7,8,7,6,5,4,3,2,1,0,-1,-2,-3,-4,-5,-6,-7};
	/**
	 * @uml.property  name="step"
	 */
	int step=0;

	/**
	 * @uml.property  name="boss"
	 */
	boolean boss=false;

	GameScreen(W_Shooting_frame main){
		this.main=main;
		font=new Font("Default",Font.PLAIN,9);
	}

	public void paint(Graphics g){
		if(gc==null) {
			dblbuff=createImage(main.gScreenWidth,main.gScreenHeight);
			if(dblbuff==null) System.out.println("오프스크린 버퍼 생성 실패");
			else gc=dblbuff.getGraphics();
			return;
		}
		update(g);
	}
	public void update(Graphics g){
		cnt=main.cnt;
		gamecnt=main.gamecnt;
		if(gc==null) return;
		dblpaint();
		g.drawImage(dblbuff,0,0,this);
	}
	public void dblpaint(){
		
		switch(main.status){
		case 0:
			Draw_TITLE();
			gc.setColor(new Color(0));
			gc.drawString("Education ver.", 10,40);
			break;
		case 1:
			Draw_BG();
			Draw_MY();
			Draw_BG2();
			drawImageAnc(_start, 0,270, 3);
			break;
		case 2:
		case 4:
			Draw_BG();
			Draw_MY();
			Draw_ENEMY();
			Draw_BULLET();
			Draw_EFFECT();
			Draw_ITEM();
			Draw_BG2();
			Draw_UI();
			break;
		case 3:
			Draw_BG();
			Draw_ENEMY();
			Draw_BULLET();
			Draw_BG2();
			drawImageAnc(_over, 320,240, 4);
			Draw_UI();
			break;
		default:
			break;
		}
	}

	public void Draw_TITLE(){
		gc.drawImage(title,0,0,this);
		if(cnt%20<10) gc.drawImage(title_key, 320-201,370, this);
	}
	public void Draw_BG(){
		int i;
		
		
		int step1 = (cnt%2560)/2;
		step1=step1>=640?step1-1280:step1;
		int step2 = step1>=0?640-step1:-(640+step1);
		gc.drawImage(bg,0-step1,0,this);
		gc.drawImage(bgFlip,step2,0,this);
		
		for(i=0;i<12;i++) gc.drawImage(cloud[3], i*64-((cnt/1)%128), 370, this);
		for(i=-1;i<14;i++) gc.drawImage(cloud[2], i*64-(cnt%128)*2, 395, this);
	}
	public void Draw_BG2(){
		int i;
		step=(cnt%(bg_f.getWidth(this)/main.scrspeed))*main.scrspeed;
		gc.drawImage(bg_f,0-step,540-bg_f.getHeight(this)+v[(cnt/20)%8]*2,this);
		
		if(step>=bg_f.getWidth(this)-main.gScreenWidth) {
			gc.drawImage(bg_f,0-step+bg_f.getWidth(this),540-bg_f.getHeight(this)+v[(cnt/20)%8]*2,this);
		}
		for(i=-1;i<14;i++) gc.drawImage(cloud[0], i*128-(cnt%128)*8, 435, this);
	}
	public void Draw_MY(){
		int myx,myy;
		myx=main.myx/100;
		myy=main.myy/100;
		switch(main.mymode){
		case 0:
		case 1:
			if(main.cnt%20<10) drawImageAnc(chr[2+(main.cnt/5)%2], myx, myy, 4);
			break;
		case 2:
			if(main.myimg==6) drawImageAnc(chr[main.myimg+(main.cnt/3)%2], myx, myy, 4);
			else if(main.myimg!=8) drawImageAnc(chr[main.myimg+(main.cnt/5)%2], myx, myy, 4);
			else if(main.myimg==8) drawImageAnc(chr[main.myimg], myx, myy, 4);
			break;
		case 3:
			if(main.cnt%6<3) drawImageAnc(chr[8], myx, myy, 4);
			break;
		}
		if(main.myshield>2) drawImageAnc(shield, (int)(Math.sin(Math.toRadians((cnt%72)*5))*16+myx), (int)(Math.cos(Math.toRadians((cnt%72)*5))*16+myy), (main.cnt/6%7)*64,0, 64,64, 4);
		else if(main.myshield>0&&main.cnt%4<2) drawImageAnc(shield, (int)(Math.sin(Math.toRadians((cnt%72)*5))*16+myx), (int)(Math.cos(Math.toRadians((cnt%72)*5))*16+myy), (main.cnt/6%7)*64,0, 64,64, 4);
	}
	public void Draw_ENEMY(){
		int i;
		Enemy buff;
		for(i=0;i<main.enemies.size();i++){
			buff=(Enemy)(main.enemies.elementAt(i));
			switch(buff.img){
			case 0:
				drawImageAnc(enemy[0], buff.dis.x, buff.dis.y, ((cnt/8)%7)*36,0, 36,36, 4);
				break;
			case 1:
				drawImageAnc(enemy[1], buff.dis.x, buff.dis.y, 4);
				break;
			case 2:
				switch(buff.mode){
				case 0:
				case 1:
				case 2:
				case 3:
					drawImageAnc(enemy[2], buff.dis.x, buff.dis.y, 0,0,36,50,4);
					break;
				case 5:
					drawImageAnc(enemy[2], buff.dis.x, buff.dis.y, 72,0,36,50,4);
					break;
				case 4:
					drawImageAnc(enemy[2], buff.dis.x, buff.dis.y, 36,0,36,50,4);
					break;
				}
			default:
				break;
			}
		}
	}
	public void Draw_BULLET(){
		Bullet buff;
		int i;
		for(i=0;i<main.bullets.size();i++){
			buff=(Bullet)(main.bullets.elementAt(i));
			switch(buff.img_num){
			case 0:
			case 1:
			case 2:
			case 3:
				drawImageAnc(bullet[buff.img_num], buff.dis.x-6,buff.dis.y-6, 4);
				break;
			}
		}
	}
	public void Draw_EFFECT(){
		int i;
		Effect buff;
		for(i=0;i<main.effects.size();i++){
			buff=(Effect)(main.effects.elementAt(i));
			drawImageAnc(explo, buff.dis.x, buff.dis.y, ((16-buff.cnt)%4)*64,((16-buff.cnt)/4)*64,64,64, 4);
		}
	}
	public void Draw_ITEM(){
		int i;
		Item buff;
		for(i=0;i<main.items.size();i++){
			buff=(Item)(main.items.elementAt(i));
			drawImageAnc(item[buff.kind], buff.dis.x, buff.dis.y, ((main.cnt/4)%7)*36,0, 36,36, 4);
		}
	}
	public void Draw_UI(){
		
		String str2="[1] Speed down   [2] Speed up   [3] Pause";

		gc.setColor(new Color(0));
		gc.drawString(str2, 9,main.gScreenHeight-10);
		gc.drawString(str2, 11,main.gScreenHeight-10);
		gc.drawString(str2, 10,main.gScreenHeight-11);
		gc.drawString(str2, 10,main.gScreenHeight-9);
		gc.setColor(new Color(0xffffff));
		gc.drawString(str2, 10,main.gScreenHeight-10);
		
		
		gc.drawImage(uiUp, 16, 25, this);
		
		drawImageNum(num, 320, 40, main.score, 8);
		drawImageNum(num, 52, 40, main.mylife, 2);
		drawImageNum(num, 576, 40, main.level, 2);
		
	}
	
	
	public void drawImageNum(Image img, int x, int y, int value, int digit){
		
		
		int width = img.getWidth(this)/10;
		int height = img.getHeight(this);
		
		String valueStr = String.valueOf(value);
		if(valueStr.length()<digit)
			valueStr = "0000000000".substring(0, digit-valueStr.length()) + valueStr;
		int _xx = x-valueStr.length()*width/2;
		for(int i=0;i<valueStr.length();i++)
			drawImageAnc(num, _xx+i*width, y, (valueStr.charAt(i)-'0')*width, 0, width,height, 0);

	}
	
	
	public void drawImageAnc(Image img, int x, int y, int anc){
		
		
		int imgw, imgh;
		imgw=img.getWidth(this);
		imgh=img.getHeight(this);
		x=x-(anc%3)*(imgw/2);
		y=y-(anc/3)*(imgh/2);
		
		gc.drawImage(img, x,y, this);
	}
	public void drawImageAnc(Image img, int x, int y, int sx,int sy, int wd,int ht, int anc){
		if(x<0) {
			wd+=x;
			sx-=x;
			x=0;
		}
		if(y<0) {
			ht+=y;
			sy-=y;
			y=0;
		}
		if(wd<0||ht<0) return;
		x=x-(anc%3)*(wd/2);
		y=y-(anc/3)*(ht/2);
		gc.setClip(x, y, wd, ht);
		gc.drawImage(img, x-sx, y-sy, this);
		gc.setClip(0,0, main.gScreenWidth+10,main.gScreenHeight+30);
	}


}
class Bullet
{
	
	
	/**
	 * @uml.property  name="dis"
	 */
	Point dis;
	/**
	 * @uml.property  name="pos"
	 */
	Point pos;
	/**
	 * @uml.property  name="_pos"
	 */
	Point _pos;
	/**
	 * @uml.property  name="degree"
	 */
	int degree;
		
	/**
	 * @uml.property  name="speed"
	 */
	int speed;
	/**
	 * @uml.property  name="img_num"
	 */
	int img_num;
	/**
	 * @uml.property  name="from"
	 */
	int from;
	Bullet(int x, int y, int img_num, int from, int degree, int speed){
		pos=new Point(x,y);
		dis=new Point(x/100,y/100);
		_pos=new Point(x,y);
		this.img_num=img_num;
		this.from=from;
		this.degree=degree;
		this.speed=speed;
	}
	public void move(){
		_pos=pos;
		pos.x-=(speed*Math.sin(Math.toRadians(degree))*100);
		pos.y-=(speed*Math.cos(Math.toRadians(degree))*100);
		dis.x=pos.x/100;
		dis.y=pos.y/100;
		
	}
}
class Enemy
{
	
	/**
	 * @uml.property  name="main"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="enemies:W_Shooting_frame"
	 */
	W_Shooting_frame main;
	/**
	 * @uml.property  name="pos"
	 */
	Point pos;
	/**
	 * @uml.property  name="_pos"
	 */
	Point _pos;
	/**
	 * @uml.property  name="dis"
	 */
	Point dis;
	/**
	 * @uml.property  name="img"
	 */
	int img;
	/**
	 * @uml.property  name="kind"
	 */
	int kind;
	/**
	 * @uml.property  name="life"
	 */
	int life;
	/**
	 * @uml.property  name="mode"
	 */
	int mode;
	/**
	 * @uml.property  name="cnt"
	 */
	int cnt;
	/**
	 * @uml.property  name="shoottype"
	 */
	int shoottype;
	/**
	 * @uml.property  name="hitrange"
	 */
	int hitrange;
	/**
	 * @uml.property  name="bul"
	 * @uml.associationEnd  
	 */
	Bullet bul;
	Enemy(W_Shooting_frame main, int img, int x, int y, int kind, int mode){
		this.main=main;
		pos=new Point(x,y);
		_pos=new Point(x,y);
		dis=new Point(x/100,y/100);
		this.kind=kind;
		this.img=img;
		this.mode=mode;
		life=6+main.RAND(0,5)*main.level;
		cnt=main.RAND(main.level*5,80);
		shoottype=main.RAND(0,4);
		hitrange=1500;
		switch(kind){
		case 0:
			break;
		case 1:
			life=400+300*main.level;
			mode=0;
			hitrange=12000;
			break;
		case 2:
			life=20+main.RAND(0,10)*main.level;
			hitrange=2000;
			cnt=-(main.RAND(30,50));
			break;
		}

	}
	public boolean move(){
		boolean ret=true;
		
		
		switch(kind){
		case 2:
			if(mode!=4) break;
			if(cnt<30&&cnt%5==0){
				bul=new Bullet(pos.x, pos.y, 2, 1, 90, 5);
				main.bullets.add(bul);
			}
			if(cnt>50){
				if(main.RAND(1,100)<50){
					mode=1;
					cnt=30;
				}else{
					mode=5;
					cnt=0;
				}
			}
			break;
		case 0:
			switch(shoottype){
			case 0:
				if(cnt%100==0||cnt%103==0||cnt%106==0) {
					bul=new Bullet(pos.x, pos.y, 2, 1, main.getAngle(pos.x,pos.y,main.myx,main.myy), 3);
					main.bullets.add(bul);
				}
				break;
			case 1:
				if(cnt%90==0||cnt%100==0||cnt%110==0) {
					bul=new Bullet(pos.x, pos.y, 2, 1, (0+(cnt%36)*10)%360, 3);
					main.bullets.add(bul);
					bul=new Bullet(pos.x, pos.y, 2, 1, (30+(cnt%36)*10)%360, 3);
					main.bullets.add(bul);
					bul=new Bullet(pos.x, pos.y, 2, 1, (60+(cnt%36)*10)%360, 3);
					main.bullets.add(bul);
					bul=new Bullet(pos.x, pos.y, 2, 1, (90+(cnt%36)*10)%360, 3);
					main.bullets.add(bul);
				}
				break;
			case 2:
				if(cnt%30==0||cnt%60==0||cnt%90==0||cnt%120==0||cnt%150==0||cnt%180==0) {
					bul=new Bullet(pos.x, pos.y, 2, 1, (main.getAngle(pos.x,pos.y,main.myx,main.myy)+main.RAND(-20,20))%360, 2);
					main.bullets.add(bul);
				}
				break;
			case 3:
				if(cnt%90==0||cnt%110==0||cnt%130==0){
					bul=new Bullet(pos.x, pos.y, 2, 1, main.getAngle(pos.x,pos.y,main.myx,main.myy), 2);
					main.bullets.add(bul);
					bul=new Bullet(pos.x, pos.y, 2, 1, (main.getAngle(pos.x,pos.y,main.myx,main.myy)-20)%360, 2);
					main.bullets.add(bul);
					bul=new Bullet(pos.x, pos.y, 2, 1, (main.getAngle(pos.x,pos.y,main.myx,main.myy)+20)%360, 2);
					main.bullets.add(bul);
				}
				break;
			case 4:
				break;
			}
			break;
		case 1:
			int lv,i;
			switch(mode){
			case 5:
				if(main.level>=10) lv=5; else lv=(10-main.level)*5;
				if(cnt%lv==0||cnt%(lv+5)==0||cnt%(lv+15)==0) {
					for(i=0;i<4+(50-lv)/5;i++){
						bul=new Bullet(pos.x, pos.y, 2, 1, (30*i+(cnt%36)*10)%360, 5);
						main.bullets.add(bul);
					}

				}
				break;
			case 7:
				if(main.level>=10) lv=1; else lv=10-main.level;
				if(cnt%lv==0){
					bul=new Bullet(pos.x-3000+main.RAND(-10,+10)*100, pos.y+main.RAND(10,80)*100, 2, 1, 90, 5+(10-lv)/2);
					main.bullets.add(bul);
				}
				break;
			}
			break;
		}

		
		switch(kind){
		case 2:
			/*
			위치 네우로이 이동 시나리오
			1. 오른쪽에서 등장하여 왼쪽으로 이동하다 화면 70~80% 정도 위치에 정지한다.
			2. 플레이어의 y좌표를 참조하여 상하로 일정 간격 이동하다
			3. 정지하여
			4. 손을 앞으로 내밀고 공격
			5. 2~4를 몇 번 수행한 후
			6. 뒤로 돌아 물러간다 
			*/
			switch(mode){
			case 0:
				pos.x-=500;
				if(cnt>=0&&pos.x<main.gScreenWidth*80) {
					mode=1;
					cnt=0;
				}
				break;
			case 1:
				if(pos.x>main.gScreenWidth*80){
					mode=0;
					break;
				}
				if(cnt>=30) {
					if(pos.y>main.myy) mode=3;
					else mode=2;
					cnt=0;
				}
				break;
			case 2:
				if(pos.y<main.gScreenHeight*90&&cnt<20)
					pos.y+=250;
				else {
					mode=4;
					cnt=0;
				}
				break;
			case 3:
				if(pos.y>6400&&cnt<20)
					pos.y-=250;
				else {
					mode=4;
					cnt=0;
				}
				break;
			case 5:
				pos.x+=350;
				break;
			case 4:
				break;
			}
			break;
		case 0:
			switch(mode){
			case 0:
				pos.x-=500;
				pos.y+=80;
				if(pos.x<main.myx) mode=2;
				break;
			case 1:
				pos.x-=500;
				pos.y-=80;
				if(pos.x<main.myx) mode=3;
				break;
			case 2:
				pos.x+=600;
				pos.y+=240;
				break;
			case 3:
				pos.x+=600;
				pos.y-=240;
				break;
			}
			break;
		case 1:
		/*
			보스 캐릭터 움직임 시나리오 (mode 값에 따라)
			0. 화면 우측에서 조금 안쪽으로 들어온다.
			1. 플레이어보다 위에 있으면 아래로(mode=2), 아래에 있으면 위로(mode=3) 일정 간격 (실제 좌표 기준으로 120도트) 이동한다.
			4. 화면 중앙까지 나온다
			5. 0.의 위치까지 되돌아간다.
			6. 화면 바깥으로 나가 사라진다.
			7. 잠시 그 자리에서 대기한 뒤 mode=1로 돌아간다
			8. 잠시 그 자리에서 대기한 뒤 mode=5로 전환한다
		*/
			if(main.gamecnt==1200) mode=4;
			if(main.gamecnt==2210) mode=6;
			switch(mode){
			case 0:
				pos.x-=100;
				if(pos.x<53000) mode=1;
				break;
			case 1:
				if(cnt%30==0){
					if(pos.y>main.myy) mode=3;
					else if(pos.y<main.myy) mode=2;
					_pos.x=pos.x;
					_pos.y=pos.y;
				}
				break;
			case 2:
				if(pos.y+400<42000)
					pos.y+=400;
				else{
					cnt=0;
					mode=7;
				}
				if(pos.y-_pos.y>12000) {
					cnt=0;
					mode=7;
				}
				break;
			case 3:
				if(pos.y-400>6000)
					pos.y-=400;
				else{
					cnt=0;
					mode=7;
				}
				if(_pos.y-pos.y>12000) {
					cnt=0;
					mode=7;
				}
				break;
			case 4:
				pos.x-=800;
				if(pos.x<30000) {
					mode=8;
					cnt=0;
				}
				break;
			case 5:
				pos.x+=350;
				if(pos.x>53000) mode=1;
				break;
			case 6:
				pos.x+=500;
				break;
			case 7:
				if(cnt>100) mode=1;
				break;
			case 8:
				if(cnt>90) mode=5;
				break;
			}
			break;
		}
		dis.x=pos.x/100;
		dis.y=pos.y/100;
		if(dis.x<0||dis.x>640||dis.y<0||dis.y>480) ret=false;

		cnt++;
		return ret;
	}
}
class Effect
{
	
	/**
	 * @uml.property  name="pos"
	 */
	Point pos;
	/**
	 * @uml.property  name="_pos"
	 */
	Point _pos;
	/**
	 * @uml.property  name="dis"
	 */
	Point dis;
	/**
	 * @uml.property  name="img"
	 */
	int img;
	/**
	 * @uml.property  name="kind"
	 */
	int kind;
	/**
	 * @uml.property  name="cnt"
	 */
	int cnt;
	Effect(int img, int x, int y, int kind){
		pos=new Point(x,y);
		_pos=new Point(x,y);
		dis=new Point(x/100,y/100);
		this.kind=kind;
		this.img=img;
		cnt=16;
	}
}
class Item
{
	
	/**
	 * @uml.property  name="pos"
	 */
	Point pos;
	/**
	 * @uml.property  name="dis"
	 */
	Point dis;
	/**
	 * @uml.property  name="speed"
	 */
	int speed;
	/**
	 * @uml.property  name="cnt"
	 */
	int cnt;
	/**
	 * @uml.property  name="kind"
	 */
	int kind;
	Item(int x, int y, int kind){
		this.kind=kind;
		pos=new Point(x,y);
		dis=new Point(x/100,y/100);
		speed=-200;
		cnt=0;
	}
	public boolean move(){
		boolean ret=false;
		pos.x-=speed;
		cnt++;
		if(cnt>=50) speed=200;
		else if(cnt>=30) speed=100;
		else if(cnt>=20) speed=-100;
		dis.x=pos.x/100;
		if(pos.x<0) ret=true;
		return ret;
	}
}

class MyWindowAdapter extends WindowAdapter
{
	
	
	
	
	
	//
	MyWindowAdapter(){
	}
	public void windowClosing(WindowEvent e) {
		Window wnd = e.getWindow();
		wnd.setVisible(false);
		wnd.dispose();
		System.exit(0);
	}
}
