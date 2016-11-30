package com.gameMaking.Graphics;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.gameMaking.Controls.InputControl;
import com.gameMaking.Systems.Image_Loader;
import com.gameMaking.models.Player;

public class Game extends BasicGame {
	
	private InputControl input;

	public static final int Screen_Width = 640;
	public static final int Screen_height = 800;
	
	private Player p;
	


	public Game(String title) {
		super(title);

	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.drawString("Press Up, Down, Left, Right", 10, 100);
		//�� ����ȭ�鿡 ���ڿ� ��� (���ڿ�,x��ǥ��,y��ǥ��)
		
		p.getCurrentAnimation().draw(p.getX(), p.getY());
		
		//g.drawString(input.getMessage(), 10, 200); 2�� �׽�Ʈ��
		//�� ����ȭ�鿡 ���ڿ� ��� (���ڿ�,x��ǥ��,y��ǥ��)
		//input.getMessage() InputControl Ŭ������ ���ϰ��� �޾ƿͼ� ���ڿ��� ���
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		
		Image_Loader.getInstance().LoadGameResource();

		control = new InputControl(gc); // InputControl Ŭ���� �ν��Ͻ�ȭ
		p = new Player(100, 100);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		
		p.move(input.getMessage());

		
	}
// ������� BasicGame Ŭ������ �����ε� �κ� ���������� ���ϴ��� �𸣰ڴ�..2016-11-30
	
	public static void main(String[] args) {
		
		AppGameContainer appgc; //AppGameContainer Ŭ���� ȣ��
		try { // ����ó��
			appgc = new AppGameContainer(new Game("My First Java2D Game")); // ������â ��� ����Ÿ��Ʋ �Է�
			appgc.setDisplayMode(Screen_Width, Screen_height, false); // ������â ���� (���α���,���α���,Ǯ��ũ����� ����) true : Ǯ��ũ������ â ����, false : ���������� â����
			appgc.start(); // ���� ����
		} catch (SlickException ex) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			// �����߻��� ó������ ������ ������ �𸣰ڴ�...2016-11-30
			Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
	
	

}
