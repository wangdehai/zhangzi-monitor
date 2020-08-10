


var vm = new Vue({
    el:'#rrapp',
    data:{
        tableData:[]
    },
    methods: {
        getList:function(){
            $.ajax({
                url: '../../monitor/project/list',
                type: 'post',
                data: JSON.stringify(),
                contentType: "application/json",
                // dataType: 'json',
                success: function (r) {
                    if (r.code === 0) {
                        vm.tableData = vm.processingData(r.list);
                        console.log(vm.tableData);

                    } else {
                        layer.alert(r.msg);
                    }
                },
                error: function () {
                    layer.msg("网络故障");
                }
            });
        },
        // 树型数据处理
        processingData:function(data){
            var result = []
            if(!Array.isArray(data)) {
                return result
            }
            data.forEach(function (item) {
                delete item.children;
            })

            var map = {};
            data.forEach(function (item) {
                map[item.projectId] = item;
            })

            data.forEach(function (item) {
                var parent = map[item.parentId];
                if(parent) {
                    (parent.children || (parent.children = [])).push(item);
                } else {
                    result.push(item);
                }
            })
            return result;
            //     data.forEach(item => {
            //         delete item.children;
            // });
            //     data.forEach(item => {
            //         map[item.projectId] = item;
            // });
            // data.forEach(item => {
            //     let parent = map[item.parentId];
            //     if(parent) {
            //         (parent.children || (parent.children = [])).push(item);
            //     } else {
            //         result.push(item);
            //     }
            // });

        },
    },
    created:function () {
        this.getList();
    }
});

// var Menu = {
//     id: "projectTable",
//     table: null,
//     layerIndex: -1
// };
//
// /**
//  * 初始化表格的列
//  */
// Menu.initColumn = function () {
//     var columns = [
//         {field: 'selectItem', radio: true},
//         {title: '项目ID', field: 'projectId', visible: false, align: 'center', valign: 'middle', width: '80px'},
//         {title: '项目名称', field: 'projectName', align: 'center', valign: 'middle', sortable: true, width: '180px'}
//     ]
//     return columns;
// };
//
// // monitor/project/list
// // projectId
// // projectName
// // projectTable
// $(function () {
//     var colunms = Menu.initColumn();
//     var table = new TreeTable(Menu.id, baseURL + "monitor/project/list", colunms);
//     table.setExpandColumn(2);
//     table.setIdField("projectId");
//     table.setCodeField("projectId");
//     table.setParentCodeField("parentId");
//     table.setExpandAll(false);
//     table.init();
//     Menu.table = table;
// });