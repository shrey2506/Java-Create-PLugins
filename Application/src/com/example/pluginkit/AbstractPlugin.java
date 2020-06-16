package com.example.pluginkit;

public abstract class AbstractPlugin extends Thread{
	
	@Override
	public abstract void run();
	public abstract void close();
}
