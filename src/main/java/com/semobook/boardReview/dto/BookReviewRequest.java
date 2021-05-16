package com.semobook.boardReview.dto;

import com.semobook.boardReview.domain.BookReview;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookReviewRequest {
    BookReview bookReview;
}
