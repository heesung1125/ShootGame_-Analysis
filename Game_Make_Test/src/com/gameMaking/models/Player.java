package com.gameMaking.models;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import com.gameMaking.Graphics.Game;
import com.gameMaking.Systems.GameObject;

public class Player extends GameObject {
	
	private int state = 0;
	private Animation idle;
	private Animation move;

	public Player(float x, float y) throws SlickException {
		super(x, y);
		
		init();
	}

	@Override
	public void init() throws SlickException {
		
		idle = new Animation(loader.p_idle,100);
		move = new Animation(loader.p_move,100);
		move.setLooping(false);
	}
	
	public void move(boolean[] keys) {
		
		outOfrange();
		
		if(keys[0]){
			this.setY(this.getY()-0.3f); //keys 변수에 0 값 들어오면 현재좌표에서 -0.3 이동
		}
		if(keys[1]){
			this.setY(this.getY()+0.3f); //keys 변수에 1 값 들어오면 현재좌표에서 +0.3 이동
		}
		if(keys[2]){
			this.setX(this.getX()-0.3f); //keys 변수에 2 값 들어오면 현재좌표에서 -0.3 이동
			state = 1;
		}
		else if(state == 1){
			state = 0;
		}
		if(keys[3]){
			this.setX(this.getX()+0.3f); //keys 변수에 3 값 들어오면 현재좌표에서 +0.3 이동
			state = 2;
		}
		else if(state == 2){
			state = 0;
		}		
		
/*		switch (Command) {
		case "[Command=Forward]":
			this.setY(this.getY()-0.3f); //Command변수에 Forward 값 들어오면 현재좌표에서 -0.3 이동
			break;
		case "[Command=Down]":
			this.setY(this.getY()+0.3f); //Command변수에 Down 값 들어오면 현재좌표에서 +0.3 이동
			break;			
		case "[Command=Left]":
			this.setX(this.getX()-0.3f); //Command변수에 Left 값 들어오면 현재좌표에서 -0.3 이동
			state = 1;
			break;			
		case "[Command=Right]":
			this.setX(this.getX()+0.3f); //Command변수에 Right 값 들어오면 현재좌표에서 +0.3 이동
			state = 2;
			break;
		default:
			state = 0;
			3강 캐릭터 이동 기본
*/
		
		
	}
	
	public void outOfrange(){
		if(this.getX() < 0){
			this.setX(0);
		}
		if(this.getX()+idle.getWidth() > Game.Screen_Width){
			this.setX(Game.Screen_Width-idle.getWidth());
		}
		if(this.getY() < 0){
			this.setY(0);
		}
		if(this.getY()+idle.getHeight() > Game.Screen_height){
			this.setY(Game.Screen_height-idle.getHeight());
		}
	}
	
	public Animation getCurrentAnimation(){
		if(state != 0)
			return move;
		else
			return idle;
	}
	
	public int getState() {
		return state;
	}

}
