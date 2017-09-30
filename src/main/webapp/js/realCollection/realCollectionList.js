
var realCollectionMgr = {
    delRealCollection:function(id){
        bootbox.confirm("是否要删除？", function(result){
            if (result) {
                $.ajax({
                    url: applicationContextPath + "/realCollection/del",
                    method: "POST",
                    type: "json",
                    data: {
                        id: id
                    },
                    success: function (response) {
                        if (response.success) {
                            bootbox.alert("删除成功!");
                            window.location.href = applicationContextPath + "/realCollection/list";
                        }
                    }
                });
            }
        });
    }
};

function downLoad() {
    window.open(applicationContextPath + "/realCollection//template/download", "_blank");
}

$(document).ready(function() {
    var table;
    var entityName = null;
    var receiveDate = null;
    var arr = [null,null];

    var tableConfig = {
        "ajax":{
            "url":applicationContextPath +'/realCollection/query',
            "type":"GET",
            "data" :function(param){
                var params = {};
                params.draw = param.draw;
                params.start = param.start;
                params.pageSize = param.length;
                params.chargeEntityName = entityName;
                params.fromReceiveDate = arr[0];
                params.toReceiveDate = arr[1];
                return params;
            }
        },
        "columns":[
            {"data":"id","visible":false},
            {"data":"serialNumber"},
            {"data":"chargeEntityName"},
            {
                "data": "money", render: function (data, type, row) {
                return accounting.formatMoney(row['money'], "￥");
            }
            },
            {"data":"receiveDate"},
            {"data":"payerAccountName"},
            {"data":"remark"},
            {"data":"operator"},
            {"data":"null",render:function(data, type, row){
                var edit = '<a href="' + applicationContextPath + '/realCollection/edit/' + row['id'] + '">编辑</a>';
                var del = '<a href="#" onclick="javascript:realCollectionMgr.delRealCollection(' + row['id'] + ')">删除</a>';
                return edit + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + del;
            }}
        ]
    };
    tableConfig = $.extend(tableConfig, util.initDataTableConfig());

    table = $("#example").DataTable(tableConfig);

    util.initDateRangePicker("receiveDate");

    $("#okBtn").click(function(){
        entityName = $("#chargeEntityName").val();
        receiveDate = $("#receiveDate").val();
        arr = receiveDate.split(' ~ ');

        table.ajax.reload(null,false);
    });

    $("#importBtn").on("click", function () {
        window.location.href = applicationContextPath + "/realCollection/toImport"
    });

    $("#exportBtn").click(function () {
        entityName = $("#chargeEntityName").val();
        receiveDate = $("#receiveDate").val();
        if (receiveDate == null || receiveDate == '' || receiveDate == "undefined") {
            window.location.href = applicationContextPath + "/realCollection/export?chargeEntityName=" + entityName;
            return;
        }
        arr = receiveDate.split(' ~ ');
        if (arr.length < 2) {
            window.location.href = applicationContextPath + "/realCollection/export?chargeEntityName=" + entityName;
            return;
        }
        var fromBillingDate = arr[0];
        var toBillingDate = arr[1];
        window.location.href = applicationContextPath + "/realCollection/export?chargeEntityName=" + entityName + "&fromReceiveDate=" + fromBillingDate + "&toReceiveDate=" + toBillingDate;

    });

});