package org.kyhslam.persistence;

import com.opencsv.CSVReader;
import org.kyhslam.domain.Company;
import org.kyhslam.domain.RelatedSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class SubThread extends Thread {
    private Logger logger = LoggerFactory.getLogger(SubThread.class);

    private String type;
    private String isDir;

    @Autowired
    CompanyRepository comRepo;

    @Autowired
    SubjectRepository subRepo;

    public SubThread(String fileType, String isDir) {
        this.type = fileType;
        this.isDir = isDir;
    }

    @Override
    public void run() {
        try {

            logger.info("SubThread start == ");
             Integer nextVal    =   subRepo.getAccountNextValCode();

            logger.info("isDir == " + isDir);
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

                    for(int i=1; i < 10; i++) {
                        String[] a = list.get(i);
                        RelatedSubject subject = new RelatedSubject();

                        subject.setAccountName( a[4] ); //계정체계


                        // nextVal
                        if(a[5] == null || a[5].equals("")){
                            subject.setAccountCode( nextVal ); //계정코드
                            nextVal++;
                        }else{
                            subject.setAccountCode( Integer.parseInt(a[5]) ); //계정코드
                        }

                        subject.setSubjectName( a[6]);                  // 고목명(세목)
                        subject.setSubject( a[7] == null ? "" : a[7] ); //과목명(목)
                        subject.setType( a[8] == null ? "" : a[8] ); //분류
                        subject.setRelatedCode( a[9] == null ? 0 : Integer.parseInt(a[9]) ); //관계코드
                        subject.setRelatedName( a[10] == null ? "" : a[10] ); //관계계정과목명

                        Long bno = Long.parseLong(a[11]);
                        Company com = comRepo.findById(Long.parseLong(a[11])).get();
                        subject.setCompany(com);

                        subRepo.save(subject);

                    }
                }
            } // end for


        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
