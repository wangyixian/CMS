package com.iidooo.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iidooo.cms.model.po.CmsComment;
import com.iidooo.core.model.Page;

public interface CmsCommentMapper {
    int deleteByPrimaryKey(Integer commentID);

    /**
     * 插入CmsComment数据
     * @param cmsComment 该条记录被插入
     * @return 影响的行数
     */
    int insert(CmsComment cmsComment);

    /**
     * 通过主键查询CmsComment数据
     * @param commentID 主键ID
     * @return 查询活的的CmsComment对象
     */
    CmsComment selectByCommentID(Integer commentID);
    
    /**
     * 根据内容ID获得关联的评论一览
     * @param contentID
     * @param page 分页对象
     * @return 评论一览列表
     */
    List<CmsComment> selectByContentID(@Param("contentID")Integer contentID, @Param("page")Page page);

    /**
     * 更新CmsComment数据
     * @param 要更新的 CmsComment 对象内容
     * @return 更新所影响的行
     */
    int updateByCommentID(CmsComment cmsComment);
    
}