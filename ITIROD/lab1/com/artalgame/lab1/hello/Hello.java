package com.artalgame.lab1.hello;

import com.artalgame.lab1.world.*;

public class Hello {
	public static void main(String args[])	{
		World worldClass = new World();
		System.out.println("Hello " + worldClass.getWorld());
	}
}