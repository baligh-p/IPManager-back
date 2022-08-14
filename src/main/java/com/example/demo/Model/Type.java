package com.example.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idType ;
    private String typeName;

    @ManyToMany
    @JoinTable(name = "Types_Marks_Assoc",
            joinColumns = @JoinColumn( name = "idType" ),
            inverseJoinColumns = @JoinColumn( name = "idMark" ))
    private Collection<Mark> marks = new ArrayList<>();

    @OneToMany(targetEntity = IPAddress.class , mappedBy = "type")
    private Collection<IPAddress> ipAddress = new ArrayList<>();


    @CreationTimestamp
    private Date createdAt ;
}