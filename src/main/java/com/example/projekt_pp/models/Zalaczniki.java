package com.example.projekt_pp.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Base64Utils;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name="zalaczniki")
@Getter
@Setter
@NoArgsConstructor
public class Zalaczniki {

    @Min(0)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "Nazwa")
    private String nazwa;

    @Column(name = "nazwaPliku")
    private String fileName;

    @Lob
    @Column(columnDefinition="BLOB")
    private byte[] fileContent;

    public Zalaczniki(String fileName, byte[] fileContent) {
        this.fileName = fileName;
        this.fileContent = fileContent;
    }
}
