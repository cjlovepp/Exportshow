<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../../Exportshow/js/jquery.js"></script>
<script type="text/javascript" src="../../Exportshow/js/highcharts/highstock.js"></script>
<script type="text/javascript" src="../../Exportshow/js/highcharts/modules/no-data-to-display.js"></script>
<script type="text/javascript" src="../../Exportshow/js/highcharts/modules/exporting.js"></script>
<title>Insert title here</title>
</head>
<body>
	<div class="borderBox deepBgWrap mb10">
		<ul>
			<li>
				<a id="exportOrgUser" href="javascript:;" onclick="exportDate()">导出</a>
			</li>
		</ul>
	</div>
	<div id="content">
		<div class="statistics fl" id="statistics1">
	</div>
</div>
</body>
<script type="text/javascript">

$(function(){
	
	var num = 9;//饼图成员
	
	var param ={
			'num':num
		};
	
	$.ajax({
        url: 'getData.do',
        type: 'post',
        data: param,
        error: function()
        {
        	$('#statistics1').html("");
        },
        success: function(data)
        {
        	showActive1(data);
        }
    });
});

function showActive1(data){
	$('#statistics1').highcharts({
		chart: {
			plotBackgroundColor: null,
			plotBorderWidth: null,
			plotShadow: false
		},
		title: {
			text: '饼图事例'
		},
		credits: {
			enabled:false
		},
		exporting:{
			enabled:false
		},
		tooltip: {
			pointFormat: '{series.name}: <b>{point.y}</b>'
		},
		plotOptions: {
			pie: {
				allowPointSelect: true,
				cursor: 'pointer',
				dataLabels: {
					enabled: true,
					color: '#000000',
					connectorColor: '#000000',
					format: '<b>{point.name}</b>: {point.percentage:.0f}%'
				},
				showInLegend: true
			}
		},
		lang: {
            noData: "抱歉，未查询到相关记录。"
        },
        noData: {
            style: {
                fontWeight: 'bold',
                fontSize: '15px',
                color: '#303030'
            }
        },
		series: [{
			type: 'pie',
			name: 'num',
			data: data
		}]
	});
}

function exportDate(){
	var chart1 = $("#statistics1").highcharts();
	var svg = chart1.getSVG() ;
	var url_svg = "saveSvg.do";
	var url_export = "exportHighCharts.do?num=9";
	
	var param = {
			"svg":svg
	}
	
	$.ajax({
        url: url_svg,
        type: 'post',
        data: param,
        error: function()
        {
        	win.alert("导出失败！");
        },
        success: function(data)
        {
        	window.location.href = url_export;
        }
    });
	
}

</script>
</html>