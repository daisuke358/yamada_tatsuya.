<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>ユーザー編集画面</title>
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

            <form action="edit" method="post"><br />
            	<div hidden><input name="id" value="${user.id}" id="id"  /> <br /></div>

            	<label for="account">アカウント名</label>
                <input name="account" id="account" value="${user.account}"/> <br />

                <label for="password">パスワード</label>
                <input name="password"  type="password" id="password" /><br />

   				<label for="passwordB">パスワード(確認用)</label>
                <input name="passwordB"  type="password" id="password" /> <br />

                <label for="name">名称</label>
                <input name="name" type="name" id="name" value="${user.name}"/> <br />

                <c:if test="${!(loginUser.id == user.id)}">

	                <label for="branch_office">支社</label>
	                	<select name="branch_office" type="branch_office" id="branch_office">
	                		<c:if test="${ user.branch_office == 1 || user.branch_office == null}">
		                		<option value=1>本社</option>
								<option value=2>A支社</option>
								<option value=3>B支社</option>
								<option value=4>C支社</option>
							</c:if>
							<c:if test="${ user.branch_office == 2}">
								<option value=2>A支社</option>
		                		<option value=1>本社</option>
								<option value=3>B支社</option>
								<option value=4>C支社</option>
							</c:if>
							<c:if test="${ user.branch_office == 3}">
								<option value=3>B支社</option>
		                		<option value=1>本社</option>
								<option value=2>A支社</option>
								<option value=4>C支社</option>
							</c:if>
							<c:if test="${ user.branch_office == 4}">
								<option value=4>C支社</option>
		                		<option value=1>本社</option>
								<option value=2>A支社</option>
								<option value=3>B支社</option>
							</c:if>
						</select><br />

		                <label for="department">部署</label>
		                	<select name="department" type="department" id="department">
		                		<c:if test="${ user.department == 1 || user.department == null}">
			                		<option value=1>総務人事部</option>
									<option value=2>情報管理部</option>
									<option value=3>営業部</option>
									<option value=4>技術部</option>
								</c:if>
								<c:if test="${ user.department == 2}">
									<option value=2>情報管理部</option>
			                		<option value=1>総務人事部</option>
									<option value=3>営業部</option>
									<option value=4>技術部</option>
								</c:if>
								<c:if test="${ user.department == 3}">
									<option value=3>営業部</option>
			                		<option value=1>総務人事部</option>
									<option value=2>情報管理部</option>
									<option value=4>技術部</option>
								</c:if>
								<c:if test="${ user.department == 4}">
									<option value=4>技術部</option>
			                		<option value=1>総務人事部</option>
									<option value=2>情報管理部</option>
									<option value=3>営業部</option>
							</c:if>
						</select><br />
		                <label for="status">ユーザー停止状態</label>
		                	<select name="status" type="status" id="status">
		                		<c:if test="${ user.status == 1 || user.status == null}">
									<option value=1>活性</option>
									<option value=2>停止</option>
								</c:if>
								<c:if test="${ user.status == 2}">
									<option value=2>停止</option>
									<option value=1>活性</option>
								</c:if>
						</select><br />
				</c:if>

                <input type="submit" value="更新" /> <br />
            </form>
                <a href="management">戻る</a>
            <div class="copyright">Copyright(c)Your Name</div>
        </div>
    </body>
</html>