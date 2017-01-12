var BaseUrl = "http://localhost:8080";
/**
 * 获取验证码
 */
function btn_getMask() {
	var iphone_number = $("#iphone_number");
	$.showLoading();
	$.showLoading("发送中...");
	$.ajax({
		type : "get",
		url : "getmask.do",
		data : "iphone_number=" + iphone_number.val(),
		success : function(msg) {
			if (msg == "true") {
				$.alert("发送成功")
			} else {
				$.alert("发送验证码失败")
			}
			$.hideLoading();
		},
		error : function(er) {
			$.alert(er.messge);
			$.hideLoading();
		}
	});
}

/**
 * 绑定 即用验证码登录
 */
function btn_matchMask() {
	var iphone_number = $("#iphone_number");
	var mask_number = $("#mask_number");

	window.location.href = BaseUrl + "/login.do?iphone_number="
			+ iphone_number.val() + "&mask_number=" + mask_number.val();
}