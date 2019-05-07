package com.cloud.redis.entity.primary;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "node_page")
public class NodePage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String pid;

    private String nodeId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp alertTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp uploadTime;

    private String name;

    private String summary;

    private String source;

    private String dataSize;

    private String description;

    private String structure;

    private String example;

    private String other;

    private String dataLinkInfo;

    private Integer isDel;

    private String storageType;

    private String updateMode;

    private String updateFreq;

    private String paramNum;

    private String paramExplain;

    private String reqType;

    private String inParamNum;

    private String dataFormat;

    private String inParamExplain;

    private String returnValue;

    private String returnExample;

    private String category;

    private String redisKey;

    private String tableName;

    private String isExistData;

    private String associatedInfo;

    private String additional;

}
