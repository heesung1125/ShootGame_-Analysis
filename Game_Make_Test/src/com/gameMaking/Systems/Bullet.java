package com.gameMaking.Systems;

import org.newdawn.slick.SlickException;

public class Bullet extends GameObject {
	
	public float speed,angle,speedRate,angleRate;
	
	public Bullet(float x,float y) throws SlickException{
		super(x,y);
	}
	
	public Bullet(float x, float y, float speed) throws SlickException{
		this(x,y);
		this.speed = speed;
		
	}
	
	public Bullet(float x, float y, float speed, float angle) throws SlickException{
		this(x,y,speed);
		this.angle = angle;
		
	}
	
	public Bullet(float x, float y, float speed, float angle, float speedRate) throws SlickException{
		this(x,y,speed,angle);
		this.speedRate = speedRate;
		
	}

	
	public Bullet(float x, float y, float speed, float angle, float speedRate, float angleRate) throws SlickException{
		this(x,y,speed,angle,speedRate);
		this.angleRate = angleRate;
		
	}
	
	public void setSpeed(float speed){
		this.speed = speed;
	}
	
	public void setAngle(float angle){
		this.angle = angle;
	}
	
	public void setSpeedRate(float speedRate){
		this.speedRate = speedRate;
	}	
	
	public void setAngleRate(float angleRate){
		this.angleRate = angleRate;
	}
	
	public float getSpeed(){
		return speed;
	}
	
	public float getAngle(){
		return angle;
	}	
	
	public float getSpeedRate(){
		return speedRate;
	}	
	
	public float getAngleRate(){
		return angleRate;
	}

}
