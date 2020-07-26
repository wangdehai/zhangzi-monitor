$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'monitor/weatheritem/list',
        datatype: "json",
        colModel: [			
			{ label: 'weatherItmeId', name: 'weatherItmeId', index: 'weather_itme_id', width: 50, key: true },
			{ label: '日期', name: 'date', index: 'date', width: 80 }, 			
			{ label: '温度，最低温/最高温', name: 'temperature', index: 'temperature', width: 80 }, 			
			{ label: '天气情况', name: 'weather', index: 'weather', width: 80 }, 			
			{ label: '风向', name: 'direct', index: 'direct', width: 80 }, 			
			{ label: '更新时间', name: 'updateTime', index: 'update_time', width: 80 }			
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
		weatherItem: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.weatherItem = {};
		},
		update: function (event) {
			var weatherItmeId = getSelectedRow();
			if(weatherItmeId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(weatherItmeId)
		},
		saveOrUpdate: function (event) {
			var url = vm.weatherItem.weatherItmeId == null ? "monitor/weatheritem/save" : "monitor/weatheritem/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.weatherItem),
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
			var weatherItmeIds = getSelectedRows();
			if(weatherItmeIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "monitor/weatheritem/delete",
                    contentType: "application/json",
				    data: JSON.stringify(weatherItmeIds),
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
		getInfo: function(weatherItmeId){
			$.get(baseURL + "monitor/weatheritem/info/"+weatherItmeId, function(r){
                vm.weatherItem = r.weatherItem;
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