<!DOCTYPE html>
<html>
<head>
    <title>客户关系管理系统</title>
    <script src="${ctx}/highcharts4/jquery-1.8.3.min.js" type="text/javascript"></script>
    <script src="${ctx}/highcharts4/js/highcharts.js" type="text/javascript"></script>
    <script src="${ctx}/js/report.js" type="text/javascript"></script>
    <title>客户关系管理系统</title>
   <script>
       $(function () {
          // 客户关系构成
          findCustomerGc();
          /*
          var categories =  [];
		  var data = [];
		  $.ajax({
		  	url: 'khgcfx',
		  	dataType: 'json',
		  	data: {},
		  	async: false,
		  	success: function(result) {
		  		for (var i = 0; i < result.length; i++) {
		  			var gc = result[i];
		  			categories.push(gc.level);
		  			data.push(gc.amount);
		  		}
		  	}
		  });
		    
          var chart = new Highcharts.Chart({
		        chart: {
		            renderTo: 'container',
		            type: 'column'
		        }, 
				 title: {
		            text: '客户构成分析'
		        },
		        xAxis: {
		            categories: categories,
		            crosshair: true
		        },
		        yAxis: {
		            min: 0,
		            title: {
		                text: '客户数量'
		            }
		        },
		        tooltip: {
		            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
		            pointFormat: '<tr><td style="color:{series.color};padding:0">数量: </td>' +
		                '<td style="padding:0"><b>{point.y:.1f} 个</b></td></tr>',
		            footerFormat: '</table>',
		            shared: true,
		            useHTML: true
		        },
		        plotOptions: {
		            column: {
		                pointPadding: 0.2,
		                borderWidth: 0
		            }
		        },
		        series: [{
		            name: '客户',
		            data: data
		
		        }]
		    });*/
       });
   </script>
</head>
<body style="margin: 1px">
<div id="container" style="min-width: 800px;height: 400px"></div>
</body>
</html>