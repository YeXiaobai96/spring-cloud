package com.yofc.trace.param;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermiyParam {

	private String parentId;
	private String roleId;
	private String menuIds;
}
