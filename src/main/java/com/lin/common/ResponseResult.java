package com.lin.common;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Xiaolin
 * @desc 统一返回类
 * @date 2023/4/16 19:20
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult<T> {

    //状态码
    private Integer code;

    //返回信息
    private String msg;

    //返回数据
    private T data;

    public ResponseResult(Integer code) {
        this.code = code;
    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
