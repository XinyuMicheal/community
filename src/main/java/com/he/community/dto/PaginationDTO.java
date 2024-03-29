package com.he.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages  = null;
    private Integer totalPage;

    public void setPagination(Integer totalPage, Integer page) {
        this.totalPage = totalPage;
        this.page = page;
        pages  = new ArrayList<>();
        pages.add(page);
        for(int i = 1;i <= 3; i++){
            if(page - i > 0){
                pages.add(0,page  - i);
            }
            if(page + i <= totalPage){
                pages.add(page + i);
            }
        }
        /*System.out.println(page);*/

        if(page == 1){
            showPrevious = false;
        }else{
            showPrevious = true;
        }

        if(pages.contains(1)){
            showFirstPage = false;
        }else{
            showFirstPage = true;
        }
        /*System.out.println("showPrevious:"+ showPrevious);
        System.out.println("showFirstPage:"+showFirstPage);
        */

        if(page == totalPage){
            showNext = false;
        }else{
            showNext = true;
        }

        if(pages.contains(totalPage)){
            showEndPage = false;
        }else{
            showEndPage = true;
        }
    }
}
