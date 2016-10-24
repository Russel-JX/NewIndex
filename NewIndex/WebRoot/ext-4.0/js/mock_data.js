/*$(document).ready(function(){*/
	 Ext.onReady(function () {
var mockdata = {
					"items": [
						        {
						            "password": "11",
						            "realname": "韩雅婷",
						            "userid": 2,
						            "username": "hyt",
						            "usertype": 1,
						            "zbSort": 2
						        },
						        {
						            "password": "11",
						            "realname": "超级管理员",
						            "userid": 1,
						            "username": "admin",
						            "usertype": 1,
						            "zbSort": 5
						        }
						    ],
						    "total": 0
				};
			$.mockjaxSettings.contentType = "application/json";
			$.mockjax({
				url:"http://localhost:8888/NewIndex/*",
				responseTime:200,
				responseText:mockdata
			});

/*Mock.mock("http://localhost:8888/NewIndex/xx",
	{
	    "items": [
	        {
	            "password": "11",
	            "realname": "韩雅婷",
	            "userid": 2,
	            "username": "hyt",
	            "usertype": 1,
	            "zbSort": 2
	        },
	        {
	            "password": "11",
	            "realname": "超级管理员",
	            "userid": 1,
	            "username": "admin",
	            "usertype": 1,
	            "zbSort": 5
	        }
	    ],
	    "total": 0
	}
);*/
});