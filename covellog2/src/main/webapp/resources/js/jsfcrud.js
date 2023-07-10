
PrimeFaces.locales['es'] = {
    closeText: 'Cerrar',
    prevText: 'Anterior',
    nextText: 'Siguiente',
    monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
    monthNamesShort: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
    dayNames: ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'],
    dayNamesShort: ['Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab'],
    dayNamesMin: ['D', 'L', 'M', 'X', 'J', 'V', 'S'],
    weekHeader: 'Semana',
    firstDay: 1,
    isRTL: false,
    showMonthAfterYear: false,
    yearSuffix: '',
    timeOnlyTitle: 'Sólo hora',
    timeText: 'Tiempo',
    hourText: 'Hora',
    minuteText: 'Minuto',
    secondText: 'Segundo',
    currentText: 'Fecha actual',
    ampm: false,
    month: 'Mes',
    week: 'Semana',
    day: 'Día',
    allDayText: 'Todo el día',
    today: 'Hoy',
    clear: 'Claro'
};


PrimeFaces.widget.Dialog.prototype.applyFocus = function() {
  var firstInput = this.jq.find(':not(:submit):not(:button):input:visible:enabled:first');
  if(!firstInput.hasClass('disable-autofocus')) {
      firstInput.focus();
  }
}

/*
 PrimeFaces.widget.Dialog.prototype.applyFocus = function() {
  this.jq.find(':not(:submit):not(:button):input:visible:enabled:first').focus();
}
 */

function handleSubmit(args, dialog) {
    var jqDialog = jQuery('#' + dialog);
    if (args.validationFailed) {
        //alert(dialog + ": Validation Failed!");
        jqDialog.effect('shake', {times: 3}, 100);
    } else {
        //alert("OK!");
        PF(dialog).hide();
        
    }
}

function openDialog(dialog) {
    PF(dialog).show();
}

function closeDialog(dialog) {
    PF(dialog).hide();
}

function redirectOnAction(args, url) {
    alert('ca');
    if (args.validationFailed) {
        alert('Error en el action');
    } else {
        window.location.href = url;
    }
}

function focusFirst() {
    $(document).ready(function() {
        $("th > input:first").focus();
    });
}

var typingTimer;                //timer identifier
var doneTypingInterval = 500;  //time in ms, 5 second for example

function onInputChangeTimeout(input, funcion) {
    $( document ).ready(function() {  
        //var input = $('#EmpresaListForm\\:filtro-diagonal');
        input.on('keyup', function () {
            clearTimeout(typingTimer);
            typingTimer = setTimeout(funcion, doneTypingInterval);
        });
        //on keydown, clear the countdown 
        input.on('keydown', function () {
            clearTimeout(typingTimer);
        });
    });
 }
 
 var onWindowFocus_blurred = false;
 function onWindowFocus(funcion) {
    window.onblur = function() { onWindowFocus_blurred = true; };
    window.onfocus = function() { 
        if (onWindowFocus_blurred) {
            onWindowFocus_blurred = false;
            if (typeof funcion === "function") {
                funcion();
            }
        } 
    };
 }
                
/*
var miTimeout;
    
$(document).ready(function() {
   $(window).on('resize', function(){
       clearTimeout(miTimeout);
       miTimeout = setTimeout(redimensionar, 500);
   }).resize();
   $("#contenido").on('click', function(){
       redimensionar();
   }).focus();
});

function redimensionar() {
   var height = 0;
   $("#panel-interior").parent().children('div').each(function(){
       height += $(this).height();
   });        
   height = $("#panel-interior").parent().height() - height + $('.ui-datatable-scrollable-body').height();
   $('.ui-datatable-scrollable-body').css('max-height', height + 'px').css('min-height', height + 'px'); //set max height
}*/