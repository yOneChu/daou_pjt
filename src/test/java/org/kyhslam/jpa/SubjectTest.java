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

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubjectTest {

    @Autowired
    SubjectRepository subRepo;

    @Autowired
    CompanyRepository comRepo;

    //@Test
    public void testInsertCompany() {
        Company company = new Company();
        company.setName("bsnc");
        comRepo.save(company);
    }

    //Test
    public void testInsertSubject(){

        Company com = new Company();
        com.setName("daou");

        RelatedSubject subject = new RelatedSubject();
        subject.setCompany(com);
        subject.setAccountCode(13100);

        comRepo.save(com);
        subRepo.save(subject);
    }

    //@Test
    public void selectComapny() {
        Company com = comRepo.findById(1L).get();
        System.out.println(com.getBno());
        System.out.println(com.getName());
        com.setSubjectList(subRepo.getSubjectOfCompany(com));

        System.out.println(com.getSubjectList());

    }

   // @Test
    public void deleteCompany(){

        List<Company> list =  comRepo.findByName("키움증권");

        System.out.println(list.size());
        for(int i=0; i < list.size(); i++){

            Company com =list.get(i);

            comRepo.delete(com);
        }

    }


    //@Test
    public void selectRelatedCode(){
        RelatedSubject subject =  subRepo.findByRelatedCode(100003);
        System.out.println(subject.getAccountName());
        Company company = subject.getCompany();
        System.out.println(company.getName());
    }

    @Test
    public void selectnext(){
        Integer aa =   subRepo.getAccountNextValCode();
        System.out.println(aa);
    }

}
