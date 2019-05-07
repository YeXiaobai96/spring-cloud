package com.cloud.redis.entity.secondary;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Filename: Device
 * @Author: wm
 * @Date: 2019/4/15 8:45
 * @Description:设备
 * @History:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "device")
public class Device {

    @Id
    private String id;
    @Column(name = "additional_info")
    private String additionalInfo;
    @Column(name = "customer_id")
    private String customerId;
    private String type;
    private String name;
    @Column(name = "search_text")
    private String searchText;
    @Column(name = "tenant_id")
    private String tenantId;
}
