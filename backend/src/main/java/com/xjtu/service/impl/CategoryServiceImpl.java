package com.xjtu.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.xjtu.common.Const;
import com.xjtu.common.ResponseCode;
import com.xjtu.common.ServerResponse;
import com.xjtu.dao.CategoryMapper;
import com.xjtu.pojo.Category;
import com.xjtu.service.ICategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by LeonTao on 2017/9/25.
 */
@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {


    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private CategoryMapper categoryMapper;
    /**
     * 获取当前类别的子类别（只获取一级子类别）
     * @param categoryId
     * @return
     */
    @Override
    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId) {
        List<Category> categories = categoryMapper.selectCategoryChildrenByParentId(categoryId);

        if (CollectionUtils.isEmpty(categories)) {
            logger.info(Const.NOCHILDRENCATEGORIES);
        }
        return ServerResponse.createBySuccess(categories);
    }

    /**
     * 获取所有子类别（需要进行递归）
     * @param categoryId
     * @return
     */
    @Override
    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId) {
        Set<Category> categorySet = Sets.newHashSet();
        findChildCategory(categorySet, categoryId);

        List<Integer> categoryIdList = Lists.newArrayList();

        for (Category category: categorySet) {
            categoryIdList.add(category.getId());
        }

        return ServerResponse.createBySuccess(categoryIdList);
    }

    /**
     * 递归查找子类别
     * @param categoryset
     * @param categoryId
     */
    private void findChildCategory(Set<Category> categoryset, Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);

        if (categoryId != null) {
            categoryset.add(category);
        }

        // 查找子节点
        List<Category> childrenCategories = categoryMapper.selectCategoryChildrenByParentId(categoryId);

        if (!CollectionUtils.isEmpty(childrenCategories)) {
            for (Category child: childrenCategories) {
                findChildCategory(categoryset, child.getId());
            }
        }


    }
}
