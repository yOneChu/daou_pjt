package org.kyhslam.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kyhslam.domain.Company;
import org.kyhslam.persistence.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyTest {

    @Autowired
    CompanyRepository companyRepository;

    //@Test
    public void selectAll(){

        companyRepository.findAll().forEach(System.out::println);
    }

    @Test
    public void selectOneTest(){

        Long bno = 2L;
        Company company = companyRepository.findById(bno).get();
        System.out.println(company.getName());
        
    }

}
