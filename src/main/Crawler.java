package main;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Crawler {

	public void calistir(String fileName){
		int sayfaSayisi=1;
		
		boolean durum=false;
		do{
		
			
			try {
				
				Document document = Jsoup.connect("https://profil.gittigidiyor.com/"+fileName+"/aldigi-yorumlar/satis?sf="+sayfaSayisi+"#yorum").get();
				Elements broadcasts = document.select("p");

				
				System.out.println("["+sayfaSayisi+ ". Veri alınıyor.]\n\n");
				String gelenYorum="";
			      for (Element link : broadcasts) {
			    	  gelenYorum=link.getElementsByClass("comment_content").text();
			    	  if(!gelenYorum.equals("")){
			    		  dosyayaYaz(fileName,gelenYorum);
				          System.out.println(link.getElementsByClass("comment_content").text());
			    	  }
			       }

			      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			      sayfaSayisi++;
			      if(sayfaSayisi==5000){
			    	  durum=true;
			      }
			} catch (Exception e) {
				System.out.println("Connection Hatası Var Bro");
			}

		}while(!durum);
		

	}

	private void dosyayaYaz(String fileName,String yorum){
			BufferedWriter bw = null;
			FileWriter fw = null;
			
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


