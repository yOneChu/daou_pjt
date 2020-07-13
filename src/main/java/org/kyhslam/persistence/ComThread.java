package org.kyhslam.persistence;

import com.opencsv.CSVReader;
import org.kyhslam.domain.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ComThread implements Runnable {

    private Logger logger = LoggerFactory.getLogger(ComThread.class);

    private File fileDir;
    private String type;
    private String isDir;

    @Autowired
    SaveService service;

    @Autowired
    CompanyRepository comRepo;

    public ComThread(String fileType, String isDir) {
        this.type = fileType;
        this.isDir = isDir;
    }

    @Override
    public void run() {

        try {

            ArrayList<Company> c = new ArrayList<>();
            logger.info("ComThread -- " );
            ArrayList<String[]> list = new ArrayList<String[]>();

            for(File file : new File(isDir).listFiles()){
                logger.info("type -- " + type);
                if(file.getName().contains(type)){

                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(new FileInputStream(file.getAbsolutePath()), "euc-kr"));

                    CSVReader reader = new CSVReader(bufferedReader);
                    logger.info("reader -- " + reader);
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


                    logger.info("list.size() -- " + list.size());
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

                        logger.info("company.getName() === " + company.getName());
                        logger.info("company === " + company);


                        if(company != null){
                            //comRepo.save(company);
                            //service.comSave(company);
                        }

                    }
                }
            } // end for

        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
