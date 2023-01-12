/*!
* Start Bootstrap - Coming Soon v6.0.6 (https://startbootstrap.com/theme/coming-soon)
* Copyright 2013-2022 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-coming-soon/blob/master/LICENSE)
*/
// This file is intentionally blank
// Use this file to add JavaScript to your project


function showName(name) {
    alert("Here's the name: " + number + message);
}

function showAlert() {
    alert("The button was clicked!");
}

function showResult() {

	if (result != undefined) {

		var y;
		var i = document.createElement('div');
		i.setAttribute("class", "col rounded overflow-auto list-group ");
		i.setAttribute("id", "result");
		var addressContainer = document.getElementById("search");
		addressContainer.insertAdjacentElement("beforeend", i);

		for (var i in result) {
			var a = document.createElement('a');
			a.setAttribute("class", "list-group-item list-group-item-action");
			a.setAttribute("href", result[i].webUrl);
			var aName = document.createTextNode(result[i].webName);
			a.appendChild(aName);
			var div = document.getElementById("result");
			div.insertAdjacentElement("beforeend", a);
			console.log(result[i].webName);
			console.log(result[i].webUrl);
		}
	}else {
		console.log("result is empty");
	}
	
}

console.log("test")
function checkPopup() {
   alert('關鍵字   權重\ndcard.tw: 500\nptt.cc: 100\n笑死: 7\n笑爛: 7\n迷因: 7\n地獄: 5\n好笑: 4\n有笑:3\nXD: 3\nMEME: 6\n還好: -1.5\n爛梗: -3\n下去: -5\n沒梗: -5\n沒有梗: -10  ');
};



window.onload = showResult;