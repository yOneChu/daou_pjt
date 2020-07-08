package org.kyhslam.controller;

import org.kyhslam.domain.Company;
import org.kyhslam.domain.RelatedSubject;
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
    private SubjectRepository subjectRepository;

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

        subjectRepository.save(subject);


        return new ResponseEntity<>(getListByCompany(company) , HttpStatus.CREATED);
    }

    private List<RelatedSubject> getListByCompany(Company company) throws RuntimeException {

        log.info("======== " + company);
        return subjectRepository.getSubjectOfCompany(company);
    }

}
