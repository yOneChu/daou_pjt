package org.kyhslam.jpa;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.kyhslam.domain.Company;
import org.kyhslam.domain.RelatedSubject;
import org.kyhslam.persistence.CompanyRepository;
import org.kyhslam.persistence.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubjectTest {

    @Autowired
    SubjectRepository subRepo;

    @Autowired
    CompanyRepository comRepo;

    @Test
    public void testInsertSubject(){

        Company com = new Company();
        com.setName("daou");

        RelatedSubject subject = new RelatedSubject();
        subject.setCompany(com);
        subject.setAccountCode("13100");

        comRepo.save(com);
        subRepo.save(subject);


    }


}
