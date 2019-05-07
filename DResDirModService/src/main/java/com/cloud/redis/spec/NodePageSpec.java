package com.cloud.redis.spec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.cloud.redis.entity.primary.NodePage;

public class NodePageSpec {
    public static Sort buildSort() {
        return new Sort(Sort.Direction.ASC, "id");
    }

    public static Specification<NodePage> isPidEqual(String pid) {
        if (pid == null) {
            return null;
        }
        return (Root<NodePage> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> builder.equal(root.get("pid"),
                pid);
    }

    public static Specification<NodePage> isNodeIdEqual(String nodeId) {
        if (nodeId == null) {
            return null;
        }
        return (Root<NodePage> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> builder
                .equal(root.get("nodeId"), nodeId);
    }

    public static Specification<NodePage> isDel(Integer isdel) {
        return (Root<NodePage> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> builder
                .equal(root.get("isDel"), isdel);
    }

    public static Specification<NodePage> isNameLike(String string) {
        return (Root<NodePage> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> builder.like(root.get("name"),
                "%" + string + "%");
    }

    public static Specification<NodePage> isSummaryLike(String summary) {
        return (Root<NodePage> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> builder
                .like(root.get("summary"), "%" + summary + "%");
    }
}
