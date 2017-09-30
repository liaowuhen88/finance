var billingMgr = {
    delBilling: function (id) {
        bootbox.confirm("是否要删除？", function (result) {
            if (result) {
                $.ajax({
                    url: applicationContextPath + "/billing/del",
                    method: "POST",
                    type: "json",
                    data: {
                        id: id
                    },
                    success: function (response) {
                        if (response.success) {
                            bootbox.alert("删除成功!");
                            window.location.href = applicationContextPath + "/billing/list";
                        }
                    }
                });
            }
        });
    }
};

$(document).ready(function () {
    var table;
    var entityName = null;
    var receiveDate = null;
    var arr = [null, null];

    var tableConfig = {
        ajax: {
            "url": applicationContextPath + '/billing/query',
            "type": "GET",
            "data": function (param) {
                var params = {};
                params.draw = param.draw;
                params.start = param.start;
                params.pageSize = param.length;
                params.chargeEntityName = entityName;
                params.fromBillingDate = arr[0];
                params.toBillingDate = arr[1];
                return params;
            }
        },
        columns: [
            {"data": "id", "visible": false},
            {"data": "billingSerialNumber"},
            {"data": "chargeEntityName"},
            {
                "data": "billingMoney", render: function (data, type, row) {
                return accounting.formatMoney(row['billingMoney'], "￥");
            }
            },
            {"data": "billingDate"},
            {"data": "billingImgCount"},
            {"data": "lastUpdater"},
            {
                "data": "lastUpdateTime", render: function (data, type, row) {
                var now = new Date(row['lastUpdateTime']);
                return now.getFullYear() + "-" + (now.getMonth() + 1) + "-" + now.getDate() + " " + now.getHours() + ":" + now.getMinutes() + ":" + now.getSeconds();
            }
            },
            {
                "data": "null", render: function (data, type, row) {
                var edit = '<a href="' + applicationContextPath + '/billing/edit/' + row['id'] + '">编辑</a>';
                var del = '<a href="#" onclick="javascript:billingMgr.delBilling(' + row['id'] + ')">删除</a>';
                return edit + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + del;
            }
            }
        ]
    };

    tableConfig = $.extend(tableConfig, util.initDataTableConfig());

    table = $("#example").DataTable(tableConfig);

    util.initDateRangePicker('receiveDate');

    $("#okBtn").click(function () {
        entityName = $("#chargeEntityName").val();
        receiveDate = $("#receiveDate").val();
        arr = receiveDate.split(' ~ ');

        table.ajax.reload(null, false);
    });

    $("#exportBtn").click(function () {
        entityName = $("#chargeEntityName").val();
        receiveDate = $("#receiveDate").val();
        if (receiveDate == null || receiveDate == '' || receiveDate == "undefined") {
            window.location.href = applicationContextPath + "/billing/export?chargeEntityName=" + entityName;
            return;
        }
        arr = receiveDate.split(' ~ ');
        if (arr.length < 2) {
            window.location.href = applicationContextPath + "/billing/export?chargeEntityName=" + entityName;
            return;
        }
        var fromBillingDate = arr[0];
        var toBillingDate = arr[1];
        window.location.href = applicationContextPath + "/billing/export?chargeEntityName=" + entityName + "&fromBillingDate=" + fromBillingDate + "&toBillingDate=" + toBillingDate;

    });
});