package com.example.common;

import lombok.Data;

@Data
public class Page {
    private Long total;
    private Object rows;

    public Page(Long total, Object rows) {
        this.total=total;
        this.rows=rows;
    }
    public static Page date(Long total,Object data) {
        return new Page(total,data);
    }
}
