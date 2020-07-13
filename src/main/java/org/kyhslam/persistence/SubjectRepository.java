package org.kyhslam.persistence;


import org.kyhslam.domain.Company;
import org.kyhslam.domain.RelatedSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectRepository extends JpaRepository<RelatedSubject, Long> {

    @Query("SELECT r FROM RelatedSubject r WHERE r.company = ?1 " +
            " AND r.bno > 0 ORDER BY r.bno ASC")
    public List<RelatedSubject> getSubjectOfCompany(Company company);


    public RelatedSubject findByRelatedCode(Integer code);

    //계정코드 다음값 구하기
    @Query("SELECT (max(a.accountCode) + 1) FROM RelatedSubject a")
    public Integer getAccountNextValCode();

}
