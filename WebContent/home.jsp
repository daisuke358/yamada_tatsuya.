<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
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
			    <c:if test="${ empty loginUser }">
			        <a href="login">ログイン</a>
			        <a href="signup">登録する</a>
			    </c:if>
			    <c:if test="${ not empty loginUser }">
			        <a href="post">新規投稿画面</a>
			        	<c:if test="${ loginUserBranchOffice == 1}">
			        		<a href="management">ユーザー管理画面</a>
			        	</c:if>
			        <a href="logout">ログアウト</a>
			    </c:if>
			</div>

			<c:if test="${ not empty PostMessage }">
                <div class="errorMessages">
                    <ul>
                            <li><c:out value="${PostMessage}" />

                    </ul>
                </div>
             </c:if>
			<form action="extraction" method="post">
				開始日<select name="start" type="start" id="start">
                		<c:if test="${start == null}">
	                		<option value=""></option>
							<option value="${today}">本日</option>
							<option value="${threeDaysAgo}">三日前</option>
							<option value="${oneWeekAgo}">一週間前</option>
		               		<option value="${oneMonthAgo}">一ヶ月前</option>
						</c:if>
						<c:if test="${start == today}">
							<option value="${today}">本日</option>
							<option value="${threeDaysAgo}">三日前</option>
							<option value="${oneWeekAgo}">一週間前</option>
		               		<option value="${oneMonthAgo}">一ヶ月前</option>
						</c:if>
						<c:if test="${start == threeDaysAgo}">
							<option value="${threeDaysAgo}">三日前</option>
							<option value="${today}">本日</option>
							<option value="${oneWeekAgo}">一週間前</option>
		               		<option value="${oneMonthAgo}">一ヶ月前</option>
						</c:if>
						<c:if test="${start == oneWeekAgo}">
							<option value="${oneWeekAgo}">一週間前</option>
							<option value="${today}">本日</option>
							<option value="${threeDaysAgo}">三日前</option>
		               		<option value="${oneMonthAgo}">一ヶ月前</option>
						</c:if>
						<c:if test="${start == oneMonthAgo}">
		               		<option value="${oneMonthAgo}">一ヶ月前</option>
							<option value="${today}">本日</option>
							<option value="${threeDaysAgo}">三日前</option>
							<option value="${oneWeekAgo}">一週間前</option>
						</c:if>
					</select>
				終了日<select name="end" id="end">
						<c:if test="${end == null}">
	                		<option value=""></option>
							<option value="${today}">本日</option>
							<option value="${threeDaysAgo}">三日前</option>
							<option value="${oneWeekAgo}">一週間前</option>
		               		<option value="${oneMonthAgo}">一ヶ月前</option>
						</c:if>
						<c:if test="${end == today}">
							<option value="${today}">本日</option>
							<option value="${threeDaysAgo}">三日前</option>
							<option value="${oneWeekAgo}">一週間前</option>
		               		<option value="${oneMonthAgo}">一ヶ月前</option>
						</c:if>
						<c:if test="${end == threeDaysAgo}">
							<option value="${threeDaysAgo}">三日前</option>
							<option value="${today}">本日</option>
							<option value="${oneWeekAgo}">一週間前</option>
		               		<option value="${oneMonthAgo}">一ヶ月前</option>
						</c:if>
						<c:if test="${end == oneWeekAgo}">
							<option value="${oneWeekAgo}">一週間前</option>
							<option value="${today}">本日</option>
							<option value="${threeDaysAgo}">三日前</option>
		               		<option value="${oneMonthAgo}">一ヶ月前</option>
						</c:if>
						<c:if test="${end == oneMonthAgo}">
		               		<option value="${oneMonthAgo}">一ヶ月前</option>
							<option value="${today}">本日</option>
							<option value="${threeDaysAgo}">三日前</option>
							<option value="${oneWeekAgo}">一週間前</option>
						</c:if>
					</select>
				カテゴリー<input name="category" value="${category}"/><input type="submit" value="絞り込み">
			</form>

			<form action="./" method="get">
					<input type="submit" value="もとに戻す">
				</form>


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
						<th>件名</th>
						<th>本文</th>
						<th>カテゴリー</th>
						<th>投稿日時</th>
						<th>投稿削除</th>
						<th>コメント投稿</th>
					</tr>

				    <c:forEach items="${posts}" var="post">
				        <div class="post">
				        	<tr>
					            <th><div class="title"><c:out value="${post.title}" /></div></th>
					            <th><div class="text">
					            <c:forEach var="s" items="${post.splitedText}">
								    <div>${s}</div>
								</c:forEach>
								</th>
					            <th><div class="category"><c:out value="${post.category}" /></div></th>
				            	<th><div class="date"><fmt:formatDate value="${post.createdDate}" pattern="yyyy/MM/dd HH:mm:ss" /></div></th>
					            <th>
					            	<c:if test="${ loginUserId.equals(post.userId) }">
										<div hidden>
												<form action="postDelete" method="post">
												<textarea name="id" ><c:out value="${post.id}" /></textarea>
												<input id="postDelete" type="submit" value="削除">
												</form>
											</div>
											<script>
											function kakunin(){
												  ret = confirm("投稿を削除してもよろしいですか？");
												  if (ret == true){
													  document.getElementById("postDelete").click();
												  }
												}
											</script>
											<input type="button" value="削除" onClick="kakunin()">
									</c:if>
					            </th>

					            <th>
					            	<div class="form-area">
        								<form action="comment" method="post">
        									<textarea name="text" ></textarea>
        									<div hidden><textarea name="id" ><c:out value="${post.id}" /></textarea></div>
        									<div hidden><textarea name="loginUserId" ><c:out value="${loginUserId}" /></textarea></div>
        									<input type="submit" value="コメント投稿">
        								</form>
        							</div>
					            </th>
							</tr>

							<c:forEach items="${comments}" var="comment">
								<c:if test="${post.id == comment.postId }" >
				            		<tr></tr>
			            			<th>コメント:
			            			<c:forEach var="s" items="${comment.splitedText}">
								    	<div>${s}</div>
									</c:forEach>
									</th>
			            			<th>
			            				<c:if test="${ loginUserId.equals(comment.userId) }">
				            				<div hidden>
												<form action="commentDelete" method="post">
												<textarea name="id" ><c:out value="${comment.id}" /></textarea>
												<input id="commentDelete" type="submit" value="コメント削除">
												</form>
											</div>
											<script>
											function commentKakunin(){
												  ret = confirm("コメントを削除してもよろしいですか？");
												  if (ret == true){
													  document.getElementById("commentDelete").click();
												  }
												}
											</script>
											<input type="button" value="コメント削除" onClick="commentKakunin()">
							            </c:if>
						            </th>
			            		</c:if>
							</c:forEach>
				        </div>
				     </c:forEach>
				 </table>
			</div>
            <div class="copyright"> Copyright(c)YourName</div>
        </div>
        <script src="scripts/main.js"></script>
    </body>
</html>