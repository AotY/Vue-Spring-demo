package com.xjtu.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.xjtu.common.Const;
import com.xjtu.common.ResponseCode;
import com.xjtu.common.ServerResponse;
import com.xjtu.dao.CategoryMapper;
import com.xjtu.pojo.Category;
import com.xjtu.service.ICategoryService;
import com.xjtu.util.PropertiesUtil;
import com.xjtu.vo.CategoryRecommendListVo;
import com.xjtu.vo.CategoryVo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by LeonTao on 2017/9/25.
 */
@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {


    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 获取第一级类别
     *
     * @param categoryId
     * @return
     */
    @Override
    public ServerResponse<List<CategoryRecommendListVo>> getFirstFloorCategory(Integer categoryId) {
        //
        List<Category> categories = categoryMapper.selectCategoryChildrenByParentId(categoryId);

        //
        if (CollectionUtils.isEmpty(categories)) {
            logger.info(Const.NOCHILDRENCATEGORIES);
        }

        List<CategoryRecommendListVo> categoryRecommendListVoList = Lists.newArrayList();
        for (Category curCategory: categories) {
            List<Category> childCategories = categoryMapper.selectCategoryChildrenByParentId(curCategory.getId());
            List<CategoryVo> categoryVoList = assembleCategoryVo(childCategories);
            CategoryRecommendListVo categoryRecommendListVo = new CategoryRecommendListVo();
            categoryRecommendListVo.setId(curCategory.getId());
            categoryRecommendListVo.setParentId(curCategory.getParentId());
            categoryRecommendListVo.setName(curCategory.getName());
            categoryRecommendListVo.setCategoryVoList(categoryVoList);
            categoryRecommendListVoList.add(categoryRecommendListVo);
        }



        return ServerResponse.createBySuccess(categoryRecommendListVoList);
    }

    /**
     *
     * @param categories
     * @return
     */
    private List<CategoryVo> assembleCategoryVo(List<Category> categories) {
        List<CategoryVo> categoryVoList = Lists.newArrayList();
        for (Category category: categories) {
            CategoryVo categoryVo = new CategoryVo();
            categoryVo.setId(category.getId());
            categoryVo.setName(category.getName());
            categoryVo.setParentId(category.getParentId());
            categoryVo.setStatus(category.getStatus());
            categoryVo.setSortOrder(category.getSortOrder());
            categoryVo.setImage(category.getImage());
            categoryVo.setImageHost(PropertiesUtil.getProperty(Const.FTPSERVERHTTPPREFIX,""));
            categoryVoList.add(categoryVo);
        }

        return categoryVoList;
    }


    /**
     * 获取所有子类别（需要进行递归）
     *
     * @param categoryId
     * @return
     */
    @Override
    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId) {
        Set<Category> categorySet = Sets.newHashSet();
        findChildCategory(categorySet, categoryId);

        List<Integer> categoryIdList = Lists.newArrayList();
        if (categoryId != null && CollectionUtils.isNotEmpty(categorySet)) {
            for (Category categoryItem : categorySet) {
                categoryIdList.add(categoryItem.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryIdList);
    }

    /**
     * 递归查找子类别
     *
     * @param categorySet
     * @param categoryId
     */
    private void findChildCategory(Set<Category> categorySet, Integer categoryId) {


        Category category = categoryMapper.selectByPrimaryKey(categoryId);

        if (category != null) {
            categorySet.add(category);
        }

        // 查找子节点
        List<Category> childrenCategories = categoryMapper.selectCategoryChildrenByParentId(categoryId);

        if (!CollectionUtils.isEmpty(childrenCategories)) {
            for (Category child : childrenCategories) {
                findChildCategory(categorySet, child.getId());
            }
        }
    }


}
