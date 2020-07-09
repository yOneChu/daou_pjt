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
    private Timestamp regdate; // 생성일

    @UpdateTimestamp
    private Timestamp updateDate; // 수정일

    private Integer user_all; //사용자 총 계정수

    private Integer deleted_user; // 삭제 계정 수

    private Integer use_count; //사용 계정 수

    private String serviceFlag; // 서비스 사용 유무

    private String connectedFlag; //타시스템 연동 여부

    private String connectedSystem; // 연동시스템 종류

    private String connectedId; // 연동ID
/*
	 *
	 {
		"name" : "키움",
		"user_all" : 200,
		"deleted_user" : "20",
		"use_count" : 180,
		"serviceFlag" : "FALSE",
		"connectedFlag" : "TRUE",
		"connectedSystem" : "ERP",
		"connectedId" : "1000"
	}
	 */
}
