package org.kyhslam.jpa;


import com.opencsv.CSVReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kyhslam.domain.Company;
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
public class CsvFileReadTest {

    @Autowired
    CompanyRepository comRepo;


    @Test
    public void csvRead(){

        String isDir = "C:\\csvFile";

        try {

            ArrayList<String[]> list = new ArrayList<>();


            for(File file : new File(isDir).listFiles()){

                if(file.isFile()){

                    System.out.println(file.getAbsoluteFile());
                    System.out.println(file.getName());

                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(new FileInputStream(file.getAbsolutePath()), "euc-kr"));

                    CSVReader reader = new CSVReader(bufferedReader);

                    String[] nextLine;



                    while( (nextLine = reader.readNext()) != null ){

    /*                    System.out.println("0 == " + nextLine[0]);
                        System.out.println("1 == " + nextLine[1]); // 생성일
                        System.out.println("2 == " + nextLine[2]); // 수정일
                        System.out.println("3 == " + nextLine[3]); // 삭제일
                        System.out.println("4 == " + nextLine[4]); // 회사명
                        System.out.println("5 == " + nextLine[5]); // 사용자 계정수

                        System.out.println("6 == " + nextLine[6]); // 삭제 계정수
                        System.out.println("7 == " + nextLine[7]);// 실제 사용자 수
                        System.out.println("8 == " + nextLine[8]); //서비스 사용유무
                        System.out.println("9 == " + nextLine[9]); //연동여부
                        System.out.println("10 == " + nextLine[10]); // 연동id
                        System.out.println("11 == " + nextLine[11]); // 도메인명*/

                        System.out.println(" -------------------------- ");

                        String[] data = new String[nextLine.length];
                        data[4] = nextLine[4];
                        data[5] = nextLine[5];
                        data[6] = nextLine[6];
                        data[7] = nextLine[7];
                        data[8] = nextLine[8];
                        data[9] = nextLine[9];
                        data[10] = nextLine[10];
                        data[11] = nextLine[11];

                        list.add(data);
                    } // end while


                    System.out.println(list.size());
                }
            } // for file

            for(int i=1; i < list.size(); i++){

                String[] a = list.get(i);

                Company company = new Company();

                for(int j=4; j < a.length; j++){
                    System.out.println(a[j]);

                    company.setName( a[4] == null ? "" : a[4] );
                    company.setUser_all( Integer.parseInt(a[5]) );
                    company.setDeleted_user( Integer.parseInt(a[6]) );
                    company.setUse_count( Integer.parseInt(a[7]) );

                    company.setServiceFlag( a[8] == null ? "" : a[8] );
                    company.setConnectedFlag( a[9] == null ? "" : a[9] );
                    company.setConnectedSystem( a[10] == null ? "" : a[10] );

                }

                comRepo.save(company);
                System.out.println("===================");


            }

        } catch (Exception e){
            e.printStackTrace();;
        }

    }
}
