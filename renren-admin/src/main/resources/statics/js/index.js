//生成菜单
var menuItem = Vue.extend({
    name: 'menu-item',
    props:{item:{}},
    template:[
        '<li data-name="home" class="layui-nav-item">',
        '<a href="javascript:;" :lay-tips="item.name" lay-direction="2">',
        '<i :class="item.icon"></i>',
        '<cite>{{item.name}}</cite>',
        '<span class="layui-nav-more"></span>',
        '</a>',
        '<dl class="layui-nav-child">',
        '<dd v-for="list in item.list" :data-name="list.name" class="">',
        '<a :lay-href="list.url">{{ list.name }}</a>',
        '</dd>',
        '</dl>',
        '</li>',
    ].join('')

});

var menuItem1 = Vue.extend({
    name: 'menu-item',
    props:{item:{}},
    template:[
        '<cite>{{item.name}}</cite>',
    ].join('')

});


//注册菜单组件
Vue.component('menuItem',menuItem);

$(function () {
    $('.passwordLayer').click(function () {
        vm.updatePassword();
    })
    $('.baseInfo').click(function () {
        vm.baseInfo();
    })
})

window.onload=function () {
    // $('cite').css('display','inline-block');
    // console.log($('cite'));
}

//iframe自适应
$(window).on('resize', function() {
    var $content = $('.content');
    $content.height($(this).height() - 120);
    $content.find('iframe').each(function() {
        $(this).height($content.height());
    });

}).resize();



var vm = new Vue({
    el:'#LAY_app',
    data:{
        user:{},
        menuList:{},
        main:"main.html",
        password:'',
        newPassword:'',
        navTitle:"控制台",
        informNum:15,
        erroMsg4:'',
        hightTem:'',
        lowTem:'',
        parkId:''
    },
    methods: {
        // 参数设置
        setCanshu:function () {
            $.ajax({
                url: 'sys/dict/selectTem',
                type: 'get',
                data: '',
                // contentType: "application/json",
                dataType: 'json',
                success:function (r) {
                    if(r.code === 0){
                        console.log(r);
                        vm.hightTem = r.tem;
                        vm.lowTem = r.lowTem;
                        parseInt()
                        layer.open({
                            type: 1,
                            skin: 'openClass',
                            title: false,
                            area: ['360px', '200px'],
                            shadeClose: true,
                            content: $("#setParameter"),
                            btn: ['确定','取消'],
                            btn1: function (index) {
                                $.ajax({
                                    url: 'sys/dict/updateTem',
                                    type: 'get',
                                    data: {
                                        tem:parseInt(vm.hightTem),
                                        lowTem:parseInt(vm.lowTem)
                                    },
                                    // contentType: "application/json",
                                    dataType: 'json',
                                    success:function (r) {
                                        if(r.code === 0){
                                            console.log(r);
                                            layer.msg("操作成功");
                                            layer.close(index);

                                        }else {
                                            layer.alert(r.msg);
                                        }
                                    },
                                    error:function () {
                                        layer.msg("网络故障");
                                    }

                                })
                            }
                        });

                    }else {
                        layer.alert(r.msg);
                    }
                },
                error:function () {
                    layer.msg("网络故障");
                }

            })

        },
        getMenuList: function (event) {
            $.getJSON("sys/menu/nav?_"+$.now(), function(r){
                vm.menuList = r.menuList;
                console.log(vm.menuList);

                // new_element=document.createElement('scrit');
                // new_element.setAttribute('type','text/javascript');
                // new_element.setAttribute('src','statics/kuajing/js/layui.js');
                // document.body.appendChild(new_element);
            });
        },
        getUser: function(){
            $.getJSON("sys/user/info?_"+$.now(), function(r){
                vm.user = r.user;
            });
        },
        updatePassword: function(){
            layer.open({
                type: 1,
                skin: 'openClass',
                title: false,
                area: ['460px', '300px'],
                shadeClose: true,
                content: $("#passwordLayer"),
                btn: ['修改','取消'],
                btn1: function (index) {
                    // vm.chanPassword();
                    // console.log(vm.erroMsg4);
                    if(vm.erroMsg4 != ''){
                        layer.msg('请正确设置新密码')
                    }else {
                        var data = "password="+vm.password+"&newPassword="+vm.newPassword;
                        $.ajax({
                            type: "POST",
                            url: "sys/user/password",
                            data: data,
                            dataType: "json",
                            success: function(result){
                                if(result.code == 0){
                                    layer.close(index);
                                    layer.alert('修改成功', function(index){
                                        location.reload();
                                    });
                                }else{
                                    layer.alert(result.msg);
                                }
                            }
                        });
                    }

                }
            });
        },
        baseInfo: function(){
            layer.open({
                type: 1,
                skin: 'openClass',
                title: false,
                area: ['400px', '430px'],
                shadeClose: true,
                content: $("#baseInfo"),
                btn: ['保存','取消'],
                btn1: function (index) {
                    $.ajax({
                        type: "POST",
                        url: "sys/user/updateUser",
                        data: JSON.stringify(vm.user),
                        contentType: "application/json",
                        success: function(result){
                            if(result.code == 0){
                                layer.close(index);
                                layer.alert('保存成功');
                            }else{
                                layer.alert(result.msg);
                            }
                        }
                    });
                }
            });
        },
        donate: function () {
            layer.open({
                type: 2,
                title: false,
                area: ['806px', '467px'],
                closeBtn: 1,
                shadeClose: false,
                content: ['http://cdn.renren.io/donate.jpg', 'no']
            });
        },
        chanPassword:function () {
            var reg = /^(?![0-9\x21-\x2f\x3a-\x40\x5b-\x60\x7B-\x7F]+$)(?![0-9]+$)(?![0-90-9A-Za-z]+$)(?![\x21-\x2f\x3a-\x40\x5b-\x60\x7B-\x7F]+$)(?![A-Za-z]+$)(?![a-zA-Z\x21-\x2f\x3a-\x40\x5b-\x60\x7B-\x7F]+$)[0-9A-Za-z\x21-\x2f\x3a-\x40\x5b-\x60\x7B-\x7F]{8,}$/;
            console.log(vm.newPassword);
            console.log(reg.test(vm.newPassword));
            // console.log(reg.match(vm.user.password));
            if(reg.test(vm.newPassword)){
                var reg1 = /^[\x21-\x2f\x3a-\x40\x5b-\x60\x7B-\x7F]/;
                if(reg1.test(vm.newPassword)){
                    // console.log(111111);
                    vm.erroMsg4 = '密码不能以字符开头'
                }else {
                    vm.erroMsg4 = ''
                }

            }else {
                vm.erroMsg4 = '密码至少8位,要求必须字母、数字加英文符号(不包含空格)';
            }
        },
        // 同步
        synchronize:function () {
            var index = layer.load(2);
            $.ajax({
                type: "POST",
                url: "monitor/project/synchronize",
                data: '',
                contentType: "application/json",
                success: function(result){
                    layer.close(index);
                    if(result.code == 0){
                        layer.alert('同步成功');

                    }else{
                        layer.alert(result.msg);
                    }
                },
                error:function () {
                    layer.close(index);
                    layer.msg("网络故障");
                }
            });
        },
        toFullPage:function () {
            $.ajax({
                url: 'sys/syspark/getParkId',
                type: 'get',
                data: JSON.stringify(),
                contentType: "application/json",
                // dataType: 'json',
                success: function (r) {
                    vm.parkId = r;
                    if(r == 2){
                        window.open('./modules/fullPage/index2.html')
                    }else if(r == 3){
                        window.open('./modules/fullPage/index3.html')
                    }
                },
                error: function () {
                    layer.msg("网络故障");
                }
            });
        }
    },
    created: function(){
        this.getMenuList();
        this.getUser();
        setInterval(function () {
            vm.getinformNum();
        },1800000)
    },
    updated: function(){
        var el = $('#LAY-system-side-menu>li');
        el.css('display','none');
        for(var i = 0;i<vm.menuList.length;i++){
            el.eq(i).css('display','block');
        }
        //路由
        // var router = new Router();
        // routerList(router, vm.menuList);
        // router.start();
        // console.log(vm.main);
        // if(vm.main == 'modules/sys/inform.html'){
        // 	$('.layui-badge-dot1').css('display','none');
        // }
    }
});



function routerList(router, menuList){
    console.log("1111111111111111111111111");
    for(var key in menuList){
        var menu = menuList[key];
        if(menu.type == 0){
            routerList(router, menu.list);
        }else if(menu.type == 1){
            router.add('#'+menu.url, function() {
                var url = window.location.hash;

                //替换iframe的url
                vm.main = url.replace('#', '');

                //导航菜单展开
                $(".treeview-menu li").removeClass("active");
                $("a[href='"+url+"']").parents("li").addClass("active");

                vm.navTitle = $("a[href='"+url+"']").text();
            });
        }
    }
}
