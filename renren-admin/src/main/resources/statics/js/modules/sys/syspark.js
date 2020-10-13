$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/syspark/list',
        datatype: "json",
        colModel: [
                                                {
                        label: 'parkId',
                        name: 'parkId',
                        index: 'park_id',
                        width: 50,
                        key: true
                    },
                                                                {
                        label: '园区名称',
                        name: 'parkName',
                        index: 'park_name',
                        width: 80
                    }, 
                                                                {
                        label: 'tp用户名',
                        name: 'username',
                        index: 'username',
                        width: 80
                    }, 
                                                                {
                        label: 'tp密码',
                        name: 'password',
                        index: 'password',
                        width: 80
                    }, 
                                                                {
                        label: 'tp域名',
                        name: 'website',
                        index: 'website',
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
sysPark: {
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
        vm.sysPark = {};
    }
,
    update: function (event) {
        var parkId =
        getSelectedRow();
        if (parkId == null
    )
        {
            return;
        }
        vm.showList = false;
        vm.title = "修改";

        vm.getInfo(parkId)
    }
,
    saveOrUpdate: function (event) {
        var url = vm
    .sysPark.parkId ==
        null ? "sys/syspark/save" : "sys/syspark/update";
        $.ajax({
            type: "POST",
            url: baseURL + url,
            contentType: "application/json",
            data: JSON.stringify(vm.sysPark),
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
        var parkIds = getSelectedRows();
        if (parkIds == null) {
            return;
        }

        confirm('确定要删除选中的记录？', function () {
            $.ajax({
                type: "POST",
                url: baseURL + "sys/syspark/delete",
                contentType: "application/json",
                data: JSON.stringify(parkIds),
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
    getInfo: function (parkId) {
        $.get(baseURL + "sys/syspark/info/" +parkId, function (r) {
            vm.sysPark = r.sysPark;
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