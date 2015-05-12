<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="core" uri="/core-tags"%>
<%@ taglib prefix="cms" uri="/cms-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../include/Header.jsp"></jsp:include>
<script type="text/javascript" src="${CORE_URL}/js/jquery.treeview/jquery.treeview.js"></script>
<script type="text/javascript" src="${CORE_URL}/js/jquery.treeview/lib/jquery.cookie.js"></script>
<link type="text/css" rel="stylesheet" href="${CORE_URL}/js/jquery.treeview/jquery.treeview.css">

<script type="text/javascript">
	$(function() {
		$("#tree").treeview({
			animated : "fast",
			persist : "location"
		});
	})
	
	function btnCreate(){
		var contentType = $("#selContentType").val();
		window.location.href="contentDetail.action?content.contentType=" + contentType;
	}
	
	function deleteContent(contentID){
		if (confirm("确定要删除该内容吗？")) {
			$("#hidContentID").val(contentID);
			window.form.action = "contentListDelete.action";
			window.form.submit();
	    }
	}
</script>
</head>
<body>	
	<jsp:include page="../include/Top.jsp"></jsp:include>
	<div class="page_content_wrap">
		<div class="page_content_left_wrap">
			<cms:channelTree baseURL="contentList.action?content.channelID=" title="栏目树" />
		</div>
		<div class="page_content_right_wrap">
			<div class="bread_crumb">
				<span>当前的位置：</span><span>内容管理 - 内容列表</span>
			</div>			
			<div class="page_content">
				<form id="form" action="" method="post">
					<s:actionerror />
					<s:actionmessage />
					<input id="hidChannelID" type="hidden" name="content.channelID" value="${content.channelID }">
					<input id="hidContentID" type="hidden" name="content.contentID" value="${content.contentID }">
					<table class="list">
						<tr>
							<th width="5%">ID</th>
							<th width="10%">栏目</th>
							<th width="35%">标题</th>
							<th width="30%">副标题</th>
							<th width="5%">类型</th>
							<th width="5%">评论可否</th>
							<th width="10%">操作</th>
						</tr>
						<s:iterator id="item" value="contentList" status="st">
							<tr>
								<td class="align_center"><a href="contentInfo.action?content.contentID=${item.contentID }&mode=2"> ${item.contentID} </a></td>
								<td class="align_center">${item.channelName}</td>
								<td><a href="contentInfo.action?content.contentID=${item.contentID }&mode=2"> ${item.contentTitle} </a></td>
								<td>${item.contentSubTitle}</td>
								<td class="align_center">
									<s:if test="#item.contentType == 1">默认</s:if>
									<s:if test ="#item.contentType == 2">产品</s:if>
								</td>
								<td class="align_center">
									<s:if test="#item.isSilent == 0">可</s:if>
									<s:if test="#item.isSilent == 1">否</s:if>
								</td>
								<td class="align_center"><a>上移</a>| <a>下移</a>| <a href="#" onclick="return deleteContent(${item.contentID})">删除</a></td>
							</tr>
						</s:iterator>
					</table>
					<div class="button_bar">
						<core:dictItem id="selContentType" name="content.contentType" dictClassCode="CONTENT_TYPE"/>					
						<button type="button" onclick="btnCreate()">发布</button>
					</div>
					<div class="block">
						<hr>
					</div>
					<jsp:include page="../include/Paging.jsp"></jsp:include>
				</form>
			</div>
		</div>
	</div>
	<jsp:include page="../include/Footer.jsp"></jsp:include>
</body>
</html>