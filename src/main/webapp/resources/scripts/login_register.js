$(function () {
    $('#msg_bar').hide();
   $('#signup_form').hide();
   $('#register_btn').click(showSignupForm);
   $('#login_btn').click(showLoginForm);
   $('#btn_register').click(makeAjaxCallToCreateUser)
});

function showSignupForm() {
    $('#signup_form').show();
    $('#login_form').hide();
}

function showLoginForm() {
    $('#signup_form').hide();
    $('#login_form').show();
}

function makeAjaxCallToCreateUser() {
    const user = {};
    user.id = $('#user_id').val();
    user.userName = $('#user_name').val();
    user.passWord = $('#user_pass').val();
    user.email = $('#user_email').val();
    user.phone = $('#user_phone').val();
    user.groupId = $('#user_group_id').val();
    user.group = $('#user_group').val();
    $.ajax({
        url: 'profile_controller',
        method: 'post',
        data: {user: JSON.stringify(user)},
        dataType: 'json',
        success: insertSuccessful
    })

}

function insertSuccessful(data) {
    let timer = null;
    $('#msg_bar').text(data.msg).show();
    if(timer){clearTimeout(timer)}
    timer = setTimeout(function () {
        $('#msg_bar').hide();
        showLoginForm();
    }, 2000)

}