<#import "spring.ftl" as spring />
<!DOCTYPE html>
<html>
  <head>
    <title>MyHtml.html</title>
	
    <meta name="keywords" content="keyword1,keyword2,keyword3">
    <meta name="description" content="this is my page">
    <meta name="content-type" content="text/html; charset=UTF-8">
    
    <!--<link rel="stylesheet" type="text/css" href="./styles.css">-->
  </head>
  
  <body>
    <img src="<@spring.url'/imge/${imgName}'/>" width="300px"/>
    <img src="${request.contextPath }/webStatic/pic/${imgName}" width="300px"/>
    <img src="${request.contextPath }/webStatic/pic/${imgName}" width="300px"/>
    <img src="${request.contextPath }/img/test1.png" width="300px"/>
    <img src="${request.contextPath }/img/test2.png" width="300px"/>
    <img src="${request.contextPath }/album1/test3.png" width="300px"/>
    <img src="${request.contextPath }/album2/test3.png" width="300px"/>
    <!--
    <img src="${request.contextPath }/img4/test4.png" width="300px"/>
    <img src="${request.contextPath }/img4/test5.png" width="300px"/>
    <img src="${request.contextPath }/img4/test5.png" width="300px"/>
    -->
  </body>
</html>
