package com.parentapp.activity.dto;

import lombok.Data;

@Data
public class CreateChildRequest {

    private String name;
    private Integer age;
    private String interests;
}
