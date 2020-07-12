package org.kyhslam.persistence;


import com.opencsv.CSVReader;
import org.kyhslam.domain.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CsvFileReadService {


    Logger logger = LoggerFactory.getLogger(CsvFileReadService.class);


    @Autowired
    CompanyRepository companyRepository;


    public void compayFileRead(String isDir) throws Exception {

        ArrayList<String[]> list = new ArrayList<>();

        logger.info("list == " + list);

        try{

            for(File file : new File(isDir).listFiles()){
                if(file.isFile()){


                    logger.info("file.getAbsoluteFile() == " + file.getAbsoluteFile());
                    logger.info("file.getName() == " + file.getName());

                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(new FileInputStream(file.getAbsolutePath()), "euc-kr"));

                    CSVReader reader = new CSVReader(bufferedReader);

                    String[] nextLine;

                    while( (nextLine = reader.readNext()) != null ){

                        logger.info("1 == " + nextLine[1]);
                        logger.info("2 == " + nextLine[2]); // 수정일
                        logger.info("3 == " + nextLine[3]); // 삭제일
                        logger.info("4 == " + nextLine[4]); // 회사명
                        logger.info("5 == " + nextLine[5]); // 사용자 계정수

                        logger.info("6 == " + nextLine[6]); // 삭제 계정수
                        logger.info("7 == " + nextLine[7]);// 실제 사용자 수
                        logger.info("8 == " + nextLine[8]); //서비스 사용유무
                        logger.info("9 == " + nextLine[9]); //연동여부
                        logger.info("10 == " + nextLine[10]); // 연동id
                        logger.info("11 == " + nextLine[11]); // 도메인명*/

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
                    }

                }
            } // end for file


            for(int i=1; i < list.size(); i++) {
                String[] a = list.get(i);
                Company company = new Company();

                for(int j=4; j < a.length; j++){
                    System.out.println(a[j]);


                    company.setName( a[4] == null ? "" : a[4] );
                    company.setUser_all( Integer.parseInt(a[5]) );
                    company.setDeleted_user( Integer.parseInt(a[6]) );
                    company.setUse_count( Integer.parseInt(a[7])) ;

                    company.setServiceFlag(a[8] == null ? "" : a[8]);
                    company.setConnectedFlag(a[9] == null ? "" : a[9]);
                    company.setConnectedSystem(a[10] == null ? "" : a[10]);
                }

                companyRepository.save(company);

            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void fileRead(String isDir) throws Exception {

        ArrayList<String[]> list = new ArrayList<>();

        logger.info("list == " + list);

        try{

            for(File file : new File(isDir).listFiles()){
                if(file.isFile()){


                    logger.info("file.getAbsoluteFile() == " + file.getAbsoluteFile());
                    logger.info("file.getName() == " + file.getName());


                    if(file.getName().contains("account")){

                        subjectInsert(file);

                    }else{

                        companyInsert(file);
                    }

                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(new FileInputStream(file.getAbsolutePath()), "euc-kr"));

                    CSVReader reader = new CSVReader(bufferedReader);

                    String[] nextLine;

                    while( (nextLine = reader.readNext()) != null ){

                        logger.info("1 == " + nextLine[1]);
                        logger.info("2 == " + nextLine[2]); // 수정일
                        logger.info("3 == " + nextLine[3]); // 삭제일
                        logger.info("4 == " + nextLine[4]); // 회사명
                        logger.info("5 == " + nextLine[5]); // 사용자 계정수

                        logger.info("6 == " + nextLine[6]); // 삭제 계정수
                        logger.info("7 == " + nextLine[7]);// 실제 사용자 수
                        logger.info("8 == " + nextLine[8]); //서비스 사용유무
                        logger.info("9 == " + nextLine[9]); //연동여부
                        logger.info("10 == " + nextLine[10]); // 연동id
                        logger.info("11 == " + nextLine[11]); // 도메인명*/

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
                    }

                }
            } // end for file


          /*  for(int i=1; i < list.size(); i++) {
                String[] a = list.get(i);
                Company company = new Company();

                for(int j=4; j < a.length; j++){
                    System.out.println(a[j]);


                    company.setName( a[4] == null ? "" : a[4] );
                    company.setUser_all( Integer.parseInt(a[5]) );
                    company.setDeleted_user( Integer.parseInt(a[6]) );
                    company.setUse_count( Integer.parseInt(a[7])) ;

                    company.setServiceFlag(a[8] == null ? "" : a[8]);
                    company.setConnectedFlag(a[9] == null ? "" : a[9]);
                    company.setConnectedSystem(a[10] == null ? "" : a[10]);
                }

                companyRepository.save(company);

            }*/


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void companyInsert(File file){


        try {
            ArrayList<String[]> list = new ArrayList<>();

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file.getAbsolutePath()), "euc-kr"));

            CSVReader reader = new CSVReader(bufferedReader);

            String[] nextLine;

            while( (nextLine = reader.readNext()) != null ){

                logger.info("1 == " + nextLine[1]);
                logger.info("2 == " + nextLine[2]); // 수정일
                logger.info("3 == " + nextLine[3]); // 삭제일
                logger.info("4 == " + nextLine[4]); // 회사명
                logger.info("5 == " + nextLine[5]); // 사용자 계정수

                logger.info("6 == " + nextLine[6]); // 삭제 계정수
                logger.info("7 == " + nextLine[7]);// 실제 사용자 수
                logger.info("8 == " + nextLine[8]); //서비스 사용유무
                logger.info("9 == " + nextLine[9]); //연동여부
                logger.info("10 == " + nextLine[10]); // 연동id
                logger.info("11 == " + nextLine[11]); // 도메인명*/

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
            }
        }catch (Exception e){

        }
    }

    
    
    
    // Account 파일
    public void subjectInsert(File file){

        try {
            ArrayList<String[]> list = new ArrayList<>();

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file.getAbsolutePath()), "euc-kr"));

            CSVReader reader = new CSVReader(bufferedReader);
            String[] nextLine;

            while( (nextLine = reader.readNext()) != null ){

                logger.info("1 == " + nextLine[1]);
                logger.info("2 == " + nextLine[2]); // 수정일
                logger.info("3 == " + nextLine[3]); // 삭제일
                logger.info("4 == " + nextLine[4]); // 회사명
                logger.info("5 == " + nextLine[5]); // 사용자 계정수

                logger.info("6 == " + nextLine[6]); // 삭제 계정수
                logger.info("7 == " + nextLine[7]);// 실제 사용자 수
                logger.info("8 == " + nextLine[8]); //서비스 사용유무
                logger.info("9 == " + nextLine[9]); //연동여부
                logger.info("10 == " + nextLine[10]); // 연동id
                logger.info("11 == " + nextLine[11]); // 도메인명*/

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
            }
        }catch (Exception e){

        }
    }

}
