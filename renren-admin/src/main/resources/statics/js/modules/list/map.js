


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
        linkDevIdI:''
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
            let result = []
            if(!Array.isArray(data)) {
                return result
            }
            data.forEach(item => {
                delete item.children;
        });
            let map = {};
            data.forEach(item => {
                map[item.projectId] = item;
        });
            data.forEach(item => {
                let parent = map[item.parentId];
            if(parent) {
                (parent.children || (parent.children = [])).push(item);
            } else {
                result.push(item);
            }
        });
            return result;
        },
        // 选择项目
        changePro:function(){
            vm.getList(vm.projectId[vm.projectId.length-1])
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
                data: {
                    projectId:vm.projectId[vm.projectId.length-1],
                    regionId:vm.regionId
                },
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
        // 获取未关联设备列表
        getDeviceList:function () {
            $.ajax({
                url: '../../monitor/device/getDevList',
                type: 'get',
                data: {
                    mapId:this.nowMapId,
                    projectId:this.nowProId,
                    regionId:this.nowReId
                },
                contentType: "application/json",
                // dataType: 'json',
                success: function (r) {
                    if (r.code === 0) {
                        vm.devNoList = r.devList;
                    } else {
                        layer.alert(r.msg);
                    }
                },
                error: function () {
                    layer.msg("网络故障");
                }
            });
        },
        // 检测区域是否关联图片
        checkMap:function () {
            // imgVisible = true;regionVisible = false
            var index = layer.load(2);
            $.ajax({
                url: '../../monitor/map/checkMap',
                type: 'get',
                data: {
                    regionId:vm.regionIdS
                },
                // async: false,
                // contentType: "application/json",
                dataType: 'json',
                success: function (r) {
                    layer.close(index);
                    if (r.code === 0) {
                        console.log(r);
                        // vm.imgRegion = false;
                        // vm.mapId = '';
                        // vm.showimg();
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
        // 上传图片
        upImg:function () {
            this.$refs.upload.submit();
        },
        upOk:function (response, file, fileList) {
            console.log(response);
            console.log(file);
            console.log(fileList);
            if(response.code == 0){
                vm.fileList = [];
                vm.imgVisible = false;
                vm.imgurl = response.src;
                vm.imgRegion = true;
                vm.tagNumber = 1;
                vm.upshowimg();
                // this.getImage();
            }else{
                this.$message.error(response.msg)
            }
        },
        // 上传展示图片信息
        upshowimg:function () {
            $.ajax({
                url: '../../monitor/map/list',
                type: 'get',
                data: {
                    projectId:vm.projectId[vm.projectId.length-1],
                    regionId:vm.regionId
                },
                // contentType: "application/json",
                dataType: 'json',
                success: function (r) {
                    if (r.code === 0) {
                        vm.mapList = r.list;
                        vm.mapList.forEach(function (item) {
                            if(item.mapUrl == vm.imgurl){
                                vm.nowMapId = item.mapId;
                                vm.nowProId = item.projectId;
                                vm.nowReId = item.regionId;
                            }
                        })
                        console.log(vm.mapList);
                        console.log(vm.imgurl);

                        console.log(vm.nowMapId)
                        console.log(vm.nowProId)
                        console.log(vm.nowReId)
                    } else {
                        layer.alert(r.msg);
                    }
                },
                error: function () {
                    layer.msg("网络故障");
                }
            });

            $('#zoom-marker-div').empty();
            vm.imgCNum++;
            var $img = $('<img class="zoom-marker-img" id="zoom-marker-img-alt'+vm.imgurl.split('.')[0]+'-'+vm.imgCNum+'" alt="zoom-marker-img-alt" name="zoom-marker-img-alt" draggable="false" />');
            $('#zoom-marker-div').append($img);
            vm.init($('#zoom-marker-img-alt'+vm.imgurl.split('.')[0]+'-'+vm.imgCNum))
            vm.initImg(vm.imgurl,[]);
            vm.getDeviceList();
        },
        // 获取图片
        getUrl:function (val) {
            vm.imgRegion = true;
            vm.imgurl = val;
            vm.tagNumber = 1;
            vm.mapList.forEach(function (item) {
                if(item.mapUrl == vm.imgurl){
                    vm.nowMapId = item.mapId;
                    vm.nowProId = item.projectId;
                    vm.nowReId = item.regionId;
                }
            })
            vm.showimg();
        },
        // 展示图片信息
        showimg:function () {
            // var mapId = '';
            // vm.mapList.forEach(function (item) {
            //     if(item.mapUrl == vm.imgurl){
            //         mapId = item.mapId
            //     }
            // })
            $('#zoom-marker-div').empty();
            vm.imgCNum++;
            var $img = $('<img class="zoom-marker-img" id="zoom-marker-img-alt'+vm.imgurl.split('.')[0]+'-'+vm.imgCNum+'" alt="zoom-marker-img-alt" name="zoom-marker-img-alt" draggable="false" />');
            $('#zoom-marker-div').append($img);
            $.ajax({
                url: '../../monitor/mapdev/list',
                type: 'get',
                data: {
                    mapId:vm.nowMapId
                },
                contentType: "application/json",
                // dataType: 'json',
                success: function (r) {
                    if (r.code === 0) {
                        console.log(r);
                        r.list.forEach(function (item) {
                            item.src = "../../statics/img/marker.svg";
                            item.size = 30;
                            var str = item.deviceName ? item.deviceName : '暂无关联设备';
                            item.dialog = {
                                value: "<h4>"+str+"</h4>",
                                offsetX: 20,
                                style: {
                                    "border-color": "#86df5f"
                                }
                            };
                        })
                        vm.init($('#zoom-marker-img-alt'+vm.imgurl.split('.')[0]+'-'+vm.imgCNum));
                        vm.initImg(vm.imgurl,r.list);
                        vm.getDeviceList();
                    } else {
                        layer.alert(r.msg);
                    }
                },
                error: function () {
                    layer.msg("网络故障");
                }
            });
            this.$nextTick(function(){

            })
        },
        // 获取当前图片的标记点
        getMarksList:function () {
            // var mapId = '';
            // vm.mapList.forEach(function (item) {
            //     if(item.mapUrl == vm.imgurl){
            //         mapId = item.mapId
            //     }
            // })
            $.ajax({
                url: '../../monitor/mapdev/list',
                type: 'get',
                data: {
                    mapId:vm.nowMapId
                },
                contentType: "application/json",
                // dataType: 'json',
                success: function (r) {
                    if (r.code === 0) {
                        console.log(r);
                        vm.marksList = r.list;
                    } else {
                        layer.alert(r.msg);
                    }
                },
                error: function () {
                    layer.msg("网络故障");
                }
            });
        },
        // 新增标记点
        addMark:function (position) {
            var index = layer.load(2);
            $.ajax({
                url: '../../monitor/mapdev/add',
                type: 'post',
                data: JSON.stringify({
                    mapId:vm.nowMapId,
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
                    url: '../../monitor/mapdev/delete',
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
                            $('#zoom-marker-img-alt'+vm.imgurl.split('.')[0]+'-'+vm.imgCNum).zoomMarker_RemoveMarker(vm.nowMarkId);
                            vm.markVisible = false;
                            vm.getDeviceList();

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
                url: '../../monitor/mapdev/save',
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
                url: '../../monitor/mapdev/save',
                type: 'post',
                data: JSON.stringify({
                    mapDevId:vm.mapDevId,
                    devId:vm.linkDevId,
                    deviceName:vm.linkDevName
                }),
                // async: false,
                contentType: "application/json",
                // dataType: 'json',
                success: function (r) {
                    layer.close(index);
                    if (r.code === 0) {
                        console.log(r);
                        vm.markVisible = false;
                        vm.showimg();
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
        // 关联设备修改
        devIdChange:function () {
            vm.devNoList.forEach(function (item) {
                if(item.devId == vm.linkDevId){
                    vm.linkDevName = item.deviceName
                }
            })
        },
        init:function (item) {
            var that = this;
            // console.log($('#zoom-marker-img-alt'+vm.imgurl.split('.')[0]+'-'+vm.imgCNum));
            item.on("zoom_marker_mouse_click", function(event, position) {
                // console.log("Mouse click on: " + JSON.stringify(position));
                if(vm.devNoList.length != 0){
                    vm.addMark(position);
                    var marker = item.zoomMarker_AddMarker({
                        mapDevId:vm.addMapDevId,
                        src: "../../statics/img/marker.svg",
                        x: position.x,
                        y: position.y,
                        size: 30,
                        devId:null,
                        deviceName:null,
                        mapId:vm.nowMapId
                    });
                    // 手动配置dialog
                    marker.param.dialog = {
                        value: "<h4>未关联设备</h4>",
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
                }else {
                    that.$message({
                        message: '无可添加的设备',
                        type: 'warning'
                    });
                }


            });

            item.on("zoom_marker_click", function(event, marker) {
                console.log(JSON.stringify(marker));
                vm.nowMarkId = marker.id;
                vm.mapDevId = marker.param.mapDevId;
                vm.linkDevId = marker.param.devId;
                vm.linkDevIdI = marker.param.devId;
                vm.linkDevName = marker.param.deviceName;
                vm.markVisible = true;
                // $('#zoom-marker-img-alt'+vm.imgurl.split('.')[0]+'-'+vm.imgCNum).zoomMarker_RemoveMarker(marker.id);
            });
            item.on("zoom_marker_move_end", function (event, marker) {
                console.log(marker);
                vm.upMark({x:marker.param.x,y:marker.param.y,id:marker.param.mapDevId});
            });

            item.on("zoom_marker_img_loaded", function(event, size) {
                // console.log("image has been loaded with size: " + JSON.stringify(size));
            });
        },
        initImg:function (url,markers) {
            $('#zoom-marker-img-alt'+vm.imgurl.split('.')[0]+'-'+vm.imgCNum).zoomMarker({
                src: "../../statics/img/"+vm.imgurl,
                rate: 0.1,
                width: 1300,
                max: 3000,
                markers: markers
            });
        },
        delImg:function () {
            var index = layer.load(2);
            $.ajax({
                url: '../../monitor/map/delete',
                type: 'get',
                data: {
                    mapId:vm.nowMapId
                },
                // async: false,
                // contentType: "application/json",
                dataType: 'json',
                success: function (r) {
                    layer.close(index);
                    if (r.code === 0) {
                        console.log(r);
                        vm.imgRegion = false;
                        vm.mapId = '';
                        // vm.showimg();
                        // $('#zoom-marker-img-alt'+vm.imgurl.split('.')[0]+'-'+vm.imgCNum).zoomMarker_RemoveMarker(vm.nowMarkId);
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
    created:function () {
        this.getProList();
        this.getDeviceList();
        // this.getList();
    }
});