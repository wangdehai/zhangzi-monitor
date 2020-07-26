$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'monitor/monitoriotdevice/list',
        datatype: "json",
        colModel: [
                                                {
                        label: 'iotId',
                        name: 'iotId',
                        index: 'iot_id',
                        width: 50,
                        key: true
                    },
                                                                {
                        label: '',
                        name: 'devNum',
                        index: 'dev_num',
                        width: 80
                    }, 
                                                                {
                        label: '测量项',
                        name: 'key',
                        index: 'key',
                        width: 80
                    }, 
                                                                {
                        label: '测量值',
                        name: 'value',
                        index: 'value',
                        width: 80
                    }, 
                                                                {
                        label: '区域id',
                        name: 'regionId',
                        index: 'region_id',
                        width: 80
                    }
                                    ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
monitorIotDevice: {
}
},
methods: {
    query: function () {
        vm.reload();
    }
,
    add: function () {
        vm.showList = false;
        vm.title = "新增";
        vm.monitorIotDevice = {};
    }
,
    update: function (event) {
        var iotId =
        getSelectedRow();
        if (iotId == null
    )
        {
            return;
        }
        vm.showList = false;
        vm.title = "修改";

        vm.getInfo(iotId)
    }
,
    saveOrUpdate: function (event) {
        var url = vm
    .monitorIotDevice.iotId ==
        null ? "monitor/monitoriotdevice/save" : "monitor/monitoriotdevice/update";
        $.ajax({
            type: "POST",
            url: baseURL + url,
            contentType: "application/json",
            data: JSON.stringify(vm.monitorIotDevice),
            success: function (r) {
                if (r.code === 0) {
                    alert('操作成功', function (index) {
                        vm.reload();
                    });
                } else {
                    alert(r.msg);
                }
            }
        });
    }
,
    del: function (event) {
        var iotIds = getSelectedRows();
        if (iotIds == null) {
            return;
        }

        confirm('确定要删除选中的记录？', function () {
            $.ajax({
                type: "POST",
                url: baseURL + "monitor/monitoriotdevice/delete",
                contentType: "application/json",
                data: JSON.stringify(iotIds),
                success: function (r) {
                    if (r.code == 0) {
                        alert('操作成功', function (index) {
                            $("#jqGrid").trigger("reloadGrid");
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        });
    }
,
    getInfo: function (iotId) {
        $.get(baseURL + "monitor/monitoriotdevice/info/" +iotId, function (r) {
            vm.monitorIotDevice = r.monitorIotDevice;
        });
    }
,
    reload: function (event) {
        vm.showList = true;
        var page = $("#jqGrid").jqGrid('getGridParam', 'page');
        $("#jqGrid").jqGrid('setGridParam', {
            page: page
        }).trigger("reloadGrid");
    }
}
});