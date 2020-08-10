


var vm = new Vue({
    el:'#rrapp',
    data:{
        page:1,
        projectId:[],
        options:[],
        tableData:[],
        regionList:[],
        regionId:'',
        total:0,
        page:1,

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
                        console.log(vm.options);
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
        changePro:function(id){
            console.log(id);
            vm.getRegionList()
        },
        getList:function(){
            $.ajax({
                url: '../../monitor/device/list',
                type: 'get',
                data: {
                    page:this.page,
                    // projectId:this.projectId[this.projectId.length-1],
                    regionId:this.regionId
                },
                contentType: "application/json",
                // dataType: 'json',
                success: function (r) {
                    if (r.code === 0) {
                        vm.tableData = r.page.list;
                        vm.total = r.page.totalCount
                    } else {
                        layer.alert(r.msg);
                    }
                },
                error: function () {
                    layer.msg("网络故障");
                }
            });
        },
        currentChange:function (page) {
            vm.page = page;
            vm.getList();
        },
        getRegionList:function(){
            if(vm.projectId.length == 0){
                this.$message({
                    message: '请先选择项目',
                    type: 'warning'
                });
            }else {
                $.ajax({
                    url: '../../monitor/region/list',
                    type: 'get',
                    data: {
                        projectId:vm.projectId[vm.projectId.length-1]
                    },
                    // contentType: "application/json",
                    dataType: 'json',
                    success: function (r) {
                        if (r.code === 0) {
                            vm.regionList = r.list
                        } else {
                            layer.alert(r.msg);
                        }
                    },
                    error: function () {
                        layer.msg("网络故障");
                    }
                });
            }

        },

    },
    created:function () {
        this.getProList();
        this.getList();
    }
});