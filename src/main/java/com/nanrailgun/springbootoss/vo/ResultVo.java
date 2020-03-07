package com.nanrailgun.springbootoss.vo;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResultVo<T> {
    private Integer status_code;
    private String message;
    private T data;
}
