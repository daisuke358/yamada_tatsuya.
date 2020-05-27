<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ユーザー管理画面</title>
 	  	<link rel=“stylesheet”  type="text/css" href=“style.css”>
	  	<style type="text/css">
				.table1 {
				  border: 1px solid ;
				  border-collapse:  collapse;
				  padding: 100px;
				}
				.table1 th, .table1 td {
				  border: 1px solid ;
				  width:  300px;
			      height: 10px;
			      font-weight: bolder;
				}
		</style>
    </head>
    <body>
        <div class="main-contents">
			<div class="header">
			        <a href="./">戻る</a>
			        <a href="signup">ユーザー新規登録画面</a>
			        <a href="logout">ログアウト</a>
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

			<div class="posts">
				<table class="table1">
					<tr>
						<th>ID</th>
						<th>アカウント名</th>
						<th>名称</th>
						<th>支社</th>
						<th>部署</th>
						<th>ユーザー状態</th>
						<th>変更</th>
						<th>登録日時</th>
						<th>更新日時</th>
						<th>編集</th>
					</tr>
				    <c:forEach items="${users}" var="user">
				        <div class="user">
				        	<tr>
					            <th><div class="id"><c:out value="${user.id}" /></div></th>
					            <th><div class="account"><c:out value="${user.account}" /></div></th>
					            <th><div class="name"><c:out value="${user.name}" /></div></th>
					            <th>
					            	<c:if test="${user.branch_office ==1}">本社</c:if>
					            	<c:if test="${user.branch_office ==2}">A支社</c:if>
					            	<c:if test="${user.branch_office ==3}">B支社</c:if>
					            	<c:if test="${user.branch_office ==4}">C支社</c:if>
					            </th>
					            <th>
					            	<c:if test="${user.department ==1}">総務人事部</c:if>
					            	<c:if test="${user.department ==2}">情報管理部</c:if>
					            	<c:if test="${user.department ==3}">営業部</c:if>
					            	<c:if test="${user.department ==4}">技術部</c:if>
					            <th>
					            	<c:if test="${user.status ==1}">活性</c:if>
					            	<c:if test="${user.status ==2}">停止</c:if>
					            </th>
					            <th>
					            <c:if test="${!(loginUser.id == user.id)}">
						            <c:if test="${user.status ==1}">
										<div hidden>
											<form action="management" method="post">
											<textarea name="id" ><c:out value="${user.id}" /></textarea>
											<textarea name="status">2</textarea>
											<input id="status2" type="submit" value="停止">
											</form>
										</div>
										<script>
										function kakunin2(){
											  ret = confirm("停止してもよろしいですか？");
											  if (ret == true){
												  document.getElementById("status2").click();
											  }
											}
										</script>
										<input type="button" value="停止" onClick="kakunin2()">
									</c:if>
									<c:if test="${user.status ==2}">
										<div hidden>
												<form action="management" method="post">
												<textarea name="id" ><c:out value="${user.id}" /></textarea>
												<textarea name="status">1</textarea>
												<input id="status1" type="submit" value="復活">
												</form>
											</div>
											<script>
											function kakunin(){
												  ret = confirm("変更してもよろしいですか？");
												  if (ret == true){
													  document.getElementById("status1").click();
												  }
												}
											</script>
											<input type="button" value="復活" onClick="kakunin()">
									</c:if>
							</c:if>
				            </th>
			            	<th><div class="createdDate"><fmt:formatDate value="${user.createdDate}" pattern="yyyy/MM/dd HH:mm:ss" /></div></th>
			            	<th><div class="updatedDate"><fmt:formatDate value="${user.updatedDate}" pattern="yyyy/MM/dd HH:mm:ss" /></div></th>
			            	<th><div class="edit"><a href="edit?id=${user.id}">編集</a></div></th>
					      </tr>
				        </div>
				     </c:forEach>
				 </table>
			</div>
            <div class="copyright"> Copyright(c)YourName</div>
        </div>
    </body>
</html>