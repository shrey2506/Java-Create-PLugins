package com.example.app;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.example.pluginkit.AbstractPlugin;
import com.example.pluginkit.PluginLoader;
import com.example.pluginkit.PluginLoader.PluginData;

public class Main{
	public static void main(String[] args) {
		
		final File plugin_list=new File("/home/shrey/eclipse-workspace/Application/src/config.cfg");
		
		try {
			PluginLoader.parseConfig(plugin_list, null);
		}catch(IOException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		final HashMap<String,PluginData> plugin_datas=PluginLoader.getPlugins();
		final AbstractPlugin[] plugins=new AbstractPlugin[plugin_datas.size()];
		
		try {
			int i=0;
			for(String key: plugin_datas.keySet())
				plugins[i++]=PluginLoader.newInstance(plugin_datas.get(key));
		}catch(InstantiationException e) {
			e.printStackTrace();
		}catch(IllegalAccessException e) {
			e.printStackTrace();
		}
		
		for(int i=0;i<plugins.length;i++)
			plugins[i].start();
		
		try {
			Thread.sleep(10000);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		for(int i=0;i<plugins.length;i++) {
			plugins[i].interrupt();
			try {
				plugins[i].join();			
		    }catch(InterruptedException e) {
		    	e.printStackTrace();
		    }
			plugins[i].close();
		
	     }
}
}	
	
	