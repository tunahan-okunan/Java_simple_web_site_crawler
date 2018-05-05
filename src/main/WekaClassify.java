package main;

import java.io.*;
import java.sql.*;
import java.util.*;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.*;

import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class WekaClassify {
    //Cross Validation ile eğitim seti testi.
    public void kargoKategoriTestEt() throws Exception {
        int kargoOlumluSayisi=0;
        int kargoOlumsuzSayisi=0;
        int kargoNotrSayisi=0;

        BufferedReader denemeReader = new BufferedReader(new FileReader("C:\\Users\\TUNAHAN\\Desktop\\kargo.arff"));
        Instances deneme = new Instances(denemeReader);
        denemeReader.close();

        int sonIndex = deneme.numAttributes() - 1;
        deneme.setClassIndex(sonIndex);

        ArrayList cumleAttr = new ArrayList();
        ArrayList kategoriAttr = new ArrayList();

        for (int i=0; i<deneme.numInstances(); i++)
        {
            cumleAttr.add(deneme.instance(i).stringValue(0));
            kategoriAttr.add(deneme.instance(i).stringValue(1));
            if(deneme.instance(i).stringValue(1).equals("olumlu")){
            	kargoOlumluSayisi++;
            }
            else if(deneme.instance(i).stringValue(1).equals("olumsuz"))
            {
            	kargoOlumsuzSayisi++;
            }
            else if(deneme.instance(i).stringValue(1).equals("notr")){
            	kargoNotrSayisi++;
            }
        }


        //Filtre (String to Word Vector)
        StringToWordVector filtre = new StringToWordVector();
        filtre.setInputFormat(deneme);

        deneme = Filter.useFilter(deneme, filtre);

        NaiveBayes bayes = new NaiveBayes();
        bayes.buildClassifier(deneme);

        //Cross-Validation
        Evaluation eval = new Evaluation(deneme);
        eval.crossValidateModel(bayes, deneme, 10, new Random(1));

        System.out.println(eval.toSummaryString("\nResults\n=====\n", false));
        System.out.println(eval.toMatrixString("\nConfusion Matrix\n=====\n"));

        
        System.out.println("Olumlu Kargo Sayısı= "+ kargoOlumluSayisi);
        System.out.println("Olumsuz Kargo Sayısı= "+ kargoOlumsuzSayisi);
        System.out.println("Notr Kargo Sayısı= "+ kargoNotrSayisi);
        

        /*
        //Connection
        String url = "jdbc:mysql://localhost:3306/magaza_degerlendirme?useSSL=false";
        String driver = "com.mysql.jdbc.Driver";
        String username = "root";
        String password = "root";
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, username, password);

        for (int i=0; i<deneme.numInstances(); i++)
        {
            //Sınıflandırmadan önceki verilerin etiket değerleri
            double indeks = deneme.instance(i).classValue();
            String gercekSınıfAdı = deneme.attribute(0).value((int) indeks);
            //Weka'da bayes ile sınıflandırma yapıldıktan sonraki etiket değerleri
            double index = bayes.classifyInstance(deneme.instance(i));
            String wekaSınıfAdı = deneme.attribute(0).value((int) index);
            //System.out.println("Sınıflandırmadan Önce: " + gercekSınıfAdı + " ==> " + wekaSınıfAdı);

            CallableStatement cs = conn.prepareCall("{call spMakaleIlıskı}");
            cs.executeQuery();

            CallableStatement cs1 = conn.prepareCall("{call spMakaleKaydet(?, ?, ?, ?, @kategori_id, @yazar_id, @gazete_id)}");
            cs1.setString(1,wekaSınıfAdı);
            //cs1.registerOutParameter(1, Types.INTEGER);
            cs1.setString(2, (String) kategoriAttr.get(i));
            //cs1.registerOutParameter(2, Types.INTEGER);
            cs1.setString(3, (String) cumleAttr.get(i));
            //cs1.registerOutParameter(3, Types.INTEGER);
       //     cs1.setString(4, (String) icerikAttr.get(i));
            cs1.execute();
        }
        System.out.println("Sonuçlar veritabanına başarıyla kaydedildi...");
        */
    }

    //Train modeli kullanılarak günlük köşe yazıları test edildi.
    public void urunKategoriTestEt() throws Exception {
        int urunOlumluSayisi=0;
        int urunOlumsuzSayisi=0;
        int urunNotrSayisi=0;

        BufferedReader denemeReader = new BufferedReader(new FileReader("C:\\Users\\TUNAHAN\\Desktop\\urun.arff"));
        Instances deneme = new Instances(denemeReader);
        denemeReader.close();

        int sonIndex = deneme.numAttributes() - 1;
        deneme.setClassIndex(sonIndex);

        ArrayList cumleAttr = new ArrayList();
        ArrayList kategoriAttr = new ArrayList();

        for (int i=0; i<deneme.numInstances(); i++)
        {
            cumleAttr.add(deneme.instance(i).stringValue(0));
            kategoriAttr.add(deneme.instance(i).stringValue(1));
            if(deneme.instance(i).stringValue(1).equals("olumlu")){
            	urunOlumluSayisi++;
            }
            else if(deneme.instance(i).stringValue(1).equals("olumsuz"))
            {
            	urunOlumsuzSayisi++;
            }
            else if(deneme.instance(i).stringValue(1).equals("notr")){
            	urunNotrSayisi++;
            }
        }


        //Filtre (String to Word Vector)
        StringToWordVector filtre = new StringToWordVector();
        filtre.setInputFormat(deneme);

        deneme = Filter.useFilter(deneme, filtre);

        NaiveBayes bayes = new NaiveBayes();
        bayes.buildClassifier(deneme);

        //Cross-Validation
        Evaluation eval = new Evaluation(deneme);
        eval.crossValidateModel(bayes, deneme, 10, new Random(1));

        System.out.println(eval.toSummaryString("\nResults\n=====\n", false));
        System.out.println(eval.toMatrixString("\nConfusion Matrix\n=====\n"));

        
        System.out.println("Olumlu Ürün Sayısı= "+ urunOlumluSayisi);
        System.out.println("Olumsuz Ürün Sayısı= "+ urunOlumsuzSayisi);
        System.out.println("Notr Ürün Sayısı= "+ urunNotrSayisi);
        

        /*
        //Connection
        String url = "jdbc:mysql://localhost:3306/magaza_degerlendirme?useSSL=false";
        String driver = "com.mysql.jdbc.Driver";
        String username = "root";
        String password = "root";
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, username, password);

        for (int i=0; i<deneme.numInstances(); i++)
        {
            //Sınıflandırmadan önceki verilerin etiket değerleri
            double indeks = deneme.instance(i).classValue();
            String gercekSınıfAdı = deneme.attribute(0).value((int) indeks);
            //Weka'da bayes ile sınıflandırma yapıldıktan sonraki etiket değerleri
            double index = bayes.classifyInstance(deneme.instance(i));
            String wekaSınıfAdı = deneme.attribute(0).value((int) index);
            //System.out.println("Sınıflandırmadan Önce: " + gercekSınıfAdı + " ==> " + wekaSınıfAdı);

            CallableStatement cs = conn.prepareCall("{call spMakaleIlıskı}");
            cs.executeQuery();

            CallableStatement cs1 = conn.prepareCall("{call spMakaleKaydet(?, ?, ?, ?, @kategori_id, @yazar_id, @gazete_id)}");
            cs1.setString(1,wekaSınıfAdı);
            //cs1.registerOutParameter(1, Types.INTEGER);
            cs1.setString(2, (String) kategoriAttr.get(i));
            //cs1.registerOutParameter(2, Types.INTEGER);
            cs1.setString(3, (String) cumleAttr.get(i));
            //cs1.registerOutParameter(3, Types.INTEGER);
       //     cs1.setString(4, (String) icerikAttr.get(i));
            cs1.execute();
        }
        System.out.println("Sonuçlar veritabanına başarıyla kaydedildi...");
        */
    }
    public void magazaKategoriTestEt() throws Exception {
        int magazaOlumluSayisi=0;
        int magazaOlumsuzSayisi=0;
        int magazaNotrSayisi=0;

        BufferedReader denemeReader = new BufferedReader(new FileReader("C:\\Users\\TUNAHAN\\Desktop\\magaza.arff"));
        Instances deneme = new Instances(denemeReader);
        denemeReader.close();

        int sonIndex = deneme.numAttributes() - 1;
        deneme.setClassIndex(sonIndex);

        ArrayList cumleAttr = new ArrayList();
        ArrayList kategoriAttr = new ArrayList();

        for (int i=0; i<deneme.numInstances(); i++)
        {
            cumleAttr.add(deneme.instance(i).stringValue(0));
            kategoriAttr.add(deneme.instance(i).stringValue(1));
            if(deneme.instance(i).stringValue(1).equals("olumlu")){
            	magazaOlumluSayisi++;
            }
            else if(deneme.instance(i).stringValue(1).equals("olumsuz"))
            {
            	magazaOlumsuzSayisi++;
            }
            else if(deneme.instance(i).stringValue(1).equals("notr")){
            	magazaNotrSayisi++;
            }
        }


        //Filtre (String to Word Vector)
        StringToWordVector filtre = new StringToWordVector();
        filtre.setInputFormat(deneme);

        deneme = Filter.useFilter(deneme, filtre);

        NaiveBayes bayes = new NaiveBayes();
        bayes.buildClassifier(deneme);

        //Cross-Validation
        Evaluation eval = new Evaluation(deneme);
        eval.crossValidateModel(bayes, deneme, 10, new Random(1));

        System.out.println(eval.toSummaryString("\nResults\n=====\n", false));
        System.out.println(eval.toMatrixString("\nConfusion Matrix\n=====\n"));

        
        System.out.println("Olumlu magaza Sayısı= "+ magazaOlumluSayisi);
        System.out.println("Olumsuz magaza Sayısı= "+ magazaOlumsuzSayisi);
        System.out.println("Notr magaza Sayısı= "+ magazaNotrSayisi);
        

        /*
        //Connection
        String url = "jdbc:mysql://localhost:3306/magaza_degerlendirme?useSSL=false";
        String driver = "com.mysql.jdbc.Driver";
        String username = "root";
        String password = "root";
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, username, password);

        for (int i=0; i<deneme.numInstances(); i++)
        {
            //Sınıflandırmadan önceki verilerin etiket değerleri
            double indeks = deneme.instance(i).classValue();
            String gercekSınıfAdı = deneme.attribute(0).value((int) indeks);
            //Weka'da bayes ile sınıflandırma yapıldıktan sonraki etiket değerleri
            double index = bayes.classifyInstance(deneme.instance(i));
            String wekaSınıfAdı = deneme.attribute(0).value((int) index);
            //System.out.println("Sınıflandırmadan Önce: " + gercekSınıfAdı + " ==> " + wekaSınıfAdı);

            CallableStatement cs = conn.prepareCall("{call spMakaleIlıskı}");
            cs.executeQuery();

            CallableStatement cs1 = conn.prepareCall("{call spMakaleKaydet(?, ?, ?, ?, @kategori_id, @yazar_id, @gazete_id)}");
            cs1.setString(1,wekaSınıfAdı);
            //cs1.registerOutParameter(1, Types.INTEGER);
            cs1.setString(2, (String) kategoriAttr.get(i));
            //cs1.registerOutParameter(2, Types.INTEGER);
            cs1.setString(3, (String) cumleAttr.get(i));
            //cs1.registerOutParameter(3, Types.INTEGER);
       //     cs1.setString(4, (String) icerikAttr.get(i));
            cs1.execute();
        }
        System.out.println("Sonuçlar veritabanına başarıyla kaydedildi...");
        */
    }


    
    public void kargoModelTest() throws Exception {
	       //günlükMakaleCek();

        System.out.println("Köşe yazıları kategorisine göre test ediliyor... \n");

        BufferedReader reader = new BufferedReader(
                new FileReader("C:\\Users\\TUNAHAN\\Desktop\\kargo.arff"));
        Instances train = new Instances(reader);
        reader.close();
        
        //Test datası okundu.
        BufferedReader testreader = new BufferedReader(
                new FileReader("C:\\Users\\TUNAHAN\\Desktop\\kargoTest.arff"));
        Instances test = new Instances(testreader);
        testreader.close();

        // setting class attribute for test data
        int lastIndex = train.numAttributes() - 1;
        test.setClassIndex(lastIndex);
        // setting class attribute
        train.setClassIndex(lastIndex);

        ArrayList baslikAttr = new ArrayList();
        ArrayList gazeteAttr = new ArrayList();
       // ArrayList yazarAttr = new ArrayList();
       // ArrayList icerikAttr = new ArrayList();
       // ArrayList tarihAttr = new ArrayList();

        for (int i=0; i<test.numInstances(); i++)
        {
            baslikAttr.add(test.instance(i).stringValue(0));
            gazeteAttr.add(test.instance(i).stringValue(1));
         //   yazarAttr.add(test.instance(i).stringValue(2));
       //     icerikAttr.add(test.instance(i).stringValue(3));
        //    tarihAttr.add(test.instance(i).stringValue(4));
        }

        StringToWordVector stwv = new StringToWordVector();
        stwv.setInputFormat(train);


        NaiveBayes bayes = new NaiveBayes();

        // Filtre uygulanıyor
        train = Filter.useFilter(train, stwv);
        test = Filter.useFilter(test, stwv);

        bayes.buildClassifier(train);

        /*
        //Connection
        String url = "jdbc:mysql://localhost:3306/koseyazilari?useSSL=false";
        String driver = "com.mysql.jdbc.Driver";
        String username = "root";
        String password = "root";
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, username, password);


        for(int i = 0; i < test.numInstances(); i++) {
            double index = bayes.classifyInstance(test.instance(i));
            String className = train.attribute(0).value((int)index);
            System.out.println("Sınıflandırma Sonucu: "+className);

            CallableStatement cs = conn.prepareCall("{call spMakaleIlıskı}");
            cs.executeQuery();

            CallableStatement cs1 = conn.prepareCall("{call spMakaleKaydet(?, ?, ?, ?, ?, ?, @kategori_id, @yazar_id, @gazete_id)}");
            cs1.setString(1,className);
            cs1.setString(2, (String) yazarAttr.get(i));
            cs1.setString(3, (String) gazeteAttr.get(i));
            cs1.setString(4, (String) baslikAttr.get(i));
            cs1.setString(5, (String) icerikAttr.get(i));
            cs1.setString(6, (String) tarihAttr.get(i));
            cs1.execute();
        }
        */
        System.out.println("Test işlemi tamamlandı ve sonuçlar veritabanına başarıyla kaydedildi...");

    }

}
