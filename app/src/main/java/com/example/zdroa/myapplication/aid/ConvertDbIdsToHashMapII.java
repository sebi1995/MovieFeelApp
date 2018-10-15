package com.example.zdroa.myapplication.aid;

import java.util.HashMap;

/**
 * Created by zdroa on 19/05/2017.
 */
public class ConvertDbIdsToHashMapII {

    public HashMap<Integer, Integer> getHasMap(String STRING_LIST_OF_IDS) {

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < STRING_LIST_OF_IDS.length() / 5; i++) {
            String s_s = STRING_LIST_OF_IDS.substring(i * 5, (i * 5) + 5);

            if ("0000".equals(s_s.substring(0, 4))) {
                map.put(i, Integer.parseInt(s_s.substring(4, 5)));

            } else if ("000".equals(s_s.substring(0, 3))) {
                map.put(i, Integer.parseInt(s_s.substring(3, 5)));

            } else if ("00".equals(s_s.substring(0, 2))) {
                map.put(i, Integer.parseInt(s_s.substring(2, 5)));

            } else if ("0".equals(s_s.substring(0, 1))) {
                map.put(i, Integer.parseInt(s_s.substring(1, 5)));

            } else map.put(i, Integer.parseInt(s_s));
        }
        return map;
    }
}
