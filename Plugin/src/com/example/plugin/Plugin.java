package com.example.plugin;

import com.example.pluginkit.AbstractPlugin;

public class Plugin extends AbstractPlugin{
	
	private int i=0;
	
	@Override
	public void run() {
		System.out.println("Frank is running");
		
		while(true) {
			try {
				Thread.sleep(500);
			}catch(InterruptedException e) {
				System.out.println("Hey we are closing");
				break;
			}
            i++;
		}
	}
	
	@Override
	public void close() {
		System.out.println("Plugin has stopped");
		System.out.println("The value of i was " + i);
	}
}