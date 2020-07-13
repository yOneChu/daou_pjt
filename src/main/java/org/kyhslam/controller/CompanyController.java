package org.kyhslam.controller;

import org.kyhslam.domain.Company;
import org.kyhslam.domain.RelatedSubject;
import org.kyhslam.domain.ResponseAccountMessage;
import org.kyhslam.persistence.ComThread;
import org.kyhslam.persistence.CompanyRepository;
import org.kyhslam.persistence.SubThread;
import org.kyhslam.persistence.SubjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        SubThread a = new SubThread("account", "C:\\csvFile");
        ComThread b = new ComThread("company", "C:\\csvFile");


        //Integer aa = subRepo.getAccountNextValCode();
        //System.out.println(aa);


        //Thread t1 = new Thread(a, "sub");
        Thread t2 = new Thread(b, "com");
        //t1.start();
        t2.start();

        return;
    }
}
