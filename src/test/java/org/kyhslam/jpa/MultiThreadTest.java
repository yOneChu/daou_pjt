package org.kyhslam.jpa;

import com.opencsv.CSVReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kyhslam.domain.Company;
import org.kyhslam.domain.RelatedSubject;
import org.kyhslam.persistence.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MultiThreadTest {


    String isDir = "C:\\csvFile";

    @Autowired
    CompanyRepository comRepo;


    @Test
    public void ThreadTest() throws Exception {

        comThread a = new comThread("company","C:\\csvFile");
        subThread b = new subThread("account","C:\\csvFile");

        Thread t1 = new Thread(a, "com");
        Thread t2 = new Thread(b, "sub");


        t1.start();
        t2.start();




    }


}


class comThread extends Thread {

    private String type;
    private String isDir;

    comThread(String fileType, String isDir){
        this.type = fileType;
        this.isDir = isDir;
    }

    @Override
    public void run() {

        try {

            for(File file : new File(isDir).listFiles()){
                System.out.println(file.getName());
                System.out.println(file.getAbsolutePath());

                if(file.getName().contains(type)){

                    System.out.println("com");
                    ArrayList<String[]> list = new ArrayList<String[]>();
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(new FileInputStream(file.getAbsolutePath()), "euc-kr"));
                    System.out.println("com22");
                    CSVReader reader = new CSVReader(bufferedReader);
                    System.out.println("reader == " + reader);
                    String[] nextLine;
                    System.out.println("2222222222222");
                    while( (nextLine = reader.readNext()) != null ){
                        String[] data = new String[nextLine.length];
                        System.out.println(nextLine[4]);
                        data[4] = nextLine[4];
                        data[5] = nextLine[5];
                        data[6] = nextLine[6];
                        data[7] = nextLine[7];
                        data[8] = nextLine[8];
                        data[9] = nextLine[9];
                        data[10] = nextLine[10];
                        data[11] = nextLine[11]; // 도메인명

                        list.add(data);
                    }// end while

                    System.out.println("22222222222222");
                    System.out.println(list.size());
                    for(int i=1; i < list.size(); i++) {
                        String[] a = list.get(i);
                        Company company = new Company();

                        company.setName( a[4] == null ? "" : a[4] );
                        company.setUser_all( Integer.parseInt(a[5]) );
                        company.setDeleted_user( Integer.parseInt(a[6]) );
                        company.setUse_count( Integer.parseInt(a[7])) ;

                        company.setServiceFlag(a[8] == null ? "" : a[8]);
                        company.setConnectedFlag(a[9] == null ? "" : a[9]);
                        company.setConnectedSystem(a[10] == null ? "" : a[10]);

                        System.out.println(a[4] );

                        //comRepo.save(company);


                    }




                }
            } // end for




        } catch (Exception e){
            e.printStackTrace();
        }


    }
}

class subThread extends Thread {

    private String type;
    private String isDir;

    subThread(String fileType, String isDir){
        this.type = fileType;
        this.isDir = isDir;
    }

    @Override
    public void run() {
       /* for(int i=1000; i < 2000; i++){
            System.out.println(i);
        }*/

        try {

            for(File file : new File(isDir).listFiles()){
                if(file.getName().contains(type)){
                    ArrayList<String[]> list = new ArrayList<String[]>();
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(new FileInputStream(file.getAbsolutePath()), "euc-kr"));

                    CSVReader reader = new CSVReader(bufferedReader);
                    String[] nextLine;

                    while( (nextLine = reader.readNext()) != null ){
                        String[] data = new String[nextLine.length];
                        data[4] = nextLine[4];
                        data[5] = nextLine[5];
                        data[6] = nextLine[6];
                        data[7] = nextLine[7];
                        data[8] = nextLine[8];
                        data[9] = nextLine[9];
                        data[10] = nextLine[10];
                        data[11] = nextLine[11]; // 도메인명

                        list.add(data);
                    }// end while

                    for(int i=1; i < list.size(); i++) {
                        String[] a = list.get(i);
                        RelatedSubject subject = new RelatedSubject();

                       /* subject.setName( a[4] == null ? "" : a[4] );
                        subject.setUser_all( Integer.parseInt(a[5]) );
                        subject.setDeleted_user( Integer.parseInt(a[6]) );
                        subject.setUse_count( Integer.parseInt(a[7])) ;

                        subject.setServiceFlag(a[8] == null ? "" : a[8]);
                        subject.setConnectedFlag(a[9] == null ? "" : a[9]);
                        subject.setConnectedSystem(a[10] == null ? "" : a[10]);*/

                        System.out.println(a[4] );

                    }
                }
            } // end for


        } catch (Exception e){
            e.printStackTrace();
        }

    }
}

