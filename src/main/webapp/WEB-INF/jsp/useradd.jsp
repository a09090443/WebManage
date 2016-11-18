<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Add_User</title>
</head>
<body>
	<div id="userAdd" title="Add_User">
		<form:form modelAttribute="userAddForm" method="post" action="add">
			<table>
			<fieldset>
					<form:label path="id.account">ACCOUNT</form:label>
					<form:input path="id.account" type="text"  class="text ui-widget-content ui-corner-all"/>
					<form:label path="userName">USER_NAME</form:label>
					<form:input path="userName" type="text"  class="text ui-widget-content ui-corner-all"/>
					<form:label path="password">PASSWORD</form:label>
					<form:password path="password" class="text ui-widget-content ui-corner-all"/>
					<form:label path="status">STATUS</form:label>
					<form:input path="status" type="text"  class="text ui-widget-content ui-corner-all"/>
					<form:label path="email">EMAIL</form:label>
					<form:input path="email" type="text"  class="text ui-widget-content ui-corner-all"/>
			</fieldset>
			</table>
		</form:form>
	</div>
</body>
</html>