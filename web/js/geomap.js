var map;

if (navigator.geolocation) {
	navigator.geolocation.getCurrentPosition(showMap, showError);
} else {
	alert('not supported');
};

function showMap(position) {
	// ...
}

function showError(error) {
	// ...
}
