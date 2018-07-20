<%@ page contentType="text/html;charset=UTF-8" %>
<%--<%@ include file="/WEB-INF/views/include/taglib.jsp"%>--%>
<%@include file="/WEB-INF/jsp/admin/include/tags.jsp" %>

<!DOCTYPE html> 
 <head>
 <style type="text/css">
      body{ text-align:center;padding:0px;margin:0px;} 
      .div{ margin:0 auto; width:800px; height:100%; }
 </style>
 <title>嵌入PDF浏览</title>
 <script type="text/javascript" src="${ctxStatic }/pdfobject/pdfobject.js"></script>
 <script type="text/javascript" src="${ctxStatic }/js/jquery.media.js"></script>
<script>
window.onload = function (){
     // chrome 
      var success = new PDFObject({ url: "${book.bookUrl}" }).embed();
      if(!success){
           var opts = {
              width:800,
              height:$(document).height(),
              autoplay:true
         };  
          $('a.media').media(opts);
      }
  };

</script>
 </head>
<body>
<div class="div">
     <a style="display:none;" class="media" href="${pdfUrl}"></a>
</div>
 </body>
</html>