var util = {

    initSelect2: function (elementId, url) {
        var sel = $("#" + elementId).select2({
            ajax: {
                url: url,
                dataType: 'jsonp',
                jsonp: "callback",
                delay: 500,
                data: function (params) {
                    return {
                        name: params.term
                    };
                },
                processResults: function (data, params) {
                    params.page = params.page || 1;
                    return {
                        results: data.aaData
                    };
                },
                cache: true
            },
            minimumInputLength: 1,
            language:'zh-CN',
            templateResult: formatRepo,
            templateSelection: formatRepoSelection
        });
        return sel;
    },
    initDateRangePicker: function (elementId) {
        $("#" + elementId).daterangepicker({
            autoUpdateInput: false,
            "locale": {
                format: 'YYYY-MM-DD',
                separator: ' ~ ',
                applyLabel: '确定',
                cancelLabel: '清空',
                weekLabel: 'W',
                customRangeLabel: '自定义',
                daysOfWeek: moment.weekdaysMin(),
                monthNames: moment.monthsShort(),
                firstDay: moment.localeData().firstDayOfWeek()
            }
        });
        $("#" + elementId).on('apply.daterangepicker', function (ev, picker) {
            $(this).val(picker.startDate.format('YYYY-MM-DD') + ' ~ ' + picker.endDate.format('YYYY-MM-DD'));
        });

        $("#" + elementId).on('cancel.daterangepicker', function (ev, picker) {
            $(this).val('');
        });
    },
    initDataTableConfig: function () {
        return {
            "paging": true,
            "pagingType": "full_numbers",
            "info": true,
            "searching": false,
            "processing": true,
            "lengthChange": true,
            "aLengthMenu": [10, 20, 50, 100],
            'language': {
                "zeroRecords": "对不起，查询不到任何相关数据",
                "info": "当前_START_条到_END_条,共_TOTAL_条",
                "sInfoEmpty": "没有数据",
                "processing": "载入中...",
                "sLengthMenu": "每页显示 _MENU_ 条数据",
                "paginate": {
                    "first": "首页",
                    "previous": "上一页",
                    "next": "下一页",
                    "last": "尾页"
                }
            },
            "rowId": "id",
            "serverSide": true,
            "ordering": false,
            "autoWidth": true
        };
    }
};

function formatRepo(repo) {
    if (repo.loading)
        return repo.text;
    return repo.name;
}

function formatRepoSelection(result) {
    return result.name || result.text;
}