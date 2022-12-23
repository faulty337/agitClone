package com.example.testserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {
    private String msg;
    private int statusCode;
    private Object data;
}
