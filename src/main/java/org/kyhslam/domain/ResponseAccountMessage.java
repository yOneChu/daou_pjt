package org.kyhslam.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Setter
@Getter
public class ResponseAccountMessage {

    private int code;
    private boolean status;
    private String message;

    private Company company;

    public ResponseAccountMessage(HttpStatus httpStatus){
        this.code = httpStatus.value();
        this.status = (httpStatus.isError()) ? false:true;
        this.message = httpStatus.getReasonPhrase();
    }



}
