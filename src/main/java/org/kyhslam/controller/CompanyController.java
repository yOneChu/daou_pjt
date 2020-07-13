package org.kyhslam.controller;

import com.opencsv.CSVReader;
import org.kyhslam.domain.Company;
import org.kyhslam.domain.RelatedSubject;
import org.kyhslam.domain.ResponseAccountMessage;
import org.kyhslam.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/account/*")
public class CompanyController {

    Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    CompanyRepository comRepo;

    @Autowired
    SubjectRepository subRepo;


    // 회사 조회 (계정과목 포함)
    @GetMapping("/{bno}/list")
    public ResponseAccountMessage list(@PathVariable("bno") Long bno){

        Company company = comRepo.findById(bno).get();
        company.setSubjectList(subRepo.getSubjectOfCompany(company));

        ResponseAccountMessage message = new ResponseAccountMessage(HttpStatus.OK);
        message.setCompany(company);

        logger.info("message === "  +message);

        return message;
    }

    /*private List<RelatedSubject> getListByCompany(Company company) throws RuntimeException {
        logger.info("======== " + company);
        return subRepo.getSubjectOfCompany(company);
    }*/


    //회사 수정
    @PutMapping("/{bno}/update")
    public ResponseAccountMessage update(@PathVariable("bno") Long bno, @RequestBody Company company) {

        logger.info("bno === "  +bno);

        ResponseAccountMessage message = new ResponseAccountMessage(HttpStatus.OK);
        Company com = comRepo.findById(bno).get();

        com.setName(company.getName()); // 회사명
        com.setConnectedFlag(company.getConnectedFlag()); // 타시스템 연동 여부
        com.setServiceFlag(company.getServiceFlag()); // 서비스 사용 유무
        comRepo.save(com);

        com = comRepo.findById(bno).get();

        message.setCompany(com);
        return message;
    }

    //회사 추가
    @PostMapping("/add")
    public ResponseAccountMessage add(@RequestBody Company company) {
        logger.info("company === "  +company);

        ResponseAccountMessage message = new ResponseAccountMessage(HttpStatus.CREATED);
        comRepo.save(company);
        message.setCompany(company);

        return message;
    }

    @DeleteMapping("/{bno}/delete")
    public void delete(@PathVariable("bno") Long bno) {
        ResponseAccountMessage message = new ResponseAccountMessage(HttpStatus.OK);

        Company com = comRepo.findById(bno).get();
        comRepo.delete(com);
        return;
    }

    @GetMapping("/upload")
    public void upload() {

        suThread a = new suThread("account", "C:\\csvFile");
        ccThread b = new ccThread("company", "C:\\csvFile");
        

        Thread t1 = new Thread(a, "sub");
        Thread t2 = new Thread(b, "com");
        t1.start();
        t2.start();

        return;
    }


    public class suThread extends Thread {

        private String type;
        private String isDir;


        public suThread(String fileType, String isDir) {
            this.type = fileType;
            this.isDir = isDir;
        }

        @Override
        public void run() {
            try {

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

                        for(int i=1; i < 1000; i++) {
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

                            logger.info("a[9] == " + a[9]);
                            String aa = a[9];
                            if(aa == null || "".equals(a[9])){

                            }else{
                                subject.setRelatedCode( Integer.parseInt(a[9]) ); //관계코드
                            }
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


    public class ccThread implements Runnable {

        private File fileDir;
        private String type;
        private String isDir;


        public ccThread(String fileType, String isDir) {
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
                                comRepo.save(company);
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
}




