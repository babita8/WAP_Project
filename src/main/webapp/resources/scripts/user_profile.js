$(function () {
    $('#user_profile').hide();
    $('#msg_bar').hide();
    $('#user_profile input:not(:button)').prop('disabled', true);
    $('#btn_save').hide();
    $('#btn_profile').click(function () {
        $('#user_profile').toggle();
    });
    $('#btn_edit').click(function () {
        $('.editable').prop('disabled', false);
        $(this).hide();
        $('#btn_save').show();
    });

    $('#btn_save').click(function () {
        makeAjaxRequestToUpdateUser();
        $('.editable').prop('disabled', true);
        $(this).hide();
        $('#btn_edit').show();
    })
});

function makeAjaxRequestToUpdateUser() {
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
        success: updateSuccessful
    })
}

function updateSuccessful(data) {
    let timer = null;
    $('#msg_bar').text(data.msg).show();
    if(timer){clearTimeout(timer)}
    timer = setTimeout(function () {
        $('#msg_bar').hide();
    }, 2000)
}