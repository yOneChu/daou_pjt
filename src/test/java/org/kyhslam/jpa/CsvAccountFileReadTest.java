package org.kyhslam.jpa;

import com.opencsv.CSVReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kyhslam.domain.Company;
import org.kyhslam.domain.RelatedSubject;
import org.kyhslam.persistence.CompanyRepository;
import org.kyhslam.persistence.SubjectRepository;
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
public class CsvAccountFileReadTest {


    @Autowired
    SubjectRepository subRepo;

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

                    if(file.getName().contains("account")){

                        System.out.println("file name = === " + file.getName());
                        BufferedReader bufferedReader = new BufferedReader(
                                new InputStreamReader(new FileInputStream(file.getAbsolutePath()), "euc-kr"));

                        CSVReader reader = new CSVReader(bufferedReader);

                        String[] nextLine;

                        while( (nextLine = reader.readNext()) != null ){

                            String[] data = new String[nextLine.length];
                            data[4] = nextLine[4]; // 계정체계
                            data[5] = nextLine[5]; // 계정코드
                            data[6] = nextLine[6]; // 과목명(세목)
                            data[7] = nextLine[7]; // 과목명(목)
                            data[8] = nextLine[8]; // 분류관계
                            data[9] = nextLine[9]; // 코드관계
                            data[10] = nextLine[10]; // 계정과목명
                            data[11] = nextLine[11]; // 회사코드

                            list.add(data);
                        } // end while

                        System.out.println(list.size());
                    }
                }

            } // for file

            System.out.println("size ======" + list.size());


            if(list != null && list.size() > 0){

            }

            if(list != null && list.size() > 0){
                for(int i=1; i < list.size(); i++){
                    String[] a = list.get(i);
                    RelatedSubject subject = new RelatedSubject();

                    subject.setAccountName( a[4] == null ? "" : a[4] ); // 계정체계
                    subject.setAccountCode( a[5] ); // 계정코드
                    subject.setSubjectName( a[6] ); // 과목명(세목)
                    subject.setSubject( a[7] ); // 과목명(목)



                    subject.setType( a[8] == null ? "" : a[8] ); // 분류관계
                    subject.setRelatedCode( a[9] == null ? "" : a[9] ); // 코드관계
                    subject.setRelatedName( a[10] == null ? "" : a[10] ); // 계정과목명



                    if(a[11].trim().equals("3")){

                    }else{
                        System.out.println("no === " + a[11]);
                        Long bno = Long.parseLong(a[11] );
                        System.out.println("111 == " + bno);

                        subject.setCompany( comRepo.findById(bno).get() ); // 회사코드

                        System.out.println("set");
                        subRepo.save(subject);
                    }
                }
            }// end if

            System.out.println();
            System.out.println(list.size());

        } catch (Exception e){
            e.printStackTrace();;
        }



    }


}
