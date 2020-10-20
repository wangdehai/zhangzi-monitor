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
    // setInterval(function(){
    //     vm.getWeater();
    //     vm.getTable();
    // },15000);
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
        to:function(name,id){
            $.ajax({
                url: '../../sys/syspark/swapParkId',
                type: 'get',
                data: {
                	parkId:id
				},
                contentType: "application/json",
                // dataType: 'json',
                success: function (r) {
                    if (r.code === 0) {
                        window.location.href='./'+name+'.html?return=1';
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
	created:function(){
		this.getDate();
		this.getTime();
		var that = this;
		setInterval(function(){
			that.getTime()
		},900);
		
	},
	mounted:function(){
		// this.init();
	}
})