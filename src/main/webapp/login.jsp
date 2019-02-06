<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MongoDb Servlet Example</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <!-- jQuery Files -->
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script type="text/javascript" src="resources/scripts/login_register.js"></script>

    <!-- CSS File -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
<div id="mongoDbServlet" class="container">
    <h1 align="center" class="text-primary">User: account and Pwd: account</h1>
    <hr/>

    <!------ MONGODB JSP & SERVLET EXAMPLE ------>
    <div id="login_form">
        <form id="user_login_form" name="loginForm" method="post" action="loginServlet">
            <!----- LOGIN FORM ------>
            <div class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                <input type="text" class="form-control" id="login_id" placeholder="Enter login id ..." name="login_id"
                       maxlength="10">
            </div>
            <div></div>
            <div class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                <input type="password" class="form-control" id="login_pwd" placeholder="Enter password ..."
                       name="login_pwd">
            </div>

            <!----- SUBMIT BUTTON ------>
            <div></div>
            <button id="submit_btn" type="submit" class="btn btn-primary">Confirm identity</button>
        </form>
        <div>
            <button class="btn btn-link" id="register_btn">Register here</button>
        </div>
    </div>

    <div id="signup_form">
        <div id="user_profile" class="form-horizontal">
            <div class="form-group">
                <label for="user_id" class="control-label col-xs-2">User ID:</label>
                <div class="col-xs-5">
                    <input type="text" class="form-control form-control-sm" id="user_id" value="${user.id}" >
                </div>
            </div>
            <div class="form-group">
                <label for="user_group_id" class="control-label col-xs-2">User Group ID:</label>
                <div class="col-xs-5">
                    <input type="text" class="form-control form-control-sm" id="user_group_id" value="${user.groupId}" >
                </div>
            </div>
            <div class="form-group">
                <label for="user_group" class="control-label col-xs-2">User Group:</label>
                <div class="col-xs-5">
                    <input type="text" class="form-control form-control-sm" id="user_group" value="${user.group}" >
                </div>
            </div>
            <div class="form-group">
                <label for="user_name" class="control-label col-xs-2">User Name:</label>
                <div class="col-xs-5">
                    <input type="text" class="editable form-control form-control-sm" id="user_name" value="${user.userName}" >
                </div>
            </div>
            <div class="form-group">
                <label for="user_pass" class="control-label col-xs-2">Password:</label>
                <div class="col-xs-5">
                    <input type="text" class="editable form-control form-control-sm" id="user_pass" value="${user.passWord}" >
                </div>
            </div>
            <div class="form-group">
                <label for="user_email" class="control-label col-xs-2">User Email:</label>
                <div class="col-xs-5">
                    <input type="text" class="editable form-control form-control-sm" id="user_email" value="${user.email}" >
                </div>
            </div>
            <div class="form-group">
                <label for="user_phone" class="control-label col-xs-2">User Phone:</label>
                <div class="col-xs-5">
                    <input type="text" class="editable form-control form-control-sm" id="user_phone" value="${user.phone}" >
                </div>
            </div>
            <button id="btn_register" class="btn btn-sm btn-primary">Register</button>
        </div>
        <div>
            <button class="btn btn-link" id="login_btn">Existing user?</button>
        </div>
        <div id="msg_bar" class="alert alert-success"></div>
    </div>

    <h4 id="errMsg" class="text-danger" align="center">${error_message}</h4>
</div>
</body>
</html>