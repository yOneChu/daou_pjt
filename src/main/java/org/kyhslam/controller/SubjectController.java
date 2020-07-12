package org.kyhslam.controller;

import org.kyhslam.domain.Company;
import org.kyhslam.domain.RelatedSubject;
import org.kyhslam.domain.ResponseSubject;
import org.kyhslam.persistence.CompanyRepository;
import org.kyhslam.persistence.SubjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/subject/*")
public class SubjectController {

    Logger log = LoggerFactory.getLogger(SubjectController.class);

    @Autowired
    private SubjectRepository subRepo;

    @Autowired
    private CompanyRepository comRepo;

    // 계정 추가 -> 조회
    @Transactional
    @PostMapping("/{bno}")
    public ResponseEntity<List<RelatedSubject>> addSubject(
            @PathVariable("bno") Long bno, @RequestBody RelatedSubject subject)
    {

        log.info("bno === " + bno);
        log.info("subject === " + subject);

        Company company = new Company();
        company.setBno(bno);


        subject.setCompany(company);

        subRepo.save(subject);
        return new ResponseEntity<>(subRepo.getSubjectOfCompany(company) , HttpStatus.CREATED);
    }


    //수정
    @PutMapping("/{bno}/update")
    public ResponseSubject update(@PathVariable("bno") Long bno, @RequestBody RelatedSubject subject) {

        ResponseSubject message = new ResponseSubject(HttpStatus.OK);

        RelatedSubject sub =	subRepo.findById(bno).get();
        sub.setAccountName(subject.getAccountName());
        sub.setAccountCode(subject.getAccountCode());

        subRepo.save(sub);

        message.setSubject(sub);

        return message;
    }

    @DeleteMapping("/{bno}/delete")
    public void delete(@PathVariable("bno") Long bno) {
        RelatedSubject sub =	subRepo.findById(bno).get();

        subRepo.delete(sub);
        return;
    }
}
