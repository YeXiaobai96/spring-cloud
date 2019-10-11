package com.yofc.trace.param;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleParam {
	private Integer index;
	private Integer size;
	private String name;
	private String startTime;
	private String endTime;
}
