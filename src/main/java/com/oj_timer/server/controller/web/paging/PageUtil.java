package com.oj_timer.server.controller.web.paging;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@Slf4j
public class PageUtil {

    private Page page;
    private List<Integer> pages = new ArrayList<>();
    private boolean isFirst;
    private boolean isLast;
    private boolean hasPreList;
    private boolean hasNextList;

    public PageUtil(Page page) {
        this.page = page;
        setIsFirst();
        setIsLast();
        setHasPreList();
        setHasNextList();
        setPages();
    }

    private void setPages() {
        int number = page.getNumber() == 0 ? 1 : page.getNumber();
        int idx = number / 5;
        // 마지막 페이징 => 마지막 줄에 위치해있거나 또는 첫번째 줄만 있을때
        if (!hasNextList) {
            insertNumsIntoPages(5 * idx, page.getTotalPages() - 1);
            return;
        }
        insertNumsIntoPages(idx * 5, idx * 5 + 4);
    }

    // 첫째 페이징 줄
    private void setHasPreList() {
        int number = page.getNumber() == 0 ? 1 : page.getNumber();
        hasPreList = number / 5 != 0;
    }

    // 마지막 페이징 줄
    private void setHasNextList() {
        int totalPages = page.getTotalPages() == 1 ? 2 : page.getTotalPages();
        int number = page.getNumber() == 0 ? 1 : page.getNumber();
        hasNextList = (totalPages - 1) / 5 != number / 5;
    }

    private void insertNumsIntoPages(int start, int last) {
        for (int i = start; i <= last; i++) {
            pages.add(i);
        }
    }

    public void setIsFirst() {
        isFirst = page.isFirst();
    }

    public void setIsLast() {
        isLast = page.isLast();
    }
}
