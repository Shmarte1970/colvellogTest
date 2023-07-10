/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

/*function updateTime() {
    var now = new Date();
    var datetimeElement = document.getElementById('datetime');
    datetimeElement.textContent = now.toLocaleString('es-ES', { hour12: false }); 
}

function updateTime() {
    var now = new Date();
    var datetimeElement = document.getElementById('datetime');
    var options = { hour12: false, hour: '2-digit', minute: '2-digit'};
    var timeString = now.toLocaleTimeString('es-ES', options);
    var dateString = now.toLocaleDateString('es-ES');
    datetimeElement.textContent = timeString + ' - ' + dateString;
}

updateTime();
setInterval(updateTime, 1000);
*/


function updateTime() {
    var now = new Date();
    var datetimeElement = document.getElementById('datetime');
    var options = {
        hour12: true,
        hour: '2-digit',
        minute: '2-digit'
    };
    var timeString = now.toLocaleTimeString('es-ES', options);
    var dateString = now.toLocaleDateString('es-ES');
    datetimeElement.textContent = timeString + ' - ' + dateString;
}




function openAlarmPopup() {
    var hour = prompt("Ingrese la hora (0-12):");
    var minute = prompt("Ingrese los minutos (0-59):");
    var message = prompt("Ingrese un mensaje para la alarma:");
    hour = parseInt(hour, 10);
    minute = parseInt(minute, 10);

    if (!isNaN(hour) && !isNaN(minute)) {
        setAlarm(hour, minute, message);
    } else {
        alert("Por favor, ingrese una hora y minutos válidos.");
    }
}



function setAlarm(hour, minute, message) {
    var now = new Date();
    var alarmTime = new Date(now.getFullYear(), now.getMonth(), now.getDate(), hour, minute);

    var timeRemaining = alarmTime - now;

    if (timeRemaining > 0) {
        setTimeout(function() {
            // Código a ejecutar cuando llegue la hora de la alarma
            var formattedHour = hour % 12 || 12; // Convertir la hora a formato 12 horas
            var amPm = hour < 12 ? "AM" : "PM"; // Determinar si es AM o PM
            // alert("¡Alarma! Hora: " + formattedHour + ":" + minute.toString().padStart(2, '0') + " " + amPm + "\nMensaje: " + message);
            // Aquí puedes agregar el código para mostrar una notificación, reproducir un sonido, etc.
            Swal.fire("¡Alarma! Hora: " + formattedHour + ":" + minute.toString().padStart(2, '0') + " " + amPm + "\nMensaje: " + message);
        }, timeRemaining);


    } else {
        console.log("La hora de la alarma ya ha pasado.");
    }
}


updateTime();
setInterval(updateTime, 1000);

var datetimeElement = document.getElementById('datetime');
datetimeElement.addEventListener('click', openAlarmPopup);


