realCollectionManager = {};

$(document).ready(function () {

    util.initSelect2('chargeEntityId', chargeEntityApiUrl);

    $('#chargeEntityId').on('select2:select', function (evt) {
        var data = evt.params.data;
        $("#chargeEntityName").val(data.name);
    });

    $("#returnBtn").on("click",function(e){
       window.location.href = applicationContextPath + "/billing/list";
    });

    var allowUpload = true;

    var initialPreviews = [];

    var initialPreviewConfigs = [];

    //加载该开票对应的影像图片
    var billingId = $("#id").val();
    if (billingId != null) {
        $.ajax({
            url: applicationContextPath + "/billing/image/" + billingId,
            dataType: 'json',
            type: 'GET',
            success: function (response) {
                initialPreviews = [];
                initialPreviewConfigs = [];
                if (response.success) {
                    for (var i = 0; i < response.data.length; i++) {
                        initialPreviews.push(applicationContextPath + "/xmppDownLoad/downLoad?key=" + response.data[i].path);
                        var previewConfig = {};
                        previewConfig.key = response.data[i].id;
                        previewConfig.url = applicationContextPath + "/billingImage/del";
                        initialPreviewConfigs.push(previewConfig);
                    }

                    $("#billingImgs").fileinput({
                        language: 'zh',
                        uploadUrl: applicationContextPath + "/xmppUpload/up/finance/img/billing",
                        allowedFileExtensions: ['jpg', 'png', 'gif'],//接收的文件后缀,
                        allowedFileTypes: ['image'],
                        enctype: 'multipart/form-data',
                        uploadAsync: true,
                        showRemove: false,
                        browseClass: "btn btn-primary", //按钮样式
                        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
                        msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
                        previewFileType: ['image'],
                        overwriteInitial: false,
                        initialPreview: initialPreviews,
                        initialPreviewAsData: true, // identify if you are sending preview data only and not the raw markup
                        initialPreviewFileType: 'image', // image is the default and can be overridden in config below
                        initialPreviewConfig: initialPreviewConfigs,
                        purifyHtml: true,
                        maxFileCount: 5
                    }).on('filepreupload', function (event, data, previewId, index) {
                        var billingId = $("#id").val();
                        if (billingId == null || billingId == "") {
                            bootbox.alert("请先保存开票信息之后再上传影像");
                            allowUpload = false;
                        }
                    }).on('fileuploaded', function (event, data, id, index) {
                        if (data.response.success) {
                            $.ajax({
                                url: applicationContextPath + "/billingImage/add",
                                dataType: 'json',
                                type: 'POST',
                                data: {
                                    path: data.response.data.src,
                                    billingId: $("#id").val()
                                },
                                success: function (response) {
                                    bootbox.alert('上传成功。');
                                },
                                error: function (response, status) {
                                    bootbox.alert('上传失败。');
                                }
                            });
                        } else {
                            bootbox.alert('上传失败。');
                        }
                    });
                }
            },
            error: function (response, status) {

            }
        });
    }

    $('#editBillingForm').validator().on('submit', function (e) {
        if (e.isDefaultPrevented()) {
            bootbox.alert('请完整填写表单！');
        } else {
            $('#editBillingForm').ajaxSubmit({
                url: applicationContextPath + "/billing/edit",
                type: 'POST',
                dataType: 'json',
                success: function (response) {
                    if (response.success) {
                        bootbox.alert("保存成功!");
                    } else {
                        bootbox.alert(response.message);
                    }
                }
            });
        }
        return false;
    });
});