package com.manhhoach.EofficeFull.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagingRequest {
    private int page = 1;
    private int size = 10;
    private String search;

    public int getSize(){
        return size > 0 ? size: Integer.MAX_VALUE;
    }
}
