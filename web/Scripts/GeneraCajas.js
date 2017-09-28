var numCiudadano;

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

function inicializar(){
    Execute("ServletGeneradorPeriodico",GeneradorCajasCiudadano);
}

function cajaValue(value){
    var valorEnCaja = document.getElementById("cajas").value;
    valorEnCaja = parseInt(valorEnCaja)+parseInt(value);
    if (valorEnCaja < 0){
        valorEnCaja = 0;
    }
    document.getElementById("cajas").value = valorEnCaja;
    Execute("ServletGeneradorCajas?valor="+valorEnCaja,GeneradorCajasPeriodicos);
}

function caja2Value(value){
    var valorEnCaja = document.getElementById("cajas2").value;
    valorEnCaja = parseInt(valorEnCaja)+parseInt(value);
    if (valorEnCaja < 0){
        valorEnCaja = 0;
    }
    document.getElementById("cajas2").value = valorEnCaja;
    Execute("ServletGeneradorCajas2?valor="+valorEnCaja,GeneradorCajasPaises);
}

function GeneradorCajasCiudadano(xhttp){
    document.getElementById("formC").innerHTML = xhttp.responseText;
}
function GeneradorCajasPaises(xhttp){
    document.getElementById("formP").innerHTML = xhttp.responseText;
}
function GeneradorCajasPeriodicos(xhttp){
    document.getElementById("periodicos").innerHTML = xhttp.responseText;
}
function GeneradorCajaPartido(xhttp){
    document.getElementById("formP_"+numCiudadano).innerHTML = xhttp.responseText;
}

function checkEnter(e,caja){
    if (e.keyCode === 13) {
        e.preventDefault();
        cajaValue(0);
    }
}
function check2Enter(e,caja){
    if (e.keyCode === 13) {
        e.preventDefault();
        caja2Value(0);
    }
}

function PartidoConstruir(num){
    if(questionRadio1(num) === 'si'){
        numCiudadano = document.getElementById("numCiudadano_"+num).textContent;
        Execute("ServletGeneradorPartido?num="+numCiudadano,GeneradorCajaPartido);
    }else{
        document.getElementById("formP_"+num).innerHTML = "";
    }
}
function PartidoConstruirCargo(num){
    if(questionRadio2(num) === 'si'){
        numCiudadano = document.getElementById("numCiudadano_"+num).textContent;
        document.getElementById("puesto_"+num).innerHTML = "<p>Puesto ejercido<span>*</span></p> <input class=\"input-100\" type='text' name='puesto_"+num+"'/>";
    }else{
        document.getElementById("puesto_"+num).innerHTML = "";
    }
}
function PartidoConstruirDictamen(){
    if(questionRadio3() === 'si'){
        document.getElementById("dictamen").innerHTML = "<p>Dictamen<span>*</span></p> <input class='input-100' type='text' name='dictamen'/>";
    }else{
        document.getElementById("dictamen").innerHTML = "";
    }
}

function questionRadio1(num){
    var radios = document.getElementsByName('pregunta_'+num);
    for (var i = 0, length = radios.length; i < length; i++) {
        if (radios[i].checked) {
            return radios[i].getAttribute("value");
        }
    }
}
function questionRadio2(num){
    var radios = document.getElementsByName('pregunta2_'+num);
    for (var i = 0, length = radios.length; i < length; i++) {
        if (radios[i].checked) {
            return radios[i].getAttribute("value");
        }
    }
}
function questionRadio3(){
    var radios = document.getElementsByName('casoT');
    for (var i = 0, length = radios.length; i < length; i++) {
        if (radios[i].checked) {
            return radios[i].getAttribute("value");
        }
    }
}