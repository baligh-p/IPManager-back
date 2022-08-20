package com.example.demo.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idHistory ;
    @ManyToOne @JoinColumn(name = "id", nullable = false)
    private AppUser user ;
    private String typeOperation ;
    private String address ;
    private String bureau ;
    private String direction ;
    @CreationTimestamp
    private Date createdAt ;
}
