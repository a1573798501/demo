package open.zzj.demo.demo.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class PaginationDto {

    private List<QuestionDto> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNextPage;
    private boolean showEndPage;

    private Integer totalPages;

    private Integer currentPage;

    private List<Integer> pages = new ArrayList<>();



    public void setPagInation(Integer totalcount, Integer page, Integer size) {

        Integer totalPage;

        if(totalcount%size == 0){
            totalPage = totalcount/size;
        }else {
            totalPage = totalcount/size + 1;
        }


        totalPages = totalPage;

        currentPage = page;
        pages.add(page);

        for (int i = 1;i <=3 ; i++){
            if (page-i>0){
                pages.add(0,page - i);
            }
            if (page + i <= totalPage){
                pages.add(page+i);
            }
        }

        if(page == 1){
            showPrevious = false;
        }else {
            showPrevious = true;
        }

        if (page == totalPage){
            showNextPage = false;
        }else {
            showNextPage = true;
        }

        //判断是否展示跳转到第一页
        if (pages.contains(1)){
            showFirstPage = false;
        }else {
            showFirstPage = true;
        }

        //判断是否展示跳转最后一页
        if (pages.contains(totalPage)){
            showEndPage = false;
        }else {
            showEndPage = true;
        }
    }
}
