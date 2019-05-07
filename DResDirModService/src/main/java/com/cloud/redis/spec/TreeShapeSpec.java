package com.cloud.redis.spec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.cloud.redis.entity.primary.TreeShape;

public class TreeShapeSpec {
    public static Sort buildSort() {
        return new Sort(Sort.Direction.ASC, "id");
    }

    public static Specification<TreeShape> isPidEqual(String pid) {
        if (pid == null) {
            return null;
        }
        return (Root<TreeShape> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> builder.equal(root.get("pid"),
                pid);
    }

    public static Specification<TreeShape> isNodeIdEqual(String nodeId) {
        if (nodeId == null) {
            return null;
        }
        return (Root<TreeShape> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> builder
                .equal(root.get("nodeId"), nodeId);
    }

    public static Specification<TreeShape> isDel(Integer isdel) {
        return (Root<TreeShape> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> builder.equal(root.get("isDel"), isdel);
    }
}
