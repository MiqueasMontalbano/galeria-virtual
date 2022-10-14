(function () {
  'use strict'
  
  var forms = document.querySelectorAll('.needs-validation')
  
  Array.prototype.slice.call(forms)
    .forEach(function (form) {
      form.addEventListener('submit', function (event) {
        if (!form.checkValidity()) {
          event.preventDefault()
          event.stopPropagation()
        }

        form.classList.add('was-validated')
      }, false)
    })
})()

function cargarPais() {
  var array = ["Argentina", "Chile"];
  array.sort();
  addOptions("pais", array);
}


function cargarPaises() {
  var array = ["Argentina", "Chile"];
  array.sort();
  addOptions("pais", array);
}


//Función para agregar opciones a un <select>.
function addOptions(domElement, array) {
  var selector = document.getElementsByName(domElement)[0];
  for (pais in array) {
      var opcion = document.createElement("option");
      opcion.text = array[pais];
      // Añadimos un value a los option para hacer mas facil escoger los provincias
      opcion.value = array[pais].toLowerCase()
      selector.add(opcion);
  }
}



function cargarProvincias() {
  // Objeto de paises con provincias
  var listaProvincias = {
    argentina: ["Buenos Aires", "Buenos Aires Capital", "Catamarca", "Chaco", "Chubut", "Cordoba", "Corrientes", "Entre Rios", "Formosa", "Jujuy", "La Pampa", "La Rioja", "Mendoza", "Misiones", "Neuquen", "Rio Negro", "Salta", "San Juan", "San Luis", "Santa Cruz", "Santa Fe", "Santiago del Estero", "Tierra del Fuego", "Tucuman"],
    chile: ["Arica","Tarapacá","Antofagasta",'Atacama','Coquimbo','Valparaiso','Santiago','Maule','Biobío','Araucanía','Los Ríos','Los Lagos'],
    
  }
  
  var paises = document.getElementById('pais')
  var provincias = document.getElementById('provincia')
  var paisSeleccionado = paises.value
  
  // Se limpian los provincias
  provincias.innerHTML = '<option value="">Seleccione un Provincia...</option>'
  
  if(paisSeleccionado !== ''){
    // Se seleccionan los provincias y se ordenan
    paisSeleccionado = listaProvincias[paisSeleccionado]
    paisSeleccionado.sort()
  
    // Insertamos los provincias
    paisSeleccionado.forEach(function(provincia){
      let opcion = document.createElement('option')
      opcion.value = provincia
      opcion.text = provincia
      provincias.add(opcion)
    });
  }
  
}


cargarPaises();