package com.readingisgood.bookordermanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    private Long id;

    @Transient
    public static final String SEQUENCE_NAME = "customer_sequence";

    private String name;

    private String surname;

    @Indexed(unique = true)
    private String email;

    private String gsm;

    private String address;

    @Builder.Default
    private Date createdDate = Date.from(Instant.now());



}
