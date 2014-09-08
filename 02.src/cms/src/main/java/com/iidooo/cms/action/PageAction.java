package com.iidooo.cms.action;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.iidooo.cms.constant.AttributeConstant;
import com.iidooo.cms.dto.extend.CmsBlockDto;
import com.iidooo.cms.dto.extend.CmsPageDto;
import com.iidooo.cms.service.PageService;
import com.iidooo.framework.action.BaseAction;

public class PageAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(PageAction.class);

    private CmsPageDto cmsPage;
    
    private HashMap<String, CmsBlockDto> blockMap;
    
    public CmsPageDto getCmsPage() {
        return cmsPage;
    }

    public void setCmsPage(CmsPageDto cmsPage) {
        this.cmsPage = cmsPage;
    }

    public HashMap<String, CmsBlockDto> getBlockMap() {
        return blockMap;
    }

    public void setBlockMap(HashMap<String, CmsBlockDto> blockMap) {
        this.blockMap = blockMap;
    }

    @Autowired
    private PageService pageService;

    @Override
    public String execute() throws Exception {
        try {
            String pageID = getRequest().getParameter(AttributeConstant.PAGE_ID);
            if (pageID != null) {
                cmsPage = pageService.getPageByID(Integer.parseInt(pageID));
                if (cmsPage != null) {
                    blockMap = pageService.getBlockMap(cmsPage.getPageID());
                }
            }
        } catch (Exception e) {
            logger.fatal(e);
        }
        return super.execute();
    }

}
