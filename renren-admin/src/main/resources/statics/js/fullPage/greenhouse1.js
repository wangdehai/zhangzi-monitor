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
        $.ajaxSetup({cache:false});
        vm.getWeater();
        vm.getTable();
        vm.init();
        vm.getDevInfo();
        // vm.getDevInfo1();
    },900000);
})
window.onload = function () {
    vm.init();
}

var vm = new Vue({
    el:'#box',
    data:{
        data:'',
        time:'',
        tableData:[],
        mapId:5,
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
        weaterT:[],
        info:{},
        info1:{},
        hightTem:'',
        lowTem:'',
        videoUrl:''
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
                vm.mapDevId = marker.param.mapDevId;
                var index = layer.load(2);
                vm.markVisible = true;
                $.ajax({
                    url: '../../monitor/device/getPreviewUrl',
                    type: 'get',
                    data: {
                        devId:marker.param.devId
                    },
                    contentType: "application/json",
                    // dataType: 'json',
                    success: function (r) {
                        layer.close(index);
                        if (r.code === 0) {
                            vm.videoUrl = r.url;

                            var vlc=document.getElementById('vlc');
                            var options = new Array("rtsp-tcp");
                            vlc.playlist.clear();
                            vlc.playlist.add(r.url,'',options);
                            vlc.playlist.play();
                        } else {
                            vm.markVisible = false;
                            layer.alert(r.msg);
                        }

                    },
                    error: function () {
                        vm.markVisible = false;
                        layer.close(index);
                        layer.msg("网络故障");
                    }
                });
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
            $('#imgBg').css('display','inline-block')
            $('#imgBg').css('width',width+'px')
            console.log($('#imgBg').height())
            $('#imgBg').css('top',($('#zoom-marker-div').height()-$('#imgBg').height())/2 + 'px')
            $('.gif').css({
                'height':$('#imgBg').height()+'px',
                'top':($('#zoom-marker-div').height()-$('#imgBg').height())/2 + 'px'
            })
        },
        // 获取设备信息
        getDevInfo:function () {
            $.ajax({
                url: '../../monitor/monitoriotdevice/showList',
                type: 'get',
                data: {
                    regionId:1
                },
                contentType: "application/json",
                // dataType: 'json',
                success:function (r) {
                    if(r.code === 0){
                        console.log(r);
                        r.iotList.forEach(function (item) {
                            vm.info[item.devKey] = item.value
                        })
                    }else {
                        layer.alert(r.msg);
                    }
                },
                error:function () {
                    layer.msg("网络故障");
                }

            })
        },
        // 获取设备信息
        getDevInfo1:function () {
            $.ajax({
                url: '../../monitor/monitoriotdevice/showList',
                type: 'get',
                data: {
                    regionId:'3'
                },
                contentType: "application/json",
                // dataType: 'json',
                success:function (r) {
                    if(r.code === 0){
                        console.log(r);
                        vm.siwangData = [];
                        r.iotList.forEach(function (item) {
                            vm.info1[item.devKey] = item.value

                        })
                        // siwang(vm.siwangData);
                    }else {
                        layer.alert(r.msg);
                    }
                },
                error:function () {
                    layer.msg("网络故障");
                }

            })
        },
        getHightTem:function () {
            $.ajax({
                url: '../../sys/dict/selectTem',
                type: 'get',
                data: '',
                // contentType: "application/json",
                dataType: 'json',
                success:function (r) {
                    if(r.code === 0){
                        console.log(r);
                        vm.hightTem = r.tem;
                        vm.lowTem = r.lowTem;

                    }else {
                        layer.alert(r.msg);
                    }
                },
                error:function () {
                    layer.msg("网络故障");
                }

            })
        }
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
        this.getDevInfo();
        this.getDevInfo1();
        this.getHightTem();
        var that = this;
        setInterval(function(){
            that.getTime()
        },900);

    },
    mounted:function(){

        if(this.weater.wid==13||this.weater.wid==14||this.weater.wid==15||this.weater.wid==16||this.weater.wid==17||this.weater.wid==06||this.weater.wid==26||this.weater.wid==27||this.weater.wid==28){
            this.xue();
        }
    }
})