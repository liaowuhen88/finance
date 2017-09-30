realCollectionManager = {};

$(document).ready(function () {
    util.initSelect2('chargeEntityId', chargeEntityApiUrl);

    $('#chargeEntityId').on('select2:select', function (evt) {
        var data = evt.params.data;
        $("#chargeEntityName").val(data.name);
    });

    $("#cancelBtn").on("click",function(e){
        window.location.href = applicationContextPath + "/realCollection/list";
    });

    $('#saveRellCollectionForm').validator().on('submit', function (e) {
        if (e.isDefaultPrevented()) {
            bootbox.alert('请完整填写表单！');
        } else {
            $('#saveRellCollectionForm').ajaxSubmit({
                url: applicationContextPath + "/realCollection/add",
                type: 'POST',
                dataType: 'json',
                beforeSend: function (request) {
                    //request.setRequestHeader("X-Forwarded-Host", "test.17doubao.cn");
                },
                success: function (data) {
                    if (data.success) {
                        bootbox.alert("保存成功!");
                        window.location.href = applicationContextPath + "/realCollection/list";
                    } else {
                        bootbox.alert(data.message);
                    }
                }
            });
        }
        return false;
    });
});