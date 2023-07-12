
    function generateOptions() {
        var seleccion = document.getElementById('huespedes');
        
        for (let i = 1; i < 13; i++) {
            var options = document.createElement('option');
            options.value = i;
            options.innerHTML = i;
            seleccion.append(options);
        }
    }

    function usarDatos(ubicacion, fecha, huespedes) {
        
    }

    
    // Para que se active al cargar la página
window.onload = function(){


    var boton = document.getElementById("send");
    boton.addEventListener('click', ()=>{
        let ubicacion =  document.getElementById("ubicacion").value;
        let fecha = document.getElementById("fechaCheckIn").value;
        let huespedes = document.getElementById("huespedes").value;
        var navbar = document.getElementById('navbarError');
        var error = document.createElement('p');
        if (ubicacion == "" || fecha == "") {
            error.innerHTML = "Introduce todos los datos";
            navbar.append(error);
        }else{
            if (error.innerHTML != "") {
                error.innerHTML = "";
                navbar.removeChild();
            }
        }
        console.log(ubicacion, fecha, huespedes)
    })
    generateOptions();
}