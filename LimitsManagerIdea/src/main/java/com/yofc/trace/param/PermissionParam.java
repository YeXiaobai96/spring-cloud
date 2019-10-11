package com.yofc.trace.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Filename: PermissionParam
 * @Author: wm
 * @Date: 2019/9/17 15:38
 * @Description:
 * @History:
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionParam {

    private String uid;

    private String [] roleIds;
}
