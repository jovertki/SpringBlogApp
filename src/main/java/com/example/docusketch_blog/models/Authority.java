package com.example.docusketch_blog.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

//@Entity
@Document
@Getter
@Setter
@NoArgsConstructor
public class Authority implements Serializable {

    @Id
    private String name;

    @Override
    public String toString(){
        return "Authority{" +
                "name='" + name +"'" +
                "}";
    }
}
