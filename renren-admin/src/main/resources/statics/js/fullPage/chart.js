$.ajaxSetup({cache:false})
function siwang(d) {
	var dom = document.getElementById("siwang");;
	var myChart = echarts.init(dom);
	var app = {};
	option = null;

	var ydata = [];
	var color = ["#289eff", '#00fff9', "#fbcd13", "#00d05d", "#ef974f", "#28ffca","#6528ff"]
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
				color: 'rgba(255,255,255,.8)'
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
					color: 'rgba(255,255,255,.8)'
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

function wendu(d) {
    var dom = document.getElementById("wendu");;
    var myChart = echarts.init(dom);
    var app = {};
    option = null;

    option = {
        legend: {
            data: ['最高温度', '最低温度'],
            icon: 'roundRect',
            itemWidth: 16,
            itemHeight: 8,
            right: 10,
            textStyle: {
                fontSize: 12,
                color: 'rgb(255,255,255,0.8)'
            },
        },
        tooltip: {
            trigger: 'axis'
        },
        grid: {
            top: 30,
            left: 40,
            right: 10,
            bottom: 30,
            //	        containLabel: true
        },
        xAxis: {
            type: 'category',
            data: d.xData,
            axisLine: {
                lineStyle: {
                    color: "rgba(255,255,255,.1)"
                }
            },
            axisLabel: {
                show:true,
                color: 'rgba(255,255,255,.7)',
                fontSize:12
            },
            axisTick: {
                show: false
            }
        },
        yAxis: {
            type: 'value',
            splitLine: {
                lineStyle: {
                    color: 'rgba(255,255,255,.05)'
                }
            },
            axisLine: {
                show: false,
                lineStyle: {
                    color: "#fff"
                },
            },
            nameTextStyle: {
                color: "#fff"
            },
            splitArea: {
                show: false
            },
            axisTick: {
                show: true,
                lineStyle: {
                    color: '#289eff'
                }
            },
            axisLabel: {
                show:true,
                color: 'rgba(255,255,255,.7)',
                fontSize:12
            },
        },
        series: [{
            name: '最高温度',
            type: 'line',
            data: d.data[0],
            symbol: 'circle',
            symbolSize: 6,
            color: '#fbcd13',
            lineStyle: {
                width: 2,
                color: '#fbcd13',
            },
            itemStyle: {
                normal: {
                    color: '#fbcd13',
                    borderWidth: 1.5,
                    /*shadowColor: 'rgba(72,216,191, 0.3)',
                     shadowBlur: 100,*/
                    borderColor: "#fbcd13"
                }
            },
            smooth: true
        },
            {
                name: '最低温度',
                type: 'line',
                data: d.data[1],
                symbol: 'circle',
                symbolSize: 6,
                color: '#289eff',
                lineStyle: {
                    normal: {
                        width: 2,
                        color: '#289eff',
                        //	                    shadowColor: 'rgba(245,128,128, 0.5)',
                        //	                    shadowBlur: 10,
                        //	                    shadowOffsetY: 7
                    }
                },
                itemStyle: {
                    normal: {
                        color: '#289eff',
                        borderWidth: 1.5,
                        /*shadowColor: 'rgba(72,216,191, 0.3)',
                         shadowBlur: 100,*/
                        borderColor: "#289eff"
                    }
                },
                smooth: true
            }
        ]
    };

    if(option && typeof option === "object") {
        myChart.setOption(option, true);
    }
}
