package com.cloud.redis.model;

import lombok.*;

/**
 * 返回体报文实体类
 *
 * @author ZX
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Result {

    /**
     * 状态值：true 成功；false：失败
     */
    private boolean flag;
    /**
     * 错误编码
     */
    private Integer code;
    /**
     * 错误信息 ：若flag为true时，为success
     */
    private String msg;
    /**
     * 返回体报文的出参，使用泛型兼容不同的类型
     */
    private Object data;

}

