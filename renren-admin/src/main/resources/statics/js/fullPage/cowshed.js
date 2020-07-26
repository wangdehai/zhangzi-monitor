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
//	$('#zoom-marker-img-alt').zoomMarker({
//      src: "img/1594812489444.jpg",
//      rate: 0.2,
//      width: 715,
//      max: 3000,
//      markers: [{
//      	"src":"img/marker.svg",
//      	"draggable":false,
//      	"mapDevId":75,"mapId":5,"devId":"1","deviceName":"TP-LINK IPC","x":"1876.2057692307694","y":"1143.180023331448"
//      	}],
////              enable_drag:false
//  });
//	$('.timer').each(count);
//	mingandu(vm.minganduData);
//	qushi(vm.qushiData);
    siwang(vm.siwangData);
//	xiaoshou(vm.xiaoshouData);
//	paihang(vm.paihangData);
    setInterval(function(){
        vm.getWeater();
        vm.getTable();
        vm.init();

    },900000);
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
        siwangData:[{
                type:'二氧化碳',
                num:40
            },{
                type:'硫化氢',
                num:60
            },{
                type:'氮气',
                num:10
            }
        ],
        mapId:'',
        mapName:'',
        reId:'',
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
        getTable:function () {
            $.ajax({
                url: '../../monitor/device/showDevList',
                type: 'get',
                data: {
                    regionId:this.reId
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
                    }else {
                        layer.alert(r.msg);
                    }
                },
                error:function () {
                    layer.msg("网络故障");
                }

            })
        },
        init:function(){
			$.ajax({
             url: '../../monitor/mapdev/showList',
             type: 'get',
             data: {
                 mapId:this.mapId
             },
             contentType: "application/json",
             // dataType: 'json',
             success: function (r) {
                 if (r.code === 0) {
                    // var r = {
                    //     list:[{"mapDevId":75,"mapId":5,"devId":"1","deviceName":"TP-LINK IPC","x":"1876.2057692307694","y":"1143.180023331448"}]
                    // }
                    r.list.forEach(function (item) {
                        item.src = "../../statics/css/fullPage/img/marker.svg";
                        item.size = 30;
                        item.draggable = false;
                        var str = item.deviceName ? item.deviceName : '暂无关联设备';
                        item.dialog = {
                            value: "<h4>"+str+"</h4>",
                            offsetX: 20,
                            style: {
                                "border-color": "#86df5f"
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
        },
        imgEvent:function (item) {
            var that = this;
            // console.log($('#zoom-marker-img-alt'+vm.imgurl.split('.')[0]+'-'+vm.imgCNum));
            item.on("zoom_marker_mouse_click", function(event, position) {

            });

            item.on("zoom_marker_click", function(event, marker) {
                console.log(JSON.stringify(marker));
                // window.location.href = 'greenhouse.html'
                vm.mapDevId = marker.param.mapDevId;
                vm.markVisible = true;
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
                src: "../../statics/img/"+vm.mapName+'.jpg',
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
        var url = decodeURI(window.location.href);
        var argsIndex = url.split("?id=");
        var argsIndex1 = argsIndex[1].split("&map=");
        this.mapId = argsIndex1[0];
        var argsIndex2 = argsIndex1[1].split("&reId=");
        this.mapName = argsIndex2[0];
        this.reId = argsIndex2[1];
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