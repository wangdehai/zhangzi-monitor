$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'monitor/weather/list',
        datatype: "json",
        colModel: [			
			{ label: 'weatherId', name: 'weatherId', index: 'weather_id', width: 50, key: true },
			{ label: '温度', name: 'temperature', index: 'temperature', width: 80 }, 			
			{ label: '湿度', name: 'humidity', index: 'humidity', width: 80 }, 			
			{ label: '天气情况', name: 'info', index: 'info', width: 80 }, 			
			{ label: '天气标识id', name: 'wid', index: 'wid', width: 80 }, 			
			{ label: '风向', name: 'direct', index: 'direct', width: 80 }, 			
			{ label: '风力', name: 'power', index: 'power', width: 80 }, 			
			{ label: '空气质量指数', name: 'aqi', index: 'aqi', width: 80 }, 			
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
		weather: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.weather = {};
		},
		update: function (event) {
			var weatherId = getSelectedRow();
			if(weatherId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(weatherId)
		},
		saveOrUpdate: function (event) {
			var url = vm.weather.weatherId == null ? "monitor/weather/save" : "monitor/weather/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.weather),
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
			var weatherIds = getSelectedRows();
			if(weatherIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "monitor/weather/delete",
                    contentType: "application/json",
				    data: JSON.stringify(weatherIds),
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
		getInfo: function(weatherId){
			$.get(baseURL + "monitor/weather/info/"+weatherId, function(r){
                vm.weather = r.weather;
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