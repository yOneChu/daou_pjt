package org.kyhslam.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;


// REST방식에서는 URL이 콘텐츠 자체를 의미하기 때문에 콘텐츠에 대한 어떤 작업을
// 할 것인가는 GET/POST/PUT/DELETE등의 전송방식을 이용해서 처리한다.
// GET: 특정 리소스를 조회하는 용도 , /product/123
// POST : 특정 리소스를 생성하는 용도 , /products or /member/123
// PUT : 특정 리소스를 수정
// DELETE : 특정 리소스를 삭제

// POST는 특별한 작업을 처리하는 방식 : 정보의 가공이 목적, 정확한 목저을 가지고 특정한 작업을 수행하기 위해 사용

@Entity
@Table(name = "go_account_subject")
@Setter
@Getter
public class RelatedSubject {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String accountName; // 계정체계

    private String accountCode; // 계정코드

    //불필요하게 양쪽 테이블을 조회하지 않도록 양쪽 모두 '지연로딩' 방식으로 설정
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company; // 회사코드

    @CreationTimestamp
    private Timestamp regdate; // 생성일

    @UpdateTimestamp
    private Timestamp updatedate; // 수정일
}
