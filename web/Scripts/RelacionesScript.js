function Execute(url, cFunction) {
  var xhttp;
  xhttp=new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState === 4 && this.status === 200) {
      cFunction(this);
    }
 };
  xhttp.open("GET", url, true);
  xhttp.send();
}
function ModificadorRelaciones(xhttp){
    document.getElementById("relaciones").innerHTML = xhttp.responseText;
}