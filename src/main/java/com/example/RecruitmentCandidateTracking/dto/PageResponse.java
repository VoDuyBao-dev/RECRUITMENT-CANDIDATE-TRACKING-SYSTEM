package com.example.RecruitmentCandidateTracking.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse<T> {
    private List<T> items;
    private int page;
    private int size;
    private long totalItems;
    private int totalPages;

    // ĐÂY LÀ HÀM QUAN TRỌNG ĐỂ SỬA LỖI "The method of... is undefined"
    public static <T> PageResponse<T> of(List<T> items, int page, int size, long totalItems, int totalPages) {
        return PageResponse.<T>builder()
                .items(items)
                .page(page)
                .size(size)
                .totalItems(totalItems)
                .totalPages(totalPages)
                .build();
    }
}