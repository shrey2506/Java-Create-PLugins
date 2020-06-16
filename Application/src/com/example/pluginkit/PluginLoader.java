package com.example.pluginkit;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

public class PluginLoader{
	
	
	public static class PluginData{
		public final String name;
		public final String pack;
		public final URL file;
		public final Class<?>cache;
		
		public PluginData(String name ,String pack,URL file) throws ClassNotFoundException{
			this.name=name;
			this.pack=pack;
			this.file=file;
			this.cache=Class.forName(this.pack,true,new URLClassLoader(new URL[] {this.file}));
		}
	}
	
	final private static HashMap<String,PluginData> data =new HashMap<>();
	
	public static void parseConfig(File file,String plugin_root) throws IOException,ClassNotFoundException{
		final BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		
		String in;
		int line=0;
		while((in=br.readLine())!=null) {
			line++;
			if(in.isEmpty()||in.startsWith("#"))
				continue;
			final String[] split=in.split(" ");
			if(split.length<2) {
				br.close();
				throw new RuntimeException("Could not split line in configuration file"+ in +" on line "+ line); 
			}
			if(data.get(split[0])!=null) {
				br.close();
				throw new RuntimeException("Atempt to replace loaded plugin"+ split[0] +" on line "+ line); 
			}
			data.put( split[0] ,new PluginData(split[0] ,split[1] ,new File(plugin_root,split[0] + ".jar").toURI().toURL()) );       
		}
		br.close();
	}
	
	public static AbstractPlugin newInstance(PluginData data) throws InstantiationException,IllegalAccessException{
		return (AbstractPlugin) data.cache.newInstance();
		
	}
	public static HashMap<String, PluginData> getPlugins(){
		return PluginLoader.data;
	}
	
	
	
}