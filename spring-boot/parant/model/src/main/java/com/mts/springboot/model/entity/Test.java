package com.mts.springboot.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Filename: Test
 * @Author: wm
 * @Date: 2019/9/2 15:36
 * @Description:
 * @History:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Test {

    private Integer id;
    private Integer age;
}
