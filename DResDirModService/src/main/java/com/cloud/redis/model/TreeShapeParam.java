package com.cloud.redis.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TreeShapeParam {
    private String id;
    private Integer level;
    private String pid;
    private String name;
    private String summary;
    private String dataSize;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp alertTime;
    private String source;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp uploadTime;
    private String storageType;
    private String description;
    private List<?> structure;
    private List<?> example;
    private String other;
    private String dataLinkInfo;
    private String treeShape;
    private String nodesId;
    private String treeNodes;

    private String category;

    private String updateMode;
    private String updateFreq;
    private String dataFormat;
    private String paramNum;
    private List<?> paramExplain;

    private String reqType;
    private String inParamNum;
    private List<?> inParamExplain;
    private List<?> returnValue;
    private String returnExample;
    private String tableName;
    private String isExistData;
    private List<?> associatedInfo;
    private List<?> additional;
}
