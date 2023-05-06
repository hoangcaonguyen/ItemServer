package com.example.itemserver.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateItemRequest {
    private String name;
    private String className;
    private List<PersonRequest> person;
}
