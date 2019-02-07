<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>Team To-Do</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <!-- jQuery Files -->
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script type="text/javascript" src="resources/scripts/login_register.js"></script>

    <!-- CSS File -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
<div class="jumbotron">
    <h1>Team To-Do</h1>
    <p>Let's DO IT together.</p>
</div>
<div class="container">
    <div id="signin_form">
        <div class="input-group">
            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
            <input type="text" class="form-control" id="login_id" placeholder="Enter login id ..." name="login_id"
                  required>
        </div>
        <div></div>
        <div class="input-group">
            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
            <input type="password" class="form-control" id="login_pwd" placeholder="Enter password ..."
                   name="login_pwd" required>
        </div>
        <br/>
        <button id="btn_login" class="btn btn-primary">Login</button>
        <div>
            <button class="btn btn-link" id="btn_to_signup">Register here</button>
        </div>
        <div id="signin_msg_bar" class="alert alert-danger"></div>
    </div>

    <div id="signup_form">
        <div id="user_profile" class="form-horizontal">
            <div class="form-group">
                <label for="user_id" class="control-label col-xs-2">User ID:</label>
                <div class="col-xs-5">
                    <input type="text" class="form-control form-control-sm" id="user_id" value="${user.id}">
                </div>
            </div>
            <div class="form-group">
                <label for="user_group" class="control-label col-xs-2">User Group:</label>
                <div class="col-xs-5">
                    <select class="form-control form-control-sm" id="user_group">

                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="user_name" class="control-label col-xs-2">User Name:</label>
                <div class="col-xs-5">
                    <input type="text" class="editable form-control form-control-sm" id="user_name"
                           value="${user.userName}">
                </div>
            </div>
            <div class="form-group">
                <label for="user_pass" class="control-label col-xs-2">Password:</label>
                <div class="col-xs-5">
                    <input type="password" class="editable form-control form-control-sm" id="user_pass"
                           value="${user.passWord}">
                </div>
            </div>
            <div class="form-group">
                <label for="user_email" class="control-label col-xs-2">User Email:</label>
                <div class="col-xs-5">
                    <input type="email" class="editable form-control form-control-sm" id="user_email"
                           value="${user.email}">
                </div>
            </div>
            <div class="form-group">
                <label for="user_phone" class="control-label col-xs-2">User Phone:</label>
                <div class="col-xs-5">
                    <input type="tel" class="editable form-control form-control-sm" id="user_phone"
                           value="${user.phone}">
                </div>
            </div>
            <button id="btn_register" class="btn btn-sm btn-primary">Register</button>
        </div>
        <div>
            <button class="btn btn-link" id="btn_to_login">Existing user?</button>
        </div>
        <div id="signup_msg_bar" class="alert alert-success"></div>
    </div>
</div>
</body>
</html>