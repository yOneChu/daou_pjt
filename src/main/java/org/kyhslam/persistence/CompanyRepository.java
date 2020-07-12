package org.kyhslam.persistence;


import org.kyhslam.domain.Company;
import org.kyhslam.domain.RelatedSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;


public interface CompanyRepository extends JpaRepository<Company,Long> {


    public List<Company> findByName(String name);

    public Collection<Company> findByBno(Long id);


}
