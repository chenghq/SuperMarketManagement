<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/1/16
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>订单</title>
    <jsp:include page="_head.html"></jsp:include>
    <style>
        div {
            margin: 10px;
        }

        .address {
            background-color: rgba(0, 0, 0, .6);
        }

        .discount {
            background-color: red;
        }

        .products {
            background-color: #009900;
        }

        /*备注相关信息*/
        .others {
            background-color: yellow;
        }

    </style>

</head>
<body>
<div class="address">
    地址信息
</div>
<div class="discount">优惠等</div>
<div class="products">
    <table width="100%">
        <c:forEach items="${product_list}" var="product" varStatus="vs">
            <tr>
                <td style="width: 40%;">${product.name}</td>
                <td style="width: 30%;">*${product.count}</td>
                <td style="width: 30%;">${product.totalPrice}</td>
            </tr>
        </c:forEach>
    </table>
</div>
<div class="others">
    <label>备注: </label>
    <input id="remarks" placeholder="请输入备注信息"/>
</div>
</body>
</html>
