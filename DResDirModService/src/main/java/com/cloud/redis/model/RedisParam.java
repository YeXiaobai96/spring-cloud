package com.cloud.redis.model;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RedisParam {
    private String hashName;
    private List<?> hashKeys;
    private Object hashKey;
    private Map<Object, Object> hashMap;
    private String hashValue;
    private String keyName;
    private String keyValue;
    private String keyOldName;
    private String keyNewName;
    private String listName;
    private String listValue;
    private String setName;
    private String setValue;
    private String setOtherName;
    private double score;
    private double min;
    private double max;
    private Long start;
    private Long end;


}
