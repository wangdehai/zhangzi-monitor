$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'monitor/region/list',
        datatype: "json",
        colModel: [			
			{ label: 'regionId', name: 'regionId', index: 'region_id', width: 50, key: true },
			{ label: '区域名称', name: 'regionName', index: 'region_name', width: 80 }, 			
			{ label: '项目层级内顺序', name: 'order', index: 'order', width: 80 }, 			
			{ label: '区域层级', name: 'regionLevel', index: 'region_level', width: 80 }, 			
			{ label: '区域类型', name: 'sysType', index: 'sys_type', width: 80 }, 			
			{ label: '父区域id', name: 'parentId', index: 'parent_id', width: 80 }, 			
			{ label: '区域所属项目id', name: 'projectId', index: 'project_id', width: 80 }, 			
			{ label: '是否还有子区域，0为无，1为有', name: 'hasChildren', index: 'has_children', width: 80 }, 			
			{ label: '流媒体服务器id', name: 'mediaServerId', index: 'media_server_id', width: 80 }, 			
			{ label: '流媒体服务器名称', name: 'mediaServerName', index: 'media_server_name', width: 80 }, 			
			{ label: '流媒体服务器Ip地址', name: 'mediaServerIpAddr', index: 'media_server_ip_addr', width: 80 }			
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
		region: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.region = {};
		},
		update: function (event) {
			var regionId = getSelectedRow();
			if(regionId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(regionId)
		},
		saveOrUpdate: function (event) {
			var url = vm.region.regionId == null ? "monitor/region/save" : "monitor/region/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.region),
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
			var regionIds = getSelectedRows();
			if(regionIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "monitor/region/delete",
                    contentType: "application/json",
				    data: JSON.stringify(regionIds),
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
		getInfo: function(regionId){
			$.get(baseURL + "monitor/region/info/"+regionId, function(r){
                vm.region = r.region;
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