package com.baizhi.cyf.controller;

import com.baizhi.cyf.entity.City;
import com.baizhi.cyf.entity.Models;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("echarts")
public class EchartsController {

    @RequestMapping("getUserEcharts")
    public HashMap<String, Object> getUserEcharts(){

        HashMap<String, Object> map = new HashMap<>();
        map.put("month", Arrays.asList("1月","2月","3月","4月","5月","6月"));
        map.put("boys", Arrays.asList(50, 200, 100, 100, 100, 200));
        map.put("girls", Arrays.asList(5, 20, 36, 100, 10, 20));

        return map;
    }

    @RequestMapping("getEchartsMap")
    public ArrayList<Models> getEchartsMap(){

        List<City> boysCities = new ArrayList<>();
        boysCities.add(new City("北京","100"));
        boysCities.add(new City("河南","500"));
        boysCities.add(new City("河北","300"));
        boysCities.add(new City("湖南","100"));
        boysCities.add(new City("湖北","900"));
        boysCities.add(new City("山东","600"));

        List<City> girlsCities = new ArrayList<>();
        girlsCities.add(new City("北京","100"));
        girlsCities.add(new City("山西","500"));
        girlsCities.add(new City("河北","700"));
        girlsCities.add(new City("湖南","600"));
        girlsCities.add(new City("吉林","900"));
        girlsCities.add(new City("辽宁","600"));

        ArrayList<Models> ModelsList = new ArrayList<>();
        ModelsList.add(new Models("小男生",boysCities));
        ModelsList.add(new Models("小姑娘",girlsCities));

        return ModelsList;
    }
}
