package com.example.itemserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDTO<T> {
    private int code;
    private String message;
    private T response;

    public static ResponseDTO authFailed(){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.code = 403;
        responseDTO.message = "Authorization failed";
        return responseDTO;
    }
}
