package com.hiccup.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author wenhy
 * @date 2018年4月18日
 * @description 
 */
public class BatchOperatingForList {

	interface Command<E> {
		void execute(List<E> list);
	}

	/**
	 * 通过策略模式来实现对整个list做批量操作，不用关心边界问题
	 * @param list
	 * @param batchSize
	 * @param command
	 * @param <T>
	 */
	public static <T> void batchOperating(List<T> list, int batchSize, Command<T> command) {
		if(!(null != list && list.size() > 0)) {
			return ;
		}
		int listSize = list.size();
		int batchCount = listSize/batchSize;
		if(0 != listSize%batchSize) {
		    batchCount++;
		}
		List<T> batchList = null;
		for(int i=0; i<batchCount; i++) {
		    if(i != batchCount-1) {
		        batchList = list.subList(i*batchSize, (i+1)*batchSize);
		    } else {
			    batchList = list.subList(i*batchSize, listSize);
		    }
			if(null != batchList && 0 != batchList.size()) {
				command.execute(batchList);
			}
		}
	}


	public static void main(String[] args) {
		List<Long> list = new ArrayList<>(2000);
		Random rand = new Random(47);
		for(int i=0; i<200; i++) {
//			list.add(Long.valueOf(rand.nextInt(1000)));
			list.add(Long.valueOf(i+1001));
		}
		batchOperating(list, 10, new Command<Long>() {
			@Override
			public void execute(List<Long> list) {
				for(Long l : list) {
					System.out.print(l + " ");
				}
				System.out.println();
			}
		});
	}

}
