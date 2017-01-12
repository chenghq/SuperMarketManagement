var loading = false; // 状态标记
var selected_type = '';
var pageNumber = 1;
var load_end = false;

var selected_products = {};

window.onload = function() {
	// 点击li的时候显示不同的样式
	var main_li = document.getElementById('main_list');
	obj_li = main_li.getElementsByTagName("li"); // 获取li对象数组
	for (key in obj_li) {
		obj_li[key].onclick = function() { // 为每个li注册单击事件
			for (one in obj_li) {
				obj_li[one].className = "";
			}
			this.className = "active";
		}
	}
	select_product('');
	//JS初始化滚动加载插件
	$(document.body).infinite(100);
	
	// 去结算
	document.getElementById("settlement").onclick = function () {
		console.log(selected_products)
	}
};
$(document.body).infinite().on("infinite", function() {
	if (loading)
		return;
	loading = true;
	$.ajax({
		type : "get",
		url : "products/load.do",
		contentType: "text/html; charset=utf-8", 
		data : "type=" + selected_type + "&pageNumber=" + pageNumber,
		success : function(result) {
			var html = "";
			var models = eval("(" + result + ")");
	        if (models['error']) {
	        	if (!load_end) {
		        	$.alert(models['error']);
		        	load_end = true;
	        	}
	        } else {
		        for (var i in models) {
		        	html = html + "<div class=\"prt-lt\">" +
				        	"<div class=\"lt-lt\">" +
				        	"<img src=\"images/part1.jpg\" />" +
				        	"</div>" +
							"<div class=\"lt-ct\">" +
							"<p>" + models[i].name +
							"</p>" +
							"<p class=\"pr\">¥<em class=\"price\">" + models[i].price + "</em>" +
							"</p>" +
							"</div>" +
							"<div class=\"lt-rt\" id=\"" + models[i].number + "\">" +
							"<input type=\"button\" value=\"-\">" +
							"<input type=\"text\" class=\"result\" value=\"0\">" +
							"<input type=\"button\" value=\"+\">" +
							"</div></div>";
		        }
		    	shoppingCart();
		        pageNumber = pageNumber + 1;
	        }
			loading = false;
			$("#Content-Main").append(html);
		},
		error : function(er) {
			$.alert(er);
			loading = false;
		}
	});
});

function select_product(type) {
	selected_products = {};
	document.getElementById('totalNum').innerHTML = "0";
	document.getElementById('totalPrice').innerHTML = "0";
	pageNumber = 1;
	load_end = false;
	selected_type = type;
	$.ajax({
		type : "get",
		url : "products/select.do",
		contentType: "text/html; charset=utf-8", 
		data : "type=" + type + "&pageNumber=" + pageNumber,
		success : function(result) {
			var models = eval("(" + result + ")");
	        if (models['error']) {
	        	if (!load_end) {
		        	$.alert(models['error']);
		        	load_end = true;
	        	}
	        } else {
	        	var ul = document.getElementById("Content-Main");
	        	ul.innerHTML = "";
		        for (var i in models) {
		        	$("#Content-Main").append("<div class=\"prt-lt\">" +
		        	"<div class=\"lt-lt\">" +
		        	"<img src=\"images/part1.jpg\" />" +
		        	"</div>" +
					"<div class=\"lt-ct\">" +
					"<p>" + models[i].name +
					"</p>" +
					"<p class=\"pr\">¥<em class=\"price\">" + models[i].price + "</em>" +
					"</p>" +
					"</div>" +
					"<div class=\"lt-rt\" id=\"" + models[i].number + "\">" +
					"<input type=\"button\" class=\"minus\" value=\"-\">" +
					"<input type=\"text\" class=\"result\" value=\"0\">" +
					"<input type=\"button\" class=\"add\" value=\"+\">" +
					"</div></div>");
		        	
		        }
		    	shoppingCart();
		        pageNumber = pageNumber + 1;
	        }
		},
		error : function(er) {
			$.alert(er);
		}
	});
}

/**
 * 购物车功能的实现
 * */
function shoppingCart() {
	var oUl = document.getElementById('Content-Main');
	var totalNumber = document.getElementById('totalNum');
	var totalPrice = document.getElementById('totalPrice');
	var div_class = oUl.getElementsByTagName('div');
	
	for (var i = 0; i < div_class.length; i++) {
		if (div_class[i].className == "prt-lt") {
			var child_div = div_class[i].getElementsByTagName('div');
			for (var j = 0; j < child_div.length; j++) {
				if (child_div[j].className == "lt-rt") {
					price(div_class[i], child_div[j].id);
				}
			}
			
		}
	}

	function price(oLi, id) {

		var allBtn = oLi.getElementsByTagName('input');
		var oEm = oLi.getElementsByTagName('em')[0];
//		var oSpan = oLi.getElementsByTagName('span')[0];
		var oResult, removeBtn, addBtn;
		
		for (var i = 0; i < allBtn.length; i++) {
			if (allBtn[i].className == "minus") {
				removeBtn = allBtn[i];
			} else if (allBtn[i].className == "add") {
				addBtn = allBtn[i];
			} else if (allBtn[i].className == "result") {
				oResult = allBtn[i]
			}
		}
		// 移除
		removeBtn.onclick = function() {
			var num = Number(oResult.value);
			var price = parseFloat(oEm.innerHTML);
			var numbers = Number(totalNumber.innerHTML);
			var prices = parseFloat(totalPrice.innerHTML);
			num = num - 1;
			if (num == 0) {
				// 如果该商品数量为0, 那么就得把它的价格从价格表中清除
				numbers = numbers - 1;
				oResult.value = num;
//				oSpan.innerHTML = num * price;
				totalNumber.innerHTML = numbers;
				totalPrice.innerHTML = prices - price;
				selected_products[id]--;
			} else if (num < 0) {
				num = 0;

			}

		};
		// 加入购物车
		addBtn.onclick = function() {
			var num = Number(oResult.value);
			var price = parseFloat(oEm.innerHTML);
			var numbers = Number(totalNumber.innerHTML);
			var prices = parseFloat(totalPrice.innerHTML);

			num = num + 1;
			numbers = numbers + 1;
			if (selected_products[id]) {
				selected_products[id]++;
			} else {
				selected_products[id] = 1;
			}
			
			oResult.value = num;
//			oSpan.innerHTML = num * price + '元';
			totalNumber.innerHTML = numbers;
			totalPrice.innerHTML = prices + price;
		}
		
	}
}
