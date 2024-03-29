$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'monitor/montiormapdev/list',
        datatype: "json",
        colModel: [			
			{ label: 'mapDevId', name: 'mapDevId', index: 'map_dev_id', width: 50, key: true },
			{ label: '', name: 'mapId', index: 'map_id', width: 80 }, 			
			{ label: '', name: 'devId', index: 'dev_id', width: 80 }, 			
			{ label: '', name: 'deviceName', index: 'device_name', width: 80 }, 			
			{ label: '', name: 'x', index: 'x', width: 80 }, 			
			{ label: '', name: 'y', index: 'y', width: 80 }			
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
		montiorMapDev: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.montiorMapDev = {};
		},
		update: function (event) {
			var mapDevId = getSelectedRow();
			if(mapDevId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(mapDevId)
		},
		saveOrUpdate: function (event) {
			var url = vm.montiorMapDev.mapDevId == null ? "monitor/montiormapdev/save" : "monitor/montiormapdev/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.montiorMapDev),
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
			var mapDevIds = getSelectedRows();
			if(mapDevIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "monitor/montiormapdev/delete",
                    contentType: "application/json",
				    data: JSON.stringify(mapDevIds),
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
		getInfo: function(mapDevId){
			$.get(baseURL + "monitor/montiormapdev/info/"+mapDevId, function(r){
                vm.montiorMapDev = r.montiorMapDev;
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