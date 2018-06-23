<#import "spring.ftl" as spring />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="${request.contextPath }/ftp/down.do" method="POST" enctype="multipart/form-data">
        文件：<input type="text" name="fileName"/>
             <input type="submit"/>
    </form>
</body>
</html>