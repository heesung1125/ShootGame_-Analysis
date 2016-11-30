package com.gameMaking.Systems;


public abstract class GameObject {
	public float x,y;
	public Image_Loader loader;
	
	public GameObject(float x,float y) {
		
		this.x = x;
		this.y = y;
		loader = Image_Loader.getInstance();
	}
		
	public void setX(float x){
		this.x = x;
	}
	
	public void setY(float y){
		this.y = y;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}

}
