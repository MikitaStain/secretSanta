package com.innowise.secret_santa.service;

import com.innowise.secret_santa.model.dto.request_dto.PagesDto;
import com.innowise.secret_santa.model.dto.response_dto.PagesDtoResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PagesServiceImpl<T> implements PageService<T>{

    @Override
    public Pageable getPage(PagesDto pages) {
        return PageRequest.of(pages.getPage(),
                pages.getSize(),
                Sort.by(pages.getSort())
        );
    }

    @Override
    public PagesDtoResponse<Object> getPagesDtoResponse(PagesDto pagesDto, List<T> all) {
        return PagesDtoResponse
                .builder()
                .page(pagesDto.getPage())
                .size(pagesDto.getSize())
                .sort(pagesDto.getSort())
                .dto(Arrays.asList((all).toArray()))
                .build();
    }
}