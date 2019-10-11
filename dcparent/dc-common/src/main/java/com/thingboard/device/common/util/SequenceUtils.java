package com.thingboard.device.common.util;

public class  SequenceUtils {

	private static final SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);

	/**
	 * 增加sequenceId值
	 * 
	 * @return
	 */
	public static long getSeqId() {
		return idWorker.nextId();
	}
	
	public static String getSeqIdString() {
		return String.valueOf(idWorker.nextId());
	}

//	public static void main(String[] args) {
//        Map<Long,Object> map = new HashMap<>();
//        for (int i = 0; i < 10000000; i++){
//        	map.put(getSeqId(), "value");
//        }
//        System.out.println(map.size());
//	}
}
