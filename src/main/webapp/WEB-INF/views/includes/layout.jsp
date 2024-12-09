<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<%
request.setCharacterEncoding("utf-8");
%>


<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8">
	<title><tiles:insertAttribute name="title" /></title>
	
	<style>
		#layout-container {
			width: 100%
		}
		
		#layout-container>table {
			width: 100%;
			border-collapse: collapse;
		}
		
		.layout-center {
			min-height: 400px;
		}
	</style>
</head>

<body>
	<div id="layout-container">
		<table>
			<tr>
				<td colspan="3">
					<div class="layout-top">
						<tiles:insertAttribute name="top" />
					</div>
				</td>
			</tr>
			<tr>
				<td width="360px">
					<div class="layout-left">
						<tiles:insertAttribute name="left" />
					</div>
				</td>
				<td width="1200px">
					<div class="layout-center">
						<tiles:insertAttribute name="center" />
					</div>
				</td>
				<td width="360px">
					<div class="layout-right">
						<tiles:insertAttribute name="right" />
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<div class="layout-bottom">
						<tiles:insertAttribute name="bottom" />
					</div>
				</td>
			</tr>
		</table>
	</div>
</body>

</html>