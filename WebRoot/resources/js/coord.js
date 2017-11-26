function isNearby(p1, p2) {
	if(Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2)) > 10) return false;
	return true;
}

function isLongPress(t1, t2) {
	return (t2 - t1) > 800;
}

function getXY(e, screenQ) {
	e = window.event || e;
	var x = (e.offsetX === undefined) ? getOffset(e).offsetX : e.offsetX;
	var y = (e.offsetY === undefined) ? getOffset(e).offsetY : e.offsetY;
	x = realPosition(x, screenQ);
	y = realPosition(y, screenQ);
	var pos = {"x":x, "y":y};
	return pos;
}

function getOffset(e) {
	var target = e.target;
	if (target.offsetLeft === undefined) {
		target = target.parentNode;
	}
	var pageCoord = getPageCoord(target);
	var eventCoord = {x:window.pageXOffset + e.clientX, y:window.pageYOffset + e.clientY};
	var offset = {offsetX:eventCoord.x - pageCoord.x, offsetY:eventCoord.y - pageCoord.y};
	return offset;
}

function getPageCoord(element) {
	var coord = {x:0, y:0};
	while (element) {
		coord.x += element.offsetLeft;
		coord.y += element.offsetTop;
		element = element.offsetParent;
	}
	return coord;
}

function realPosition(x, screenQ) {
	return Math.floor(x / screenQ);
}