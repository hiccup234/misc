package com.hiccup;

import java.util.*;

public class MainTest {

    public static void main(String[] args) {

        Object obj = new Object();


        String str = "ffffsdsdf@abc";
        System.out.println(str.replaceAll("@abc", "HH"));

        Random random = new Random(47);
        Long startTime = System.currentTimeMillis();
        List<Long> userIds = new LinkedList<>();
        for(int i=0; i<200; i++) {
            userIds.add((long)random.nextInt(10));
//            userIds.add(count++);
        }
        //======================================
        int batchSize = 100;
        int userIdCount = userIds.size();
        int batchCount = userIdCount / batchSize;
        // 考虑边界问题
        if(0 != userIdCount % batchSize) {
            batchCount++;
        }

//        HashMap
        HashSet set = new HashSet();
        set.hashCode();
        List batchList = null;
        for(int i=0; i<batchCount; i++) {
            if(i != batchCount-1) {
                batchList = userIds.subList(i*batchSize, (i+1)*batchSize);
            } else {
                batchList = new LinkedList();
                // 这个时候i已经被递增了
                for(int j=i*batchSize; j<userIdCount; j++) {
                    batchList.add(userIds.get(j));
                }
            }
            if(0 != batchList.size()) {
                // 这里做批量更新
                System.out.println(batchList);

                String batchUpdateUserSegmentSql = "UPDATE `user_account` SET `segment` = 2 WHERE `id` IN (";
                StringBuilder sql = new StringBuilder(batchUpdateUserSegmentSql);
                Object[] updateArgs = new Object[batchList.size()];
                for (int k = 0; k < batchList.size(); k++) {
                    sql.append("?, ");
                    updateArgs[k] = batchList.get(k);
                }
                sql.delete(sql.length() - 2, sql.length());
                sql.append(") ");
//                return getJdbcTemplate().update(sql.toString(), args);
                System.out.println(sql.toString());
            }
        }

        Long endTime = System.currentTimeMillis();
        System.out.println(endTime-startTime);

    }
}
