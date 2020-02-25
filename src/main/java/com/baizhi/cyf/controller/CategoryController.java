package com.baizhi.cyf.controller;

import com.baizhi.cyf.entity.Category;
import com.baizhi.cyf.service.CategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

@RequestMapping("category")
@RestController
public class CategoryController {

    @Resource
    CategoryService categoryService;

    @RequestMapping("queryByPage")
    public HashMap<String,Object> queryByPage(Integer page,Integer rows){
        HashMap<String, Object> map = categoryService.queryByPage(page, rows);
        return map;
    }

    @RequestMapping("queryByTwoPage")
    public HashMap<String,Object> queryByTwoPage(Integer page,Integer rows,String parentId){

        System.out.println("parentId"+parentId);
        HashMap<String, Object> map = categoryService.queryByTwoPage(page, rows,parentId);
        return map;
    }

    @RequestMapping("edit")
    public Object edit(Category category, String oper){

        //1.数据入库   返回数据id
        //2.添加之后  做文件上传
        //3.修改文件路径
        System.out.println("==category=="+category);
        String id=null;
        if(oper.equals("add")){
            //id = categoryService.add(category);
            return id;
        }

        if(oper.equals("edit")){}

        if(oper.equals("del")){
            HashMap<String, Object> map = categoryService.delete(category);
            return map;
        }
        return "";
    }
}
