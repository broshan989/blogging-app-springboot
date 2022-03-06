package com.blog.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PaginatedPostData {
    private List<PostDto> postList;
    private int pageNo;
    private int pageSize;
    private int totalPages;
    private long totalElements;
    private boolean isLastPage;

}
