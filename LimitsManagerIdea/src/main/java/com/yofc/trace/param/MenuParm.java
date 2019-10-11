package com.yofc.trace.param;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuParm {

	private String mid;
	private Integer index;
	private Integer size;
	private String name;
	private String code;
}
