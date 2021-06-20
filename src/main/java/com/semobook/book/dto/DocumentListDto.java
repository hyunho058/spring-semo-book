package com.semobook.book.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class DocumentListDto {
    private ArrayList<Document> documents = new ArrayList<>();
}
