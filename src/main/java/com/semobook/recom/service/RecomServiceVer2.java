package com.semobook.recom.service;

import com.semobook.book.domain.Book;
import com.semobook.book.dto.BookDto;
import com.semobook.book.dto.BookSearchRequest;
import com.semobook.book.dto.Document;
import com.semobook.book.repository.BookRepository;
import com.semobook.book.service.BookService;
import com.semobook.common.SemoConstant;
import com.semobook.common.StatusEnum;
import com.semobook.recom.domain.*;
import com.semobook.recom.dto.RecomResponse;
import com.semobook.recom.repository.*;
import com.semobook.user.dto.UserInfoDto;
import com.semobook.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.StringTools;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecomServiceVer2 {





}