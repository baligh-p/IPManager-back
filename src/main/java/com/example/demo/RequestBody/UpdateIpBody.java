package com.example.demo.RequestBody;

import lombok.Data;

@Data
public class UpdateIpBody {
    private long idUser ;
    private long idAddress;
    private String bureau ;
    private String noms ;
    private long idType ;
    private long idMark ;
}
