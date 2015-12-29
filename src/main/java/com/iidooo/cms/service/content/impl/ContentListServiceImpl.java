package com.iidooo.cms.service.content.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.iidooo.cms.dao.extend.ChannelDao;
import com.iidooo.cms.dao.extend.ContentDao;
import com.iidooo.cms.dto.extend.ChannelDto;
import com.iidooo.cms.dto.extend.ContentDto;
import com.iidooo.cms.service.content.ContentListService;
import com.iidooo.cms.util.ChannelUtil;
import com.iidooo.core.constant.DateTimeFormat;
import com.iidooo.core.constant.SessionConstant;
import com.iidooo.core.dto.PageDto;
import com.iidooo.core.dto.extend.SecurityUserDto;
import com.iidooo.core.util.DateUtil;
import com.opensymphony.xwork2.ActionContext;

public class ContentListServiceImpl implements ContentListService {

    private static final Logger logger = Logger.getLogger(ContentListServiceImpl.class);

    private ChannelDao channelDao;

    private ContentDao contentDao;

    @Override
    public ContentDto getContent(Integer contentID) {
        try {
            ContentDto result = contentDao.selectByContentID(contentID);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return null;
        }
    }

    @Override
    public int getContentListCount(Integer siteID, Integer channelID) {
        int result = 0;
        try {
            if (channelID == null || channelID <= 0) {
                result = contentDao.selectContentListCount(siteID, null);
            } else {
                List<Integer> channels = getOffspringChannelList(channelID, siteID);
                result = contentDao.selectContentListCountByChannels(channels);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
        return result;
    }

    @Override
    public List<ContentDto> getContentList(Integer siteID, Integer channelID, PageDto page) {
        List<ContentDto> result = new ArrayList<ContentDto>();
        try {
            if (channelID == null || channelID <= 0) {
                result = contentDao.selectContentList(siteID, null, page);
            } else {
                List<Integer> channels = getOffspringChannelList(channelID, siteID);
                result = contentDao.selectContentListByChannels(channels, page);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
        return result;
    }

    @Override
    public boolean deleteContent(ContentDto content) {
        try {

            Map<String, Object> sessionMap = ActionContext.getContext().getSession();
            SecurityUserDto user = (SecurityUserDto) sessionMap.get(SessionConstant.LOGIN_USER);
            content.setUpdateUser(user.getUserID());
            content.setUpdateTime(DateUtil.getNow(DateTimeFormat.DATE_TIME_HYPHEN));
            int count = contentDao.delete(content);
            if (count <= 0) {
                return false;
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return false;
        }
    }

    private List<Integer> getOffspringChannelList(Integer channelID, Integer siteID) {
        List<Integer> result = new ArrayList<Integer>();
        try {
            result.add(channelID);

            ChannelUtil channelUtil = new ChannelUtil(channelDao);

            ChannelDto channel = new ChannelDto();
            List<ChannelDto> channelList = channelDao.selectChannelList(channel);
            channelUtil.counstructChildren(channelList);

            result = channelUtil.getOffspring(channelList, channelID);
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
        return result;
    }

}
