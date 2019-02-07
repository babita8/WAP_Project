(function ($) {
    $(function () {
        $('#signup_msg_bar').hide();
        $('#signin_msg_bar').hide();
        $('#signup_form').hide();

        makeAjaxCallToRetriveUserGroups();

        $('#btn_to_signup').click(showSignupForm);
        $('#btn_register').click(makeAjaxCallToCreateUser);
        $('#btn_to_login').click(showLoginForm);
        $("#btn_login").click(makeAjaxCallToLogin)
    });


    function makeAjaxCallToRetriveUserGroups() {
        $.ajax({
            url: 'loginServlet',
            method: 'get',
            dataType: 'json',
            success: populateGroupsDropDown
        })
    }

    function populateGroupsDropDown(data) {
        for (let group_id in data) {
            let option = $('<option>').val(group_id).text(data[group_id]);
            $('#user_group').prepend(option);
        }
    }

    function showSignupForm() {
        $('#signup_form').show();
        $('#signin_form').hide();
    }

    function showLoginForm() {
        $('#signup_form').hide();
        $('#signin_form').show();
    }

    function makeAjaxCallToCreateUser() {
        const user = {};
        user.id = $('#user_id').val();
        user.userName = $('#user_name').val();
        user.passWord = $('#user_pass').val();
        user.email = $('#user_email').val();
        user.phone = $('#user_phone').val();
        user.groupId = $('#user_group').val();
        user.group = $(`#user_group option[value=${user.groupId}]`).text();
        $.ajax({
            url: 'profile_controller',
            method: 'post',
            data: {user: JSON.stringify(user)},
            dataType: 'json',
            success: handleInsertResponse
        })

    }

    function handleInsertResponse(data) {
        let timer = null;
        $('#signup_msg_bar').text(data.msg).show();
        if (timer) {
            clearTimeout(timer)
        }
        timer = setTimeout(function () {
            $('#signup_form input').val("");
            $('#signup_msg_bar').hide();
            showLoginForm();
        }, 2000)

    }

    function makeAjaxCallToLogin() {
        const login_id = $('#login_id').val();
        const login_pwd = $('#login_pwd').val()
        $.ajax({
            url: 'loginServlet',
            method: 'post',
            data: {login_id: login_id, login_pwd: login_pwd},
            dataType: 'json',
            success: handleLoginResponse
        })
    }

    function handleLoginResponse(data) {
        if (data.redirect) {
            window.location.href = data.redirect;
        } else {
            let timer = null;
            $('#signin_msg_bar').text(data.msg).show();
            if (timer) {
                clearTimeout(timer)
            }
            timer = setTimeout(function () {
                $('#signin_msg_bar').hide();
            }, 2000)
        }

    }
})(jQuery);