package com.semobook.bookReview.dto;

import com.semobook.bookReview.domain.BookReview;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class BookReviewRequest {
    BookReview bookReview;
    int startPage;
    int endPage;
}
