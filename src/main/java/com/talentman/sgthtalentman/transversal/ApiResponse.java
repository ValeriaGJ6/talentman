package com.talentman.sgthtalentman.transversal;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
    
    private boolean error;
    private String msg;
    private List<Paises> data;
}
