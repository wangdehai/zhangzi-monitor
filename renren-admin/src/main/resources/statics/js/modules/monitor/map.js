$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'monitor/map/list',
        datatype: "json",
        colModel: [			
			{ label: 'mapId', name: 'mapId', index: 'map_id', width: 50, key: true },
			{ label: '地图名称', name: 'mapName', index: 'map_name', width: 80 }, 			
			{ label: '项目id', name: 'projectId', index: 'project_id', width: 80 }, 			
			{ label: '区域id', name: 'regionId', index: 'region_id', width: 80 }, 			
			{ label: '地图路径', name: 'mapUrl', index: 'map_url', width: 80 }			
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
		map: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.map = {};
		},
		update: function (event) {
			var mapId = getSelectedRow();
			if(mapId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(mapId)
		},
		saveOrUpdate: function (event) {
			var url = vm.map.mapId == null ? "monitor/map/save" : "monitor/map/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.map),
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
			var mapIds = getSelectedRows();
			if(mapIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "monitor/map/delete",
                    contentType: "application/json",
				    data: JSON.stringify(mapIds),
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
		getInfo: function(mapId){
			$.get(baseURL + "monitor/map/info/"+mapId, function(r){
                vm.map = r.map;
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