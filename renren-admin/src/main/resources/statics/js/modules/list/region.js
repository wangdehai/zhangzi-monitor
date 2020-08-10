


var vm = new Vue({
    el:'#rrapp',
    data:{
        page:1,
        projectId:null,
        options:[],
        tableData:[],
    },
    methods: {
        getProList:function () {
            $.ajax({
                url: '../../monitor/project/list',
                type: 'post',
                data: JSON.stringify(),
                contentType: "application/json",
                // dataType: 'json',
                success: function (r) {
                    if (r.code === 0) {
                        vm.options = vm.processingData(r.list);
                        // vm.statisticsProfit = r.dto
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
        },
        // 选择项目
        changePro:function(){
            vm.getList(vm.projectId[vm.projectId.length-1])
        },

        getList:function(id){
            $.ajax({
                url: '../../monitor/region/list',
                type: 'get',
                data: {
                    projectId:id
                    // startDate:this.startDate,
                    // endDate:this.endDate,
                    // 'deptId':this.allGongsiValue,
                    // 'userId':this.allYUanGValue,
                },
                // contentType: "application/json",
                dataType: 'json',
                success: function (r) {
                    if (r.code === 0) {
                        vm.tableData = r.list
                    } else {
                        layer.alert(r.msg);
                    }
                },
                error: function () {
                    layer.msg("网络故障");
                }
            });
        },

    },
    created:function () {
        this.getProList();
        // this.getList();
    }
});