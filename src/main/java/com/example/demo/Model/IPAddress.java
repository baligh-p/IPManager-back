package com.example.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class IPAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idAddress;
    @Column(unique = true)
    private String address ;
    private String direction ;
    private String Bureau;
    private String noms ;

    @ManyToOne  @JoinColumn(name = "idType")
    private Type type ;
    @ManyToOne  @JoinColumn(name = "idMark")
    private Mark mark ;

    @CreationTimestamp
    private Date createdAt ;
}
