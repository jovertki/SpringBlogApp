package com.example.docusketch_blog.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
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
