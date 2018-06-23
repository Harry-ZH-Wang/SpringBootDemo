package com.wzh.demo.thinkingInJava;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wzh
 * @create 2018-05-30 22:58
 * @desc ${测试简单的Json}
 **/
public class jsonTest {

    public static void main(String[] args) {

        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        int i = 0;
        for(int j = 0; j < 2; j++)
        {
            Map<String,Object> temp = new HashMap<String, Object>();
            temp.put("index",i);
            temp.put("type",j);
            i++;
            System.out.println(JSONObject.fromObject(temp));
            list.add(temp);
        }
        String jsonStr = JSONArray.fromObject(list).toString();
        JSONArray jsonArray = JSONArray.fromObject(jsonStr);
        JSONArray temp = new JSONArray();
        for(int j = 0 ; j < jsonArray.size(); j++)
        {
            Map<String,Object>  map = (Map<String,Object>)jsonArray.get(j);
            System.out.println(map.get("index"));
            if(StringUtils.equals("1",map.get("index").toString()))
            {
                temp.add(map);
            }
        }
        jsonArray.removeAll(temp);
        System.out.println(jsonArray.toString());
    }
}
