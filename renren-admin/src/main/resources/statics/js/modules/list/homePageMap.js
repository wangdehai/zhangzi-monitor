


var vm = new Vue({
    el:'#rrapp',
    data:{
        page:1,
        projectId:[],
        projectIdS:[],
        options:[],
        regionList:[],
        regionId:'',
        regionIdS:'',
        mapList:[],
        mapId:'',
        imgVisible:false,
        regionVisible:false,
        fileList:[],
        imgurl:'',
        imgRegion:false,
        tagNumber:1,
        imgCNum:0,
        markVisible:false,
        marksList:[],
        nowMapId:'',
        nowProId:'',
        nowReId:'',
        deviceList:[],
        linkDevId:'',
        linkDevName:'',
        nowMarkId:'',
        mapDevId:'',
        devNoList:[],
        addMapDevId:'',
        linkDevIdI:'',
        markObj:{
            signName:'',
            mapId:'',
            mapName:''
        },
        imgCNum:0
    },
    methods: {
        getProList:function () {
            $.ajax({
                url: '../../monitor/project/list',
                type: 'post',
                data: JSON.stringify(),
                contentType: "application/json",
                // dataType: 'json',
                success: function (r) {
                    if (r.code === 0) {
                        vm.options = vm.processingData(r.list);
                        // vm.statisticsProfit = r.dto
                    } else {
                        layer.alert(r.msg);
                    }
                },
                error: function () {
                    layer.msg("网络故障");
                }
            });
        },
        // 树型数据处理
        processingData:function(data){
            var result = []
            if(!Array.isArray(data)) {
                return result
            }
            data.forEach(function (item) {
                delete item.children;
            })

            var map = {};
            data.forEach(function (item) {
                map[item.projectId] = item;
            })

            data.forEach(function (item) {
                var parent = map[item.parentId];
                if(parent) {
                    (parent.children || (parent.children = [])).push(item);
                } else {
                    result.push(item);
                }
            })
            return result;
        },
        getRegionList:function(){
            if(vm.projectIdS.length == 0 && vm.regionVisible){
                this.$message({
                    message: '请先选择项目',
                    type: 'warning'
                });
            }else if(vm.projectId.length == 0 && !vm.regionVisible){
                this.$message({
                    message: '请先选择项目',
                    type: 'warning'
                });
            }else{
                var id = '';
                if(vm.regionVisible){
                    id = vm.projectIdS[vm.projectIdS.length-1]
                }else {
                    id = vm.projectId[vm.projectId.length-1]
                }
                $.ajax({
                    url: '../../monitor/region/list',
                    type: 'get',
                    data: {
                        projectId:id
                    },
                    // contentType: "application/json",
                    dataType: 'json',
                    success: function (r) {
                        if (r.code === 0) {
                            vm.regionList = r.list
                        } else {
                            layer.alert(r.msg);
                        }
                    },
                    error: function () {
                        layer.msg("网络故障");
                    }
                });
            }

        },
        getMapList:function(){
            $.ajax({
                url: '../../monitor/map/list',
                type: 'get',
                data: '',
                // contentType: "application/json",
                dataType: 'json',
                success: function (r) {
                    if (r.code === 0) {
                        vm.mapList = r.list
                    } else {
                        layer.alert(r.msg);
                    }
                },
                error: function () {
                    layer.msg("网络故障");
                }
            });
        },
        // 获取主图的标记点
        getMarksList:function () {
            $('#zoom-marker-div').empty();
            this.imgCNum++;
            var $img = $('<img class="zoom-marker-img" id="zoom-marker-img-alt'+this.imgCNum+'" alt="zoom-marker-img-alt" name="zoom-marker-img-alt" draggable="false" />');
            $('#zoom-marker-div').append($img);
            $.ajax({
                url: '../../monitor/mapdev/mainList',
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
                            // item.draggable = false;
                            var name = JSON.stringify(item.signName) != 'null' ? item.signName : '暂无名称';
                            item.dialog = {
                                value: "<h4>"+name+"</h4>",
                                offsetX: 20,
                                style: {
                                    "border-color": "#06adff",
                                    "background":'rgba(6,173,255,.3)',
                                    "color":'#0c9fe7'
                                }
                            };
                        })
                        vm.init($('#zoom-marker-img-alt'+vm.imgCNum));
                        vm.initImg(r.list);
                    } else {
                        layer.alert(r.msg);
                    }
                },
                error: function () {
                    layer.msg("网络故障");
                }
            });
            // var r = {
            //     list:[{"mapDevId":1,"mapId":5,"devId":"1","deviceName":"牛棚","x":3184.259783653846,"y":217.56828962053572},
            //         {"mapDevId":2,"mapId":5,"devId":"1","deviceName":"大棚1","x":2666.916274038462,"y":780.3079659598214},
            //         {"mapDevId":4,"mapId":5,"devId":"1","deviceName":"大棚3","x":1140.7063341346154,"y":771.8507952008929},
            //         {"mapDevId":3,"mapId":5,"devId":"1","deviceName":"大棚2","x":2422.5116466346153,"y":1746.2193777901784}]
            // };
        },
        // 新增标记点
        addMark:function (position) {
            var index = layer.load(2);
            $.ajax({
                url: '../../monitor/mapdev/addMain',
                type: 'post',
                data: JSON.stringify({
                    x:position.x,
                    y:position.y,
                }),
                async: false,
                contentType: "application/json",
                // dataType: 'json',
                success: function (r) {
                    layer.close(index);
                    if (r.code === 0) {
                        console.log(r);
                        vm.addMapDevId = r.mapDevId;
                    } else {
                        layer.alert(r.msg);
                    }
                },
                error: function () {
                    layer.msg("网络故障");
                }
            });
        },
        // 删除标记点
        delMark:function () {
            this.$confirm('确定删除该标记点吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                var index = layer.load(2);
                $.ajax({
                    url: '../../monitor/mapdev/deleteMain',
                    type: 'get',
                    data: {
                        mapDevId:vm.mapDevId
                        // mapDevId:vm.mapDevId
                    },
                    // async: false,
                    // contentType: "application/json",
                    dataType: 'json',
                    success: function (r) {
                        layer.close(index);
                        if (r.code === 0) {
                            console.log(r);
                            $('#zoom-marker-img-alt'+vm.imgCNum).zoomMarker_RemoveMarker(vm.nowMarkId);
                            vm.markVisible = false;
                            // vm.getDeviceList();

                        } else {
                            layer.alert(r.msg);
                        }
                    },
                    error: function () {
                        layer.msg("网络故障");
                    }
                });
            }).catch(function () {

            });


        },
        // 修改标记点
        upMark:function (position) {
            var index = layer.load(2);
            $.ajax({
                url: '../../monitor/mapdev/saveMain',
                type: 'post',
                data: JSON.stringify({
                    mapDevId:position.id,
                    x:position.x,
                    y:position.y,
                    // mapDevId:vm.mapDevId
                }),
                // async: false,
                contentType: "application/json",
                // dataType: 'json',
                success: function (r) {
                    layer.close(index);
                    if (r.code === 0) {
                        console.log(r);
                        // $('#zoom-marker-img-alt'+vm.imgurl.split('.')[0]+'-'+vm.imgCNum).zoomMarker_RemoveMarker(vm.nowMarkId);
                    } else {
                        layer.alert(r.msg);
                    }
                },
                error: function () {
                    layer.msg("网络故障");
                }
            });

        },
        // 保存按钮
        saveDev:function () {
            var index = layer.load(2);
            $.ajax({
                url: '../../monitor/mapdev/saveMain',
                type: 'post',
                data: JSON.stringify({
                    mapDevId:vm.mapDevId,
                    signName:vm.markObj.signName,
                    mapId:vm.markObj.mapId,
                    mapName:vm.markObj.mapName
                }),
                // async: false,
                contentType: "application/json",
                // dataType: 'json',
                success: function (r) {
                    layer.close(index);
                    if (r.code === 0) {
                        console.log(r);
                        vm.markVisible = false;
                        vm.getMarksList();
                        // $('#zoom-marker-img-alt'+vm.imgurl.split('.')[0]+'-'+vm.imgCNum).zoomMarker_RemoveMarker(vm.nowMarkId);
                    } else {
                        layer.alert(r.msg);
                    }
                },
                error: function () {
                    layer.msg("网络故障");
                }
            });
        },
        init:function (item) {
            var that = this;
            item.on("zoom_marker_mouse_click", function(event, position) {
                // console.log("Mouse click on: " + JSON.stringify(position));
                vm.addMark(position);
                var marker = item.zoomMarker_AddMarker({
                    mapDevId:vm.addMapDevId,
                    src: "../../statics/img/marker.svg",
                    x: position.x,
                    y: position.y,
                    size: 40,
                    mapId:vm.nowMapId
                });
                // 手动配置dialog
                marker.param.dialog = {
                    value: "<h4>暂无名称</h4>",
                    offsetX: 20,
                    style: {
                        "border-color": "#86df5f"
                    }
                };
                // 画线
                const context = item.zoomMarker_Canvas();
                if(context !== null) {
                    context.strokeStyle = 'red';
                    context.moveTo(position.x, position.y);
                    context.lineTo(100, 100);
                    context.stroke();
                }
            });

            item.on("zoom_marker_click", function(event, marker) {
                console.log(JSON.stringify(marker));
                vm.nowMarkId = marker.id;
                vm.mapDevId = marker.param.mapDevId;
                vm.markObj.mapId = marker.param.mapId;
                vm.markObj.signName = marker.param.signName;
                vm.markObj.mapName = marker.param.mapName;
                vm.markVisible = true;
            });
            item.on("zoom_marker_move_end", function (event, marker) {
                console.log(marker);
                vm.upMark({x:marker.param.x,y:marker.param.y,id:marker.param.mapDevId});
            });

            item.on("zoom_marker_img_loaded", function(event, size) {
                // console.log("image has been loaded with size: " + JSON.stringify(size));
            });
        },
        initImg:function (markers) {
            $('#zoom-marker-img-alt'+vm.imgCNum).zoomMarker({
                src: "../../statics/img/1595301032060.jpg",
                rate: 0.1,
                width: 1300,
                max: 3000,
                markers: markers
            });
        },
        // 获取图片
        getUrl:function (val) {
            vm.mapList.forEach(function (item) {
                if(item.mapId == val){
                    vm.markObj.mapName = item.mapName;
                }
            })
        },
    },
    created:function () {
        this.getProList();
        this.getMapList();
        // this.getMarksList();
    },
    mounted:function () {
        this.getMarksList();
    }
});