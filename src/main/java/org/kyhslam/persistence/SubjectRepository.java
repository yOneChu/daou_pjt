package org.kyhslam.persistence;


import org.kyhslam.domain.Company;
import org.kyhslam.domain.RelatedSubject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubjectRepository extends CrudRepository<RelatedSubject, Long> {

    @Query("SELECT r FROM RelatedSubject r WHERE r.company = ?1 " +
            " AND r.bno > 0 ORDER BY r.bno ASC")
    public List<RelatedSubject> getSubjectOfCompany(Company company);
}
