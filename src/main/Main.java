package main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

public class Main {


	public static void main(String[] args) {
	
    //    WekaClassify wekaClassify=new WekaClassify();
        try {
		//	wekaClassify.kargoKategoriTestEt();
		//	wekaClassify.urunKategoriTestEt();
		//	wekaClassify.magazaKategoriTestEt();
      //  	wekaClassify.kargoModelTest();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	Crawler crawler=new Crawler();
	//	crawler.calistir("yenisey");
        CrawlerGunluk crawlerGunluk=new CrawlerGunluk();
        crawlerGunluk.calistir("yenisey");
	} 
	
		
		

}
