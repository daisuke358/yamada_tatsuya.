<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>新規投稿画面</title>
    </head>
    <body>
        <div class="main-contents">
			<div class="header">
			        <a href="./">戻る</a>
			</div>

			<c:if test="${ not empty errorMessages }">
                <div class="errorMessages">
                    <ul>
                        <c:forEach items="${errorMessages}" var="errorMessage">
                            <li><c:out value="${errorMessage}" />
                        </c:forEach>
                    </ul>
                </div>
            </c:if>

			<div class="form-area">
			        <form action="post" method="post">
			            <br />
			            件名<textarea name="title" cols="100" rows="1" class="tweet-box"><c:out value="${post.title}" /></textarea>（30文字まで）
			            <br />
			            本文<textarea name="text" cols="100" rows="5" class="tweet-box"><c:out value="${post.text}" /></textarea>（1000文字まで）
			            <br />
			            カテゴリー<textarea name="category" cols="100" rows="1" class="tweet-box"><c:out value="${post.category}" /></textarea>（10文字まで）
			            <br />
			            <input type="submit" value="投稿">
			        </form>
			</div>
            <div class="copyright"> Copyright(c)YourName</div>
        </div>
    </body>
</html>
