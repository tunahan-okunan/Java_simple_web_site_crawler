package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlerGunluk {
	public void calistir(String fileName){
		int sayfaSayisi=1;
		String gelenTarih="";
		String gelenYorum="";

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd_MM_yyyy");

		String todayDate=sdf.format(new Date());
		String todayDateDif=sdf2.format(new Date());
		
		boolean durum=false;
		do{


			
			try {	
				ArrayList<String> tarihList=new ArrayList<>();
				Document document = Jsoup.connect("https://profil.gittigidiyor.com/"+fileName+"/aldigi-yorumlar/satis?sf="+sayfaSayisi+"#yorum").get();
				Elements broadcasts = document.select("p");
				Elements yorumLink = document.select("p");	

                int tarihSayac=0;
                int yorumSayac=0;
                
				System.out.println("["+sayfaSayisi+ ". Veri alınıyor.]\n\n");

                
				for (Element link : broadcasts) {
					gelenTarih=link.getElementsByClass("mt10").text();
					if(!gelenTarih.equals("") && !gelenTarih.contains("a") ){
						if(todayDate.equals(gelenTarih)){

							tarihList.add(gelenTarih);
							tarihSayac++;
							System.out.println(link.getElementsByClass("mt10").text());
						}
						else{
							durum=true;
							break;
						}
					}
				}
				
				if(sayfaSayisi==3)
				{
					int a=5;

				}
				for (Element link : yorumLink) {
			    	  gelenYorum=link.getElementsByClass("comment_content").text();
			    	  if(!gelenYorum.equals("")){
			    		  
                          
			    		  if(todayDate.equals(tarihList.get(yorumSayac)))
			    		  {
					    	  dosyayaYaz(fileName + "__" + todayDateDif ,gelenYorum);
					          System.out.println(gelenYorum);
				    		  System.out.println("Tarih="+tarihList.get(yorumSayac));
			    		 }
				    	  yorumSayac++;
                          if(tarihList.size()==yorumSayac){
                        	  break;
                          }
			    	  }
				}

				
				System.out.println("Tarih Sayisi="+ tarihSayac);

				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				sayfaSayisi++;

			} catch (Exception e) {
				System.out.println("Connection Hatası Var Bro");
			}

		}while(!durum);


	}
	private void dosyayaYaz(String fileName,String yorum){
		BufferedWriter bw = null;
		FileWriter fw = null;
		File f=new File("src/files/"+fileName+".txt");
		if(!f.exists()){
			
		}
		try {
		
			 fw = new FileWriter("src/files/"+fileName+".txt",true);		 
			 bw = new BufferedWriter(fw);

		     bw.write(yorum);
		     bw.newLine();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
}

}
