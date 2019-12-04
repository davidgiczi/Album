<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>List of Images</title>

	<style>

table, th, td {
  border: 1px solid #D6DBDF;
  border-collapse: collapse;
  }
  
th, td {
  padding: 15px;
  text-align: center;
  color:#2C3E50;
}

th {
	
	background-color: white;
	font-size: 15px;
}

button {
	
	background-color: white;
	color: #2C3E50;
}

.center {

	text-align: center;

}

</style>
</head>

<body style="background-color: lavender">

<table style="width:100%" >
  <tr>
  	<th>SORSZÁM</th>
    <th>CÍM</th>
    <th>KÉP</th> 
    <th>JEGYZET</th>
    <th>TÉMA</th>
    <th>LÁTHATÓ</th>
    <th>BEÁLLÍTÁS</th>
    <th>DÁTUM</th>
  
  </tr>


<c:forEach items="${images}" var="image" varStatus="loop">


<tr>

	<td>${loop.count}</td>
    <td>${image.fileName}</td>
    <td><img src="data:image/jpg;base64,${image.codeString}" width="300" height="200"/></td> 
    <td>${image.comment}</td>
    <td>${image.topic}</td>
    
    <c:if test="${image.isVisible}">
    <td> IGEN </td>
    </c:if>	
    <c:if test="${!image.isVisible}">
    <td> NEM </td>
    </c:if>	
    
    <td>${image.meta}</td>
    <td>${image.date}</td>

</tr>


</c:forEach>

</table><br>

<c:if test="${not empty add}">

<form action="AddImage">
	<h4 style="color:#2C3E50;">Add meg a kép sorszámát:</h4>
	
	<input type="text" name="recordNumber"/>
	
	<input style="background-color: white; color: #2C3E50" type="submit" value="Szerkesztés">
	
</form>

</c:if>

<c:if test="${not empty list}">

<form action="GetImage">
	<h4 style="color:#2C3E50;">Add meg a kép sorszámát:</h4>
	
	<input type="text" name="recordNumber"/>
	
	<input style="background-color: white; color: #2C3E50" type="submit" value="Szerkesztés">
	
</form>

</c:if>


  	<form id="add" action="AddImage"><input type="hidden" value="persist" name="persist"></form>
  	
  	<form id="back" action="AddImage"><input type="hidden" value="back" name="back"></form>
  	
	
	<c:if test="${not empty add}">
	
	<div class = "center">
	
	<button onclick="addImage()">Hozzáadás</button>
	
	</div>
	
	</c:if>
	

	<button  onclick="goBack()" style="float: right">Mégse</button>
  

<script>


function addImage() {
	
	document.getElementById("add").submit();
	
	
}

function goBack() {
	
	document.getElementById("back").submit();
	
}

var invalid = "${invalid}";


 if( invalid == 1 ) {
	 
	 
	 alert("Hibás beviteli adat, vagy nem létező sorszám.")
	 
 }



</script>


</body>
</html>