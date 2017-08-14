<!DOCTYPE html>
<html>
<head>
    <title>客户关系管理系统</title>
    <script src="${ctx}/highcharts4/jquery-1.8.3.min.js" type="text/javascript"></script>
    <script src="${ctx}/highcharts4/js/highcharts.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx}/js/report.js"></script>
    <title>客户关系管理系统</title>
    <script>
        $(function () {
        	//findCustomerFw();
        
            //客户服务分析
            var data = [];
            $.ajax({
            	url: 'khfwfx',
            	dataType: 'json',
            	data: {},
            	async: false,
            	success: function(resp) {
            		for (var i = 0; i < resp.length; i++) {
            			var serveType = resp[i].serveType;
            			var amount = resp[i].amount;
            			var result = {name: serveType, y: amount};
            			if (i == 0) {
            				result.sliced = true,
                			result.selected = true
            			}
            			data.push(result);
            		}
            		console.log(JSON.stringify(data));	
            	},
            	error: function() {},
            	before: function() {}
            })
            
            new Highcharts.Chart({
		        chart: {
		            renderTo: 'container',
		            plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false,
		            type:'pie'
		        },
		        title: {
		            text: '客戶服务分析'
		        },
		        tooltip: {
		            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		        },
		        plotOptions: {
		            pie: {
		                allowPointSelect: true,
		                cursor: 'pointer',
		                dataLabels: {
		                    enabled: true,
		                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
		                    style: {
		                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
		                    }
		                }
		            }
		        },
		        series: [{
		            name: 'Brands',
		            colorByPoint: true,
		            data: data
		        }]
		    });
        });
    </script>
</head>
<body style="margin: 1px">
 <div id="container" style="min-width: 800px;height: 400px"></div>
</body>
</html>