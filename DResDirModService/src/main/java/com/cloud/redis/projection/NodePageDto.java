package com.cloud.redis.projection;

/**
 * @description: NodePage类的jpa投影
 * @author: ZX
 * @date: 2018/11/15 11:37
 */
public interface NodePageDto {

    String getId();

    String getNodeId();

    String getName();

    String getSummary();

    String getDescription();
}
