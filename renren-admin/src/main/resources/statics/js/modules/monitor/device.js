$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'monitor/device/list',
        datatype: "json",
        colModel: [			
			{ label: 'devId', name: 'devId', index: 'dev_id', width: 50, key: true },
			{ label: 'ip地址', name: 'ip', index: 'ip', width: 80 }, 			
			{ label: '设备名称', name: 'deviceName', index: 'device_name', width: 80 }, 			
			{ label: '设备状态', name: 'deviceStatus', index: 'device_status', width: 80 }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		device: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.device = {};
		},
		update: function (event) {
			var devId = getSelectedRow();
			if(devId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(devId)
		},
		saveOrUpdate: function (event) {
			var url = vm.device.devId == null ? "monitor/device/save" : "monitor/device/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.device),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var devIds = getSelectedRows();
			if(devIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "monitor/device/delete",
                    contentType: "application/json",
				    data: JSON.stringify(devIds),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(devId){
			$.get(baseURL + "monitor/device/info/"+devId, function(r){
                vm.device = r.device;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});