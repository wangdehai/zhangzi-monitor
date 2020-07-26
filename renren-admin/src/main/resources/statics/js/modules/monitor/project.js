$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'monitor/project/list',
        datatype: "json",
        colModel: [			
			{ label: 'projectId', name: 'projectId', index: 'project_id', width: 50, key: true },
			{ label: '项目名称', name: 'projectName', index: 'project_name', width: 80 }, 			
			{ label: '项目层级内顺序', name: 'order', index: 'order', width: 80 }, 			
			{ label: '项目层级', name: 'projectLevel', index: 'project_level', width: 80 }, 			
			{ label: '父项目id', name: 'parentId', index: 'parent_id', width: 80 }, 			
			{ label: '是否还有子项目，0为无，1为有', name: 'hasChildren', index: 'has_children', width: 80 }			
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
		project: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.project = {};
		},
		update: function (event) {
			var projectId = getSelectedRow();
			if(projectId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(projectId)
		},
		saveOrUpdate: function (event) {
			var url = vm.project.projectId == null ? "monitor/project/save" : "monitor/project/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.project),
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
			var projectIds = getSelectedRows();
			if(projectIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "monitor/project/delete",
                    contentType: "application/json",
				    data: JSON.stringify(projectIds),
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
		getInfo: function(projectId){
			$.get(baseURL + "monitor/project/info/"+projectId, function(r){
                vm.project = r.project;
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