package org.kyhslam.domain;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class ResponseSubject {

    private int code;
    private boolean status;
    private String message;

    private RelatedSubject subject;


    public ResponseSubject(HttpStatus httpstatus) {
        this.code = httpstatus.value();
        this.status = (httpstatus.isError()) ? false:true;
        this.message = httpstatus.getReasonPhrase();
    }
}
