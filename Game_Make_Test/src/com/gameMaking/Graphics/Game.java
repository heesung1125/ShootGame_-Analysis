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
		//↑ 게임화면에 문자열 출력 (문자열,x좌표값,y좌표값)
		
		p.getCurrentAnimation().draw(p.getX(), p.getY());
		
		//g.drawString(input.getMessage(), 10, 200); 2강 테스트용
		//↑ 게임화면에 문자열 출력 (문자열,x좌표값,y좌표값)
		//input.getMessage() InputControl 클래스의 리턴값을 받아와서 문자열로 출력
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		
		Image_Loader.getInstance().LoadGameResource();

		control = new InputControl(gc); // InputControl 클래스 인스턴스화
		p = new Player(100, 100);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		
		p.move(input.getMessage());

		
	}
// 여기까지 BasicGame 클래스의 오버로딩 부분 아직까지는 왜하는지 모르겠다..2016-11-30
	
	public static void main(String[] args) {
		
		AppGameContainer appgc; //AppGameContainer 클래스 호출
		try { // 예외처리
			appgc = new AppGameContainer(new Game("My First Java2D Game")); // 윈도우창 상단 게임타이틀 입력
			appgc.setDisplayMode(Screen_Width, Screen_height, false); // 윈도우창 설정 (가로길이,세로길이,풀스크린모드 설정) true : 풀스크린으로 창 열림, false : 설정값으로 창열림
			appgc.start(); // 게임 시작
		} catch (SlickException ex) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			// 에러발생시 처리문구 같은데 아직은 모르겠다...2016-11-30
			Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
	
	

}
