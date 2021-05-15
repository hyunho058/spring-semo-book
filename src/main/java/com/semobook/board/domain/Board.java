package com.semobook.board.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
public class Board {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String contents;

    @Builder
    public Board(long id, String name, String contents){
        this.id = id;
        this.name = name;
        this.contents = contents;
    }

}
