<!DOCTYPE html>
<html>
  <head>
    <title>MyHtml.html</title>
    <meta name="keywords" content="keyword1,keyword2,keyword3">
    <meta name="description" content="this is my page">
    <meta name="content-type" content="text/html; charset=UTF-8">
    <script src="${request.contextPath}/js/jquery-3.3.1.min.js"></script>
    <script src="${request.contextPath}/js/jsencrypt.js?v=123"></script>
    <script src="${request.contextPath}/js/RsaJs.js?v=321123"></script>

    <script type="text/javascript">
        $(function() {
            $('#submit').click(function() {
                var data = $('#msg').val();
                // 公钥
                var publickey = $('#publickey').val();

                // 使用jsencrypt库加密前端参数
                var jsencrypt = new JSEncrypt();
                jsencrypt.setPublicKey(publickey);
                // 这里调用长文本的加密方法
                var ecodeMsg = jsencrypt.encryptLong(data);
                $('#ecodeMsg').val(ecodeMsg);

            });
        });

    </script>
  </head>
  
  <body>
  需要加密的内容:</br><textarea id="msg" name="msg" rows="10" cols="60"></textarea></br>
  公钥:</br><textarea id="publickey" rows="10" cols="60"></textarea></br>
  密文:</br><textarea id="ecodeMsg" rows="10" cols="60"></textarea>
  <br/>
  <br/>

  <input id="submit" type="button" value="加密" />
  </body>
  </body>
</html>
