package com.gameMaking.Systems;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Image_Loader { 
	
	private static final Image_Loader instance = new Image_Loader();
	
	public static Image_Loader getInstance(){
		return instance;
	}

	//LoadMethods========================
	public void LoadGameResource() throws SlickException {
		
		p_idle = new SpriteSheet(new Image("Images/Player/player_idle.png"), 32, 42);
		p_move = new SpriteSheet(new Image("Images/Player/player_move.png"), 32, 42);
		
		CBullet = new SpriteSheet(new Image("Images/Player/player_Attack.png"), 16, 16);
		
	}
	
	//Resources===============================
	
	//Bullet==================================
	public SpriteSheet CBullet;
	
	//Character
	public SpriteSheet p_idle;
	public SpriteSheet p_move;
}
