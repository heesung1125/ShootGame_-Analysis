package com.gameMaking.Controls;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;

public class InputControl implements InputProviderListener {
	

	private boolean[] keys = new boolean[16];
	private Command[] commands = new Command[16];
	
	private InputProvider provider;
	
	private enum KEY_TYPE{
			
		Forward,Down,Left,Right
		
	};
	
	private int[] KEY_VALUE = {
			
			Input.KEY_UP,Input.KEY_DOWN,
			Input.KEY_LEFT,Input.KEY_RIGHT,
	};

//	private Command Forward = new BasicCommand("Forward");
//	private Command Down = new BasicCommand("Down");
//	private Command Left = new BasicCommand("Left");
//	private Command Right = new BasicCommand("Right");
	
//	private String message = "";
	
	public InputControl(GameContainer gc) {
		
		provider = new InputProvider(gc.getInput());
		provider.addListener(this);
		
		KEY_TYPE[] types = KEY_TYPE.values();
		
		for(int i=0; i < types.length; i++){
			
			commands[i] = new BasicCommand(String.valueOf(types[i]));
			provider.bindCommand(new KeyControl(KEY_VALUE[i]), commands[i]);
		}
		
//		provider.bindCommand(new KeyControl(Input.KEY_UP), Forward);
//		provider.bindCommand(new KeyControl(Input.KEY_DOWN), Down);
//		provider.bindCommand(new KeyControl(Input.KEY_LEFT), Left);
//		provider.bindCommand(new KeyControl(Input.KEY_RIGHT), Right);
//		// 키보드값 입력 받는 부분 
	}
	
	@Override
	public void controlPressed(Command command) {
		
		//message = command.toString();
		//message = "Pressed : "+ command; 2강 테스트용
		// message 필드에 Pressed : 눌린 키값 대입
		
		switch (command.toString()) {
		case "[Command=Forward]":
			keys[0] = true;
			break;
		case "[Command=Down]":
			keys[1] = true;
			break;
		case "[Command=Left]":
			keys[2] = true;
			break;
		case "[Command=Right]":
			keys[3] = true;
			break;
		}
	}

	@Override
	public void controlReleased(Command command) {
		//message = ""; //2강 테스트용
		
		switch (command.toString()) {
		case "[Command=Forward]":
			keys[0] = false;
			break;
		case "[Command=Down]":
			keys[1] = false;
			break;
		case "[Command=Left]":
			keys[2] = false;
			break;
		case "[Command=Right]":
			keys[3] = false;
			break;
		}		
	}
	
	public  boolean[] getMessage() {return keys;}

}
