<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ログイン</title>
    </head>
    <body>
        <div class="main-contents">

            <c:if test="${ not empty errorMessages }">
                <div class="errorMessages">
                    <ul>
                        <c:forEach items="${errorMessages}" var="errorMessage">
                            <li><c:out value="${errorMessage}" />
                        </c:forEach>
                    </ul>
                </div>
            </c:if>

            <form action="login" method="post"><br />
                <label for="account">アカウント名</label>
                <input name="account" value="${account}" id="account" /> <br />

                <label for="password">パスワード</label>
                <input name="password" type="password" id="password"/> <br />

                <input type="submit" value="ログイン" /> <br />
            </form>

            <div class="copyright"> Copyright(c)YourName</div>
        </div>
    </body>
</html>