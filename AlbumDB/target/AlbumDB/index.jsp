<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>

<title>Album</title>
</head>
<body>

<style>

body{

background-color:lavender;
text-align: center;
}


.frame {

 position: absolute;
 top: 100px;
 left:500px;
  z-index: -2;
  
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
 
 .rotateframe90position {
 position: absolute;
 top: 190px;
 left: 700px;
 z-index: -1;
 }
 
 .newnextposition {
 position: absolute;
 top: 1130px;
 left: 550px;
 }
 
 .newprevposition {
 position: absolute;
 top: 1130px;
 left: 650px;
 }
 
 .newstartposition {
 position: absolute;
 top: 1130px;
 left: 1300px;
 }
 
 
 .newstopposition {
 position: absolute;
 top: 1130px;
 left: 1400px;
 }
 
 .newtopicposition {
 position: absolute;
 top: 1130px;
 left: 925px;
 }
 
 .newspeedinputposition {
 position: absolute;
 top: 1130px;
 left: 1050px;
 }

.rotate60{

	 transform: rotate(60deg);
    -webkit-transform: rotate(60deg);
    -moz-transform: rotate(60deg);
    -ms-transform: rotate(60deg);
    
}

.rotate90{

	 transform: rotate(90deg);
    -webkit-transform: rotate(90deg);
    -moz-transform: rotate(90deg);
    -ms-transform: rotate(90deg);
    
}

.rotate120{

	 transform: rotate(120deg);
    -webkit-transform: rotate(120deg);
    -moz-transform: rotate(120deg);
    -ms-transform: rotate(120deg);
    
}

.rotate180{

	 transform: rotate(180deg);
    -webkit-transform: rotate(180deg);
    -moz-transform: rotate(180deg);
    -ms-transform: rotate(180deg);
    
}


.rotate240{

	 transform: rotate(240deg);
    -webkit-transform: rotate(240deg);
    -moz-transform: rotate(240deg);
    -ms-transform: rotate(240deg);
    
}

.rotate270{

	 transform: rotate(270deg);
    -webkit-transform: rotate(270deg);
    -moz-transform: rotate(270deg);
    -ms-transform: rotate(270deg);
    
}

.rotate300{

	 transform: rotate(300deg);
    -webkit-transform: rotate(300deg);
    -moz-transform: rotate(300deg);
    -ms-transform: rotate(300deg);
    
}

.dave {
 position: absolute;
 top: 205px;
 left: 590px;
 z-index: 2;
}

.anna {
 position: absolute;
 top: 205px;
 left: 998px;
 z-index: 2;
}

.other {
 position: absolute;
 top: 205px;
 left: 590px;
 z-index: -1;
 }
 
 .next {
 position: absolute;
 top: 930px;
 left: 550px;
 }
 
 .prev {
 position: absolute;
 top: 930px;
 left: 650px;
 }
 
 .start {
 position: absolute;
 top: 930px;
 left: 1300px;
 }
 
 
 .stop {
 position: absolute;
 top: 930px;
 left: 1400px;
 }
 
 .topic {
 position: absolute;
 top: 930px;
 left: 925px;
 }
 
 .speedinput {
 position: absolute;
 top: 930px;
 left: 1050px;
 }
 
 button {
	
	background-color: white;
	color: #2C3E50;
}
 
</style>

</head>
<body>


<font face = "Lucida Handwriting" size = "15"  id="txt" style="color: red" ></font>


<img id="frame" src="" alt="" width="1000" height="800" class="frame">
<img id="dave" src=""  alt="" width="408" height="595" class="dave">
<img id="anna" src=""  alt="" width="408" height="595" class="anna">
<img id="other" src="" alt="" width="816" height="595" class="other">



<form name="topicList"  action="InitAlbum" accept-charset="UTF-8">

<select id = "topic" name = "topic" onchange = "sendValue()" class = "topic" style="background-color: white;color: Gray">


<c:forEach items="${topics}" var="topic">

<c:if test="${chosenTopic == topic}"></c:if>
<option selected="selected" value="${topic}">${topic}</option>
</c:forEach>

</select>


</form>


<div class="speedinput" id="speed" style="color: Gray">
<input type="text" size="1" id="input" value="3">&nbsp;sec/kép
</div>

<button onclick="next()" class="next" id="next">Következő</button>
<button onclick="previous()" class="prev" id="prev">Előző</button>
<button onclick="start()" class="start" id="start">Vetítés</button>
<button onclick="stop()" class="stop" id="stop">Stop</button>


<h6><a style="color: #2980B9; float: right" href="config.jsp">Config</a></h6>


<script>

	
	var actualImages = [];
	var titleStore = [];
	var metaDataStore = [];

	var bgc = [];
	var introText = [];
	var imageIndexMemory = [];
	var counter = 0;
	var imageIndex = -1;
	var introIndex = 0;
	var speed;
	var chosenTopic;
	var begining;
	var run;
		
	
	<c:forEach items="${codes}" var="image">
	actualImages.push("${image}");
	</c:forEach>
	
	<c:forEach items="${titles}" var="title">
	titleStore.push("${title}");
	</c:forEach>
	
	
	<c:forEach items="${meta}" var="metadata">
	metaDataStore.push("${metadata}");
	</c:forEach>
	

	<c:forEach items="${color}" var="bcgColor">
	bgc.push("${bcgColor}");
	</c:forEach>
	
	
	<c:forEach items="${intro}" var="text">
	introText.push("${text}");
	</c:forEach>
	
	
	begining=setInterval(intro, 500);

	
	function sendValue() {
	
	
		 document.forms["topicList"].submit();
		
	
	}
	

	function intro() {
		
		document.getElementById("txt").innerHTML = introText[introIndex];
		
		changeBgColor();
		frameRotation();
		introIndex++;
		
		if(introIndex == introText.length) {
			
			clearInterval(begining);
			init();
		}
			
	}


	function next() {
			
		imageIndex = counter - 1;
		
		if( imageIndex < actualImages.length - 1 ) {
			
			counter++;
			imageIndex++;
			imageIndexMemory.push(imageIndex);
			showImage();
			
			
		}
		else if( imageIndex == actualImages.length - 1 ) {
			
			document.getElementById("txt").innerHTML = "";
			init();
			
		}
			
	}

	function previous() {
		
			
		if ( counter == 0 && imageIndex == -1 ) {
			
			counter = actualImages.length;
			imageIndex = counter - 1;
			showImage();
			return;
		}
		
		if ( counter > 0 ) {
	
			
			if( counter == 1 ) {
				
				init();
				document.getElementById("txt").innerHTML = "";
				return;
				
			}
			
			if( imageIndexMemory.length == 0 ) {
				
				imageIndex--;
				
			}
			else if ( imageIndexMemory.length == 1 ) {
				
				imageIndexMemory.pop();
				imageIndex--;
				
			}
			else if ( imageIndexMemory.length > 1 ) {
				
				imageIndexMemory.pop();
				imageIndex = imageIndexMemory[imageIndexMemory.length-1];
				
				
			}
		
			counter--;
			showImage();
			
		}
		
						
	}

	function changeBgColor() {
		
		var index=Math.floor(Math.random() * bgc.length);
		
		document.body.style.backgroundColor = bgc[index];
	}


	function running() {
		
		
		imageIndex = Math.floor(Math.random() * actualImages.length);
		
			while( contain() ) {
				
				if(counter == actualImages.length) break;
				
				imageIndex = Math.floor(Math.random() * actualImages.length);
				
			}
		
			if(counter < actualImages.length){
			
			counter++;
			showImage();
			
			return;
		}
			
			document.getElementById("txt").innerHTML = "Vége a vetítésnek.";
			init();
			stop();
			
			
	}


	function start() {
		
		speed=input();
		
		if(speed == -1){
			
			alert("Invalid input value!");
			return;
		}
		
		running();
		run=setInterval(running, speed);

	}


	function stop() {

		clearInterval(run);
	}

	function showImage() {
		
		showImageNormalMode();
		
		document.getElementById("dave").src = "";
		document.getElementById("anna").src = "";
		
		
			
		if( metaDataStore[imageIndex] == "FORGATAS_JOBBRA" ) {
			
			
			rotate90();
			
		}
		
		else if( metaDataStore[imageIndex] == "FORGATAS_BALRA" ) {
			
			
			rotate270();
			
		}
		
		else if( metaDataStore[imageIndex] == "PANORAMA" ) {
			
			panorama();
			
		}
		else if( metaDataStore[imageIndex] == "ALLO_KERET" ) {
			
			
			rotateFrame90();
			
		}
		
		
		document.getElementById("other").src = "";
		document.getElementById("other").src ="data:image/jpg;base64,"+actualImages[imageIndex];
		document.getElementById("txt").innerHTML = counter + "/" + actualImages.length + " "+titleStore[imageIndex];
		changeBgColor();
		
	}

	function contain() {
		
		var i = 0;
		
		for( i; i < imageIndexMemory.length; i++ ) {
			
		if( imageIndexMemory[i] == imageIndex ){
						
			return true;
				
			}		
		}
		
		imageIndexMemory.push(imageIndex);
				
		return false;
	}

	function init() {
		
		showImageNormalMode();
		
		imageIndex = -1;
		counter = 0;
		imageIndexMemory = [];
		
		document.getElementById("other").src = "";
		document.getElementById("dave").src = "images/Dave.jpg";
		document.getElementById("anna").src = "images/Anna.jpg";
		
	}

	function input() {
		
		speed = document.getElementById("input").value;
		
		if(isNaN(speed)){
			
			return -1;
		}
		else if(speed <= 0){
			
			return -1;
		}
		
		return 1000 * speed;
	}


	function rotate90() {
		
		
		document.getElementById("frame").classList.add("rotate90");
		document.getElementById("frame").classList.add("newframeposition");
		
		document.getElementById("other").classList.add("rotate90");
		document.getElementById("other").classList.remove("other");
		document.getElementById("other").classList.add("newotherposition");
		
		document.getElementById("next").classList.remove("next");
		document.getElementById("next").classList.add("newnextposition");
		
		document.getElementById("prev").classList.remove("prev");
		document.getElementById("prev").classList.add("newprevposition");
		
		document.getElementById("speed").classList.remove("speedinput");
		document.getElementById("speed").classList.add("newspeedinputposition");
		
		document.getElementById("topic").classList.remove("topic");
		document.getElementById("topic").classList.add("newtopicposition");
		
		document.getElementById("start").classList.remove("start");
		document.getElementById("start").classList.add("newstartposition");
		
		document.getElementById("stop").classList.remove("stop");
		document.getElementById("stop").classList.add("newstopposition");
	}

	function rotate270() {
		
		
		document.getElementById("frame").classList.add("rotate90");
		document.getElementById("frame").classList.add("newframeposition");
		
		document.getElementById("other").classList.add("rotate270");
		document.getElementById("other").classList.remove("other");
		document.getElementById("other").classList.add("newotherposition");
		
		document.getElementById("next").classList.remove("next");
		document.getElementById("next").classList.add("newnextposition");
		
		document.getElementById("prev").classList.remove("prev");
		document.getElementById("prev").classList.add("newprevposition");
		
		document.getElementById("speed").classList.remove("speedinput");
		document.getElementById("speed").classList.add("newspeedinputposition");
		
		document.getElementById("topic").classList.remove("topic");
		document.getElementById("topic").classList.add("newtopicposition");
		
		document.getElementById("start").classList.remove("start");
		document.getElementById("start").classList.add("newstartposition");
		
		document.getElementById("stop").classList.remove("stop");
		document.getElementById("stop").classList.add("newstopposition");
	}

	function showImageNormalMode() {
		
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
		
		document.getElementById("next").classList.remove("newnextposition");
		document.getElementById("next").classList.add("next");
		
		document.getElementById("prev").classList.remove("newprevposition");
		document.getElementById("prev").classList.add("prev");
		
		document.getElementById("speed").classList.remove("newspeedinputposition");
		document.getElementById("speed").classList.add("speedinput");
		
		document.getElementById("topic").classList.remove("newtopicposition");
		document.getElementById("topic").classList.add("topic");
		
		document.getElementById("start").classList.remove("newstartposition");
		document.getElementById("start").classList.add("start");
		
		document.getElementById("stop").classList.remove("newstopposition");
		document.getElementById("stop").classList.add("stop");
	}
	
	
	function rotateFrame90() {
		
		document.getElementById("frame").classList.add("rotate90");
		document.getElementById("frame").classList.add("newframeposition");
		
		document.getElementById("other").classList.remove("other");
		document.getElementById("other").classList.add("rotateframe90position");
		
		document.getElementById("other").width = "595";
		document.getElementById("other").height = "816";
		
		document.getElementById("next").classList.remove("next");
		document.getElementById("next").classList.add("newnextposition");
		
		document.getElementById("prev").classList.remove("prev");
		document.getElementById("prev").classList.add("newprevposition");
		
		document.getElementById("speed").classList.remove("speedinput");
		document.getElementById("speed").classList.add("newspeedinputposition");
		
		document.getElementById("topic").classList.remove("topic");
		document.getElementById("topic").classList.add("newtopicposition");
		
		document.getElementById("start").classList.remove("start");
		document.getElementById("start").classList.add("newstartposition");
		
		document.getElementById("stop").classList.remove("stop");
		document.getElementById("stop").classList.add("newstopposition");
		
	}
	
	function frameRotation(){

		
		
		if(introIndex == 0){
			
			document.getElementById("frame").src = "images/frame.jpg";
			document.getElementById("frame").width = "200";
			document.getElementById("frame").height = "160";
			document.getElementById("frame").classList.add("rotate60");
			
		}
		else if(introIndex == 1){
			
			document.getElementById("frame").width = "400";
			document.getElementById("frame").height = "320";
			document.getElementById("frame").classList.remove("rotate60");
			document.getElementById("frame").classList.add("rotate120");
			
		}
		else if(introIndex == 2){
			
			document.getElementById("frame").width = "600";
			document.getElementById("frame").height = "480";
			document.getElementById("frame").classList.remove("rotate120");
			document.getElementById("frame").classList.add("rotate180");
			
		}
		else if(introIndex == 3){
			
			document.getElementById("frame").width = "800";
			document.getElementById("frame").height = "640";
			document.getElementById("frame").classList.remove("rotate180");
			document.getElementById("frame").classList.add("rotate240");
			
		}
		else if(introIndex == 4){
			
			document.getElementById("frame").width = "1000";
			document.getElementById("frame").height = "800";
			document.getElementById("frame").classList.remove("rotate240");
			document.getElementById("frame").classList.add("rotate300");
			
		}
		else if(introIndex == 5){
			
			document.getElementById("frame").classList.remove("rotate300");

		}
	}
	
	function panorama() {
		
		document.getElementById("frame").classList.add("panoramaframe");
		document.getElementById("other").classList.remove("other");
		document.getElementById("other").classList.add("biggerimage");
		document.getElementById("frame").width = "1600";
		document.getElementById("other").width = "1315";
		
	}
	
	
</script>


</body>
</html>