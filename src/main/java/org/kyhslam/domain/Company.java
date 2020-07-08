package org.kyhslam.domain;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "go_company")
@Setter
@Getter
public class Company {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;
    
    private String name; // 회사명

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<RelatedSubject> subjectList;

    @CreationTimestamp
    private Timestamp regdate;

    @UpdateTimestamp
    private Timestamp updateDate;

}
