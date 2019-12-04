<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Options</title>


<style>

button {
	
	background-color: white;
	color: #2C3E50;
}

</style>


</head>


<body style="background-color: lavender">

<form action="AddImage">

<input type="hidden"  name="input" value="input">

<button>Hozzáadás</button>

</form><br>

<form action="GetImage">

<input type="hidden"  name="getAll" value="getall">
<button>Listázás</button>

</form>


<form id = "back" action="Init"></form><br>


<form action="GetImage" accept-charset="UTF-8">
	
	<input type="text" name="search"/>
	<button>Keresés</button>
	
		
</form><br>

<button onclick="restart()">Vissza</button>

<script>



var persistImages = "${inputImagesNumber}";
var ok = "${ok}";
var notFound =  "${notfound}";

if( ok == 1 && persistImages == 1 ) {

alert("Kép hozzáadva az albumhoz.");
}

else if ( ok == 1 &&  persistImages > 1) {

alert("Képek hozzáadva az albumhoz.");
}
else if( ok == 0 && persistImages == 1 ) {

alert("Kép hozzáadása az albumhoz sikertelen.");
}

else if ( ok == 0 &&  persistImages > 1) {

alert("Képek hozzáadása az albumhoz sikertelen.");

}

if( notFound ==1 ) {
	
	alert("A megadott adatokkal kép nem található.")
}

function restart() {
	
	
	document.getElementById("back").submit();
	
}


</script>


</body>



</html>
