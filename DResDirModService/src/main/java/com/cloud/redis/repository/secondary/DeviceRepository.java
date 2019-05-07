package com.cloud.redis.repository.secondary;

import com.cloud.redis.entity.secondary.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * @Filename: DeviceRepository
 * @Author: wm
 * @Date: 2019/4/15 8:50
 * @Description:dao
 * @History:
 */
public interface DeviceRepository extends JpaRepository<Device, String>, JpaSpecificationExecutor<Device> {

    /**
     * 获取设备所有类型
     *
     * @return
     */
    @Query(value = "SELECT DISTINCT type FROM device", nativeQuery = true)
    List<String> findDeviceType();

    /**
     * 获取设备name、id
     *
     * @return
     */
    @Query(value = "SELECT id,name,type,search_text as searchtext FROM device", nativeQuery = true)
    List<Map<String, Object>> findDevice();
}
