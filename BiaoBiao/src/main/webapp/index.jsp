<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="x-ua-compatible" content="IE=8;IE=9;IE=10" />
    <link href="${pageContext.request.contextPath}/import/bootstrap-3.3.7/css/bootstrap.css" rel="stylesheet" type="text/css" />
    <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js" type="text/javascript"  language="javascript"></script>
    <title>Wellcom to Biao Biao</title>
</head>

<body>
<form  id="form1" name="form1" method="post" action="">
    <br class="container-fluid" id="container">
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <div class="row">
        <div class="col-xs-6 col-sm-4"></div>
        <div class="col-xs-6 col-sm-4">
            <div class="form-group">
                <label for="username">UserCode</label>
                <input type="text" name="username" class="form-control" id="username" placeholder="UserCode">
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" name="password" class="form-control" id="password" placeholder="Password">
            </div>
            <button type="button" onclick="sub()" class="btn btn-default">Submit</button>
        </div>
        <div class="col-xs-6 col-sm-4"></div>
    </div>
</form>
</body>
<script language="JavaScript">
    $.fn.serializeObject = function()
    {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function() {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };

    function sub() {
        debugger;
        var json1 = $('#form1').serializeObject();
        $.ajax({
            type: "POST",
            url: "/biaobiao/user/checkUser.json",
            dataType: "json",
            data: json1,
            traditional: true,
            xhrFields: {
                withCredentials: true //session id 保持不变
            },
            success: function (tt) {
                alert("尊敬的" + tt.data.name + ",欢迎登陆。");
                window.open("http://www.baidu.com");
                window.close();
            },
            error: function (data) {
                alert("登录失败," + data.responseJSON.message);
            }
        });
    }
</script>
</html>
