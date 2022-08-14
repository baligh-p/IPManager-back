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
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idMark;
    @Column(unique = true)
    private String markName;

    @OneToMany(targetEntity = IPAddress.class , mappedBy = "mark")
    private Collection<IPAddress> ipAddress = new ArrayList<>();

    @CreationTimestamp
    private Date createdAt ;
}
