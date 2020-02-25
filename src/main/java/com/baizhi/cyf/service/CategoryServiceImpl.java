package com.baizhi.cyf.service;

import com.baizhi.cyf.dao.CategoryMapper;
import com.baizhi.cyf.entity.Category;
import com.baizhi.cyf.entity.CategoryExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Resource
    CategoryMapper categoryMapper;

    @Override
    public HashMap<String, Object> queryByPage(Integer page, Integer rows) {

        HashMap<String, Object> map = new HashMap<>();
        //总条数
        CategoryExample example = new CategoryExample();
        example.createCriteria().andLevelsEqualTo(1);
        int records = categoryMapper.selectCountByExample(example);
        map.put("records",records);

        //总页数
        Integer totals=records%rows==0?records/rows:records/rows+1;
        map.put("totals",totals);

        //当前页
        map.put("page",page);

        //数据
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Category> categories = categoryMapper.selectByExampleAndRowBounds(example, rowBounds);
        map.put("rows",categories);

        return map;
    }

    @Override
    public HashMap<String, Object> queryByTwoPage(Integer page, Integer rows,String parentId) {

        HashMap<String, Object> map = new HashMap<>();
        //总条数
        CategoryExample example = new CategoryExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        int records = categoryMapper.selectCountByExample(example);
        map.put("records",records);

        //总页数
        Integer totals=records%rows==0?records/rows:records/rows+1;
        map.put("totals",totals);

        //当前页
        map.put("page",page);

        //数据
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Category> categories = categoryMapper.selectByExampleAndRowBounds(example, rowBounds);
        map.put("rows",categories);

        return map;
    }

    @Override
    public HashMap<String, Object> delete(Category category) {
        HashMap<String, Object> map = new HashMap<>();
        //判断是一级类别还是二级类别
        Category cates = categoryMapper.selectOne(category);
        if(cates.getLevels()==1){
            //一级类别   是否有对应的二级类别
            //有二级类别  不能删除  提示信息
            //没有二级类别   正常删除
            CategoryExample example = new CategoryExample();
            example.createCriteria().andParentIdEqualTo(category.getId());
            int count = categoryMapper.selectCountByExample(example);
            if(count==0){
                CategoryExample examples = new CategoryExample();
                examples.createCriteria().andIdEqualTo(category.getId());
                categoryMapper.deleteByExample(examples);

                map.put("status","200");
                map.put("message","删除成功");
            }else{
                map.put("status","400");
                map.put("message","删除失败，该类别下有子类别");
            }
        }else{
            //二级类别   二级类别下是否有视频
            //有  不能删除  提示信息
            //没有  正常删除
            CategoryExample examples = new CategoryExample();
            examples.createCriteria().andIdEqualTo(category.getId());
            categoryMapper.deleteByExample(examples);
            map.put("status","200");
            map.put("message","删除成功");
        }

        return map;
    }
}


