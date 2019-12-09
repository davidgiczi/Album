<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Modify Image</title>

<style type="text/css">


table, th, td {
  border: 0px solid #D6DBDF;
  border-collapse: collapse;
  }

th, td {
  padding: 1px;
  text-align: center;
  color:#2C3E50;
}

button {
	
	background-color: white;
	color: #2C3E50;
}

.other {
 position: absolute;
 top: 205px;
 left: 590px;
 z-index: -1;
 }
 
 .frame {

 position: absolute;
 top: 100px;
 left:500px;
  z-index: -2;
  
}

.rotate90{

	 transform: rotate(90deg);
    -webkit-transform: rotate(90deg);
    -moz-transform: rotate(90deg);
    -ms-transform: rotate(90deg);
    
}

.rotate270{

	 transform: rotate(270deg);
    -webkit-transform: rotate(270deg);
    -moz-transform: rotate(270deg);
    -ms-transform: rotate(270deg);
    
}

.newframeposition {
 position: absolute;
 top: 200px;
 left:500px;
  z-index: -2;
}

.newotherposition {
 position: absolute;
 top: 300px;
 left: 590px;
 z-index: -1;
 }
 
 .panoramaframe {
 
 position: absolute;
 top: 100px;
 left:150px;
 z-index: -2;
 
 }

.biggerimage {

 position: absolute;
 top: 205px;
 left: 295px;
 z-index: -1;

}

.rotateframe90position {
 position: absolute;
 top: 190px;
 left: 700px;
 z-index: -1;
 }

.modify {
 position: absolute;
 top: 930px;
 left: 150px;
 }
 
 .cancel {
 position: absolute;
 top: 930px;
 left: 1700px;
 }
 
 textarea, input {
 
 text-align: center;

 }


</style>



</head>


<body style="background-color: lavender">



<img id="frame" src="images/frame.jpg" alt="" width="1000" height="800" class="frame">
<img id="other" src="data:image/jpg;base64,${modifyImage.codeString}" alt="" width="816" height="595" class="other">


<button onclick="send()" class="modify" >Módosítás</button>
<button onclick="back()" class="cancel">Mégse</button>

<form id="modiForm" method="POST" action="ModifyImage" accept-charset="UTF-8">

<table style="width:100%" >
  <tr>
  	
    <th>CÍM</th>
    <th>JEGYZET</th>
    <th>TÉMA</th>
    <th>LÁTHATÓ</th>
    <th>BEÁLLÍTÁS</th>
    <th>DÁTUM</th>
  
  </tr>
  
  
  <tr>

    <td><textarea  style="color:#2C3E50" name="title" cols="62" rows="4">${title}</textarea></td>
    
    <td><textarea style="color:#2C3E50"  name="comment" cols="62" rows="4">${modifyImage.comment}</textarea></td>
    
    
    <td> 
    
    	<select id="topics" onchange="selectTopic()" style="background-color: white;color: Gray">
    	
    	<option>Témák</option>
    	<c:forEach items="${topics}" var="topic">
    	<option value="${topic}">${topic}</option>
    	</c:forEach>
    	
    	</select>
   
    <input id="selected" style="color:#2C3E50" name="top" type="text" value="${modifyImage.topic}">
    
    </td>
   
    <c:if test="${modifyImage.isVisible}">
    <td> Igen <input type="checkbox" value="true" name="visible" checked="checked"/>  </td>
    </c:if>	
    <c:if test="${!modifyImage.isVisible}">
    <td> Igen <input type="checkbox" value="true" name="visible" /> </td>
    </c:if>	
    
    
  <td> 
  
  <select id="meta" name="meta" onchange="setupImagePosition()" style="background-color: white;color: Gray">

	
	
	<c:forEach items="${meta}"  var="data">
	
	<c:choose>
	
	<c:when test="${data == modifyImage.meta}" >

	<option  value="${data}" selected="selected">${data}</option>
	
	</c:when>
	
	<c:otherwise>
	<option  value="${data}">${data}</option>
	</c:otherwise>

	</c:choose>

	</c:forEach>
	
	
	
</select>

</td>
      
   
    <td><input type="text" style="color:#2C3E50" name="date" value="${modifyImage.date}" readonly="readonly"></td>

</tr>
  
  </table>

<input type="hidden" name="image" value="${modifyImage.codeString}">
<input type="hidden" name="id" value="${modifyImage.id}">
<input type="hidden" name="option" value="${option}">


</form>

<form id="add" action="AddImage"></form>
<form id="all" action="GetImage"></form>
<form id="txt" action="GetImage"></form>


<script type="text/javascript">

var position = "${modifyImage.meta}";


if( position == "NORMAL" ) {
	
	initImagePosition();
	
}
else if (position == "FORGATAS_BALRA" ) {
	
	 rotate270();
	
}
else if (position == "FORGATAS_JOBBRA" ) {
	
		rotate90();
	
}
else if (position == "ALLO_KERET" ) {
	
	rotateFrame90();
	
}
else if (position == "PANORAMA" ) {
	
	panorama();
	
}



function send() {
	
	document.getElementById("modiForm").submit();
	
}



function selectTopic() {
	
	
	var topic = document.getElementById("topics").value;
	
	document.getElementById("selected").value = topic;
	
}


function rotate90() {
	
	
	document.getElementById("frame").classList.add("rotate90");
	document.getElementById("frame").classList.add("newframeposition");
	
	document.getElementById("other").classList.add("rotate90");
	document.getElementById("other").classList.remove("other");
	document.getElementById("other").classList.add("newotherposition");
	
	
}

function rotate270() {
	
	
	document.getElementById("frame").classList.add("rotate90");
	document.getElementById("frame").classList.add("newframeposition");
	
	document.getElementById("other").classList.add("rotate270");
	document.getElementById("other").classList.remove("other");
	document.getElementById("other").classList.add("newotherposition");
	
	
}

function initImagePosition() {
	
	document.getElementById("frame").width = "1000";
	document.getElementById("other").width = "816";
	document.getElementById("other").height = "595";

	document.getElementById("frame").classList.remove("rotate90");
	document.getElementById("frame").classList.remove("newframeposition");
	document.getElementById("frame").classList.remove("panoramaframe");
	
	
	document.getElementById("other").classList.remove("rotate90");
	document.getElementById("other").classList.remove("rotate270");
	document.getElementById("other").classList.remove("newotherposition");
	document.getElementById("other").classList.remove("biggerimage");
	document.getElementById("other").classList.remove("rotateframe90position");
	
	document.getElementById("other").classList.add("other");
	
	
}


function rotateFrame90() {
	
	
	document.getElementById("frame").classList.add("rotate90");
	document.getElementById("frame").classList.add("newframeposition");
	
	document.getElementById("other").classList.remove("other");
	document.getElementById("other").classList.add("rotateframe90position");
	
	document.getElementById("other").width = "595";
	document.getElementById("other").height = "816";
	
	
	
}

function panorama() {
	
	document.getElementById("frame").classList.add("panoramaframe");
	document.getElementById("other").classList.remove("other");
	document.getElementById("other").classList.add("biggerimage");
	document.getElementById("frame").width = "1600";
	document.getElementById("other").width = "1315";
	
}

function setupImagePosition() {
	

	var meta = document.getElementById("meta").value;
	
	
	if( meta == "NORMAL" ) {
		
		initImagePosition();
		
	}
	else if( meta == "FORGATAS_JOBBRA") {
		
		initImagePosition();
		rotate90();
		
	}
	else if( meta == "FORGATAS_BALRA") {
		
		initImagePosition();
		rotate270();
		
	}
	else if( meta == "ALLO_KERET") {
		
		initImagePosition();
		rotateFrame90();
		
	}
	else if( meta == "PANORAMA") {
		
		initImagePosition();
		panorama();
		
	}
	
	
}

var from = "${option}";


function back() {
	
	
	if( from == "add") {
		
		
		document.getElementById("add").submit();
		
	}
	else if( from == "all") {
		
		
		document.getElementById("all").submit();
		
	}
	else {
		
		
		document.getElementById("txt").submit();
		
	}
	
}



</script>



</body>

</html>