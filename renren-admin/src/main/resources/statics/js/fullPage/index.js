function tran(str) {
	var v, j, sj, rv = "";
	v = str.replace(/,/g, "").split(".");
	j = v[0].length % 3;
	sj = v[0].substr(j).toString();
	for(var i = 0; i < sj.length; i++) {
		rv = (i % 3 == 0) ? rv + "," + sj.substr(i, 1) : rv + sj.substr(i, 1);
	}
	var rvalue = (v[1] == undefined) ? v[0].substr(0, j) + rv : v[0].substr(0, j) + rv + "." + v[1];
	if(rvalue.charCodeAt(0) == 44) {
		rvalue = rvalue.substr(1);
	}
	str = rvalue;
	console.log(str);
}
//tran('123456789')

$(function(){
    setInterval(function(){
        vm.getWeater();
        vm.getTable();
    },15000);
})

var vm = new Vue({
	el:'#box',
	data:{
		data:'',
		time:'',
		zrcl:123456,
		bycl:123556,
		byclz:12345678,
		ljcl:123456,
		ljclz:123456789,
		tableData:[],
	    siwangData:[
			{
	    			type:'臭氧',
	    			num:50
	    		},{
	    			type:'氧气',
	    			num:80
	    		},{
	    			type:'二氧化碳',
	    			num:40
	    		},{
	    			type:'一氧化碳',
	    			num:60
	    		},{
	    			type:'二氧化硫',
	    			num:20
	    		},{
	    			type:'二氧化氮',
	    			num:30
	    		},{
	    			type:'氮气',
	    			num:10
	    		}
		],
		mapId:5,
		projectId:1,
		regionId:2,
		tagNumber:1,
		mapDevId:'',
		markVisible:false,
		weater:{},
		weaterL:[],
		weaterH:[],
        weaterT:[]
	},
	methods:{
		getDate:function(){
			var aa = new Date();
			var a1 = aa.getFullYear();
			var a2 = aa.getMonth() + 1;
			if(a2 < 10){
				a2 = '0' + a2;
			}
			var a3 = aa.getDate();
			if(a3 < 10){
				a3 = '0' + a3
			}
			this.data = a1 + '-' + a2 + '-' + a3;
		},
		getTime:function(){
			var aa = new Date();
			var h = aa.getHours();
			var m = aa.getMinutes();
			var s = aa.getSeconds();
			if(h < 10){
				h = '0' + h
			}
			if(m < 10){
				m = '0' + m
			}
			if(s < 10){
				s = '0' + s
			}
			
			this.time = h + ':' + m + ':' + s;

		},
		getWeater:function () {
            $.ajax({
                url: '../../monitor/weather/getWeather',
				 type: 'get',
				 data: '',
				 contentType: "application/json",
				 // dataType: 'json',
				  success:function (r) {
						if(r.code === 0){
							console.log(r);
							vm.weater = r.weather;
                            vm.weaterH = [];
                            vm.weaterL = [];
                            vm.weaterT = [];
                            r.weather.weatherItemList.forEach(function (item) {
                            	var arr = item.temperature.split('/');
								vm.weaterH.push(arr[1].split("℃")[0]);
								vm.weaterL.push(arr[0]);
								vm.weaterT.push(item.date);

                            })
							var obj = {
                                xData:vm.weaterT,
								data:[vm.weaterH,vm.weaterL]
							}
                            wendu(obj);
						}else {
                            layer.alert(r.msg);
						}
				  },
				  error:function () {
                      layer.msg("网络故障");
                  }

			})
        },
		getTable:function () {
            $.ajax({
                url: '../../monitor/device/showDevList',
                type: 'get',
                data: {
                    projectId:'',
                    regionId:''
				},
                // contentType: "application/json",
                dataType: 'json',
                success:function (r) {
                    if(r.code === 0){
                        console.log(r);
						vm.tableData = r.devList;
                    }else {
                        layer.alert(r.msg);
                    }
                },
                error:function () {
                    layer.msg("网络故障");
                }

            })
        },
		setTable:function () {
			var _height = $('#tableDiv').height();
        },
		init:function(){
            $.ajax({
                url: '../../monitor/mapdev/showMainList',
                type: 'get',
                data: '',
                // contentType: "application/json",
                dataType: 'json',
                success: function (r) {
                    if (r.code === 0) {
                        console.log(r);
                        r.list.forEach(function (item) {
                            item.src = "../../statics/css/fullPage/img/marker.svg";
//                          item.src = "img/marker.svg";
                            item.size = 40;
                            item.draggable = false;
                            // var name = JSON.stringify(item.signName) != 'null' ? item.signName : '暂无名称';
                            item.dialog = {
                                value: "<h4>"+item.signName+"</h4>",
                                offsetX: 20,
                                style: {
                                    "border-color": "#06adff",
                                    "background":'rgba(6,173,255,.3)',
                                    "color":'#0c9fe7'
                                }
                            };
                        })
                        vm.imgEvent($('#zoom-marker-img-alt'));
                        vm.initImg(r.list);
                    } else {
                        layer.alert(r.msg);
                    }
                },
                error: function () {
                    layer.msg("网络故障");
                }
            });
//
// 			var r = {
// 				list:[{"mapDevId":1,"mapId":11,"devId":"1","deviceName":"牛棚","x":3184.259783653846,"y":217.56828962053572},
// 					{"mapDevId":2,"mapId":5,"devId":"1","deviceName":"大棚1","x":2666.916274038462,"y":780.3079659598214},
// 					{"mapDevId":4,"mapId":5,"devId":"1","deviceName":"大棚3","x":1140.7063341346154,"y":771.8507952008929},
// 					{"mapDevId":3,"mapId":5,"devId":"1","deviceName":"大棚2","x":2422.5116466346153,"y":1746.2193777901784}]
// 			}
// //                      console.log(r);
// 			r.list.forEach(function (item) {
// 				item.src = "../../statics/css/fullPage/img/marker.svg";
// //                          item.src = "img/marker.svg";
// 				item.size = 40;
// 				item.draggable = false;
// 				item.dialog = {
// 					value: "<h4>"+item.deviceName+"</h4>",
// 					offsetX: 20,
// 					style: {
// 						"border-color": "#06adff",
// 						"background":'rgba(6,173,255,.3)',
// 						"color":'#0c9fe7'
// 					}
// 				};
// 			})
// //                      console.log(r.list);
// 			this.imgEvent($('#zoom-marker-img-alt'));
// 			this.initImg(r.list);
		},
		imgEvent:function (item) {
            var that = this;
            // console.log($('#zoom-marker-img-alt'+vm.imgurl.split('.')[0]+'-'+vm.imgCNum));
            item.on("zoom_marker_mouse_click", function(event, position) {
                
            });

            item.on("zoom_marker_click", function(event, marker) {
                console.log(JSON.stringify(marker));
                if(marker.param.regionId == '2'){
                    window.location.href = 'cowshed.html?id='+marker.param.mapId+'&map='+marker.param.mapName+'&reId='+marker.param.regionId
				}else if(marker.param.regionId == '3'){
                    window.location.href = 'greenhouse.html?id='+marker.param.mapId+'&map='+marker.param.mapName+'&reId='+marker.param.regionId
				}
                // else if(marker.param.signName == '大棚2'){
                //     window.location.href = 'greenhouse1.html?id='+marker.param.mapId+'&map='+marker.param.mapName+'&reId='+marker.param.regionId
                // }else if(marker.param.signName == '大棚3'){
                //     window.location.href = 'greenhouse2.html?id='+marker.param.mapId+'&map='+marker.param.mapName+'&reId='+marker.param.regionId
                // }

                // vm.mapDevId = marker.param.mapDevId;
                // vm.markVisible = true;
                // $('#zoom-marker-img-alt'+vm.imgurl.split('.')[0]+'-'+vm.imgCNum).zoomMarker_RemoveMarker(marker.id);
            });
            item.on("zoom_marker_move_end", function (event, marker) {
                console.log(marker);
            });

            item.on("zoom_marker_img_loaded", function(event, size) {
                // console.log("image has been loaded with size: " + JSON.stringify(size));
            });
        },
        initImg:function (markers) {
        		var width = $('#zoom-marker-div').width();
        		console.log($('#zoom-marker-img-alt'));
            $('#zoom-marker-img-alt').zoomMarker({
                src: "../../statics/img/1595301032060.jpg",
                rate: 0.2,
                width: width,
                max: width,
                min:width,
                markers: markers,
                enable_drag:false,
                
            });
        },
	},
	created:function(){
		this.getDate();
		this.getTime();
		this.getWeater();
		this.getTable();
		var that = this;
		setInterval(function(){
			that.getTime()
		},900);
		
	},
	mounted:function(){
		this.init();
	}
})