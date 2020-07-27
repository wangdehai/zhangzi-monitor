function siwang(d) {
	var dom = document.getElementById("siwang");;
	var myChart = echarts.init(dom);
	var app = {};
	option = null;

	var ydata = [];
	var color = ["#289eff", "#fbcd13", "#00d05d", "#ef974f", "#28ffca","#6528ff"]
	var xdata = [];
	d.forEach(function(item) {
		ydata.push({
			name: item.type,
			value: item.num
		})
		xdata.push(item.type)
	})

	option = {
		color: color,
		legend: {
			//	    		show:false,
			// orient: "vartical",
			y: 'top',
			top:20,
			// bottom: "20",
			left: "center",
			data: xdata,
			itemWidth: 16,
			itemHeight: 8,
			itemGap: 16,
			textStyle: {
				color: 'rgba(0,0,0,.8)'
			},
			formatter: function(name) {
				return '' + name
			}
		},
		series: [{
			type: 'pie',
			radius: ["40%", "75%"],
			center: ["52%", "55%"],
			roseType: 'radius',
			avoidLabelOverlap: false,
			label: {
				normal: {
					show: false,
					color: 'rgba(0,0,0,.8)'
				},
				emphasis: {
					show: true,
				}
			},
			labelLine: {
				show: true,
				lineStyle: {
					color: '#05d6f6'
				}
			},
			data: ydata
		}]
	};
	myChart.setOption(option);

	if(option && typeof option === "object") {
		myChart.setOption(option, true);
	}
}

