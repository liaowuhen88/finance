realCollectionManager = {};

$(document).ready(function () {

    util.initSelect2('chargeEntityId', chargeEntityApiUrl);

    $('#chargeEntityId').on('select2:select', function (evt) {
        var data = evt.params.data;
        $("#chargeEntityName").val(data.name);
    });

    $("#cancelBtn").on("click",function(e){
        window.location.href = applicationContextPath + "/billing/list";
    });


    $("#billingImgs").fileinput({
        language: 'zh',
        uploadUrl: applicationContextPath + "/xmppUpload/up/finance/img/billing",
        allowedFileExtensions: ['jpg', 'png', 'gif'],//接收的文件后缀,
        allowedFileTypes: ['image'],
        enctype: 'multipart/form-data',
        uploadAsync: true,
        showRemove: false,
        browseClass: "btn btn-primary", //按钮样式
        previewFileIcon: "<i class='glyphicon glyphicon-upload'></i>",
        msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
        previewFileType: ['image'],
        maxFileCount: 5
    }).on('filepreupload', function (event, data, previewId, index) {
        var billingId = $("#billingId").val();
        if (billingId == null || billingId == "") {
            return {
                message: '请先保存开票信息之后再上传影像',
                data: {key1: '403', detail1: 'validate error'}
            };
        }
    }).on('fileuploaded', function (event, data, id, index) {
        if (data.response.success) {
            $.ajax({
                url: applicationContextPath + "/billingImage/add",
                dataType: 'json',
                type: 'POST',
                data: {
                    path: data.response.data.src,
                    billingId: $("#billingId").val()
                },
                success: function (response) {
                    bootbox.alert('上传成功。');
                },
                error: function (response, status) {
                    bootbox.alert(data.msg);
                }
            });
        } else {
            bootbox.alert('上传失败。');
        }

    });

    var isFirst = true;

    $('#saveBillingForm').validator().on('submit', function (e) {
        if (e.isDefaultPrevented()) {
            bootbox.alert('请完整填写表单！');
            isFirst = true;
        } else {
            if (!isFirst){
                bootbox.alert('重复提交！');
            }else{
                $('#saveBillingForm').ajaxSubmit({
                    url: applicationContextPath + "/billing/add",
                    type: 'POST',
                    dataType: 'json',
                    success: function (response) {
                        if (response.success) {
                            bootbox.alert("保存成功!");
                            $("#billingId").val(response.data.id);
                            isFirst = false;
                            //window.location.href = applicationContextPath + "/billing/list";
                        } else {
                            bootbox.alert(response.message);
                            isFirst = true;
                        }
                    }
                });
            }
        }
        return false;
    });

});