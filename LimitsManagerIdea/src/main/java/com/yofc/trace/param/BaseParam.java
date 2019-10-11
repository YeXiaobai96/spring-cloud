package com.yofc.trace.param;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 
* @ClassName: BaseParam 
* @Description: TODO(分页基础参数类) 
* @author wm
* @date 2019年1月16日 上午9:06:43 
*  
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseParam {
	private Integer index;
	private Integer size;
	private String name;
	private String pondId;
	private String beginTime;
	private String endTime;
	//赋码查询中收成种类
	private String harvestTypeId;
}
