
let generateNumber = function (min, max ) {
    return  Math.floor(Math.random() * (max - min + 1) + min);
};


let decomposeNumber = function (number) {
    let digits = [];
    while (number !== 0) {
        digits.push( number % 10);
        number = Math.floor(number / 10);
    }
    return digits;
};


let loadOperatorToContainer = function (selector, operatorPath, operatorFile) {
    let elemento = '<img class="" src="';
    elemento += operatorPath + operatorFile + '" />';
    $(selector).fadeOut();
    $(selector).html(elemento);
    $(selector).fadeIn();
};


let loadNumberToContainer = function (digits, selector, imgOfNumbers) {
    let contenido = '';
    let i;

    for (i = digits.length - 1; i >= 0; i--) {
        contenido += '<img class="" src="' + imgOfNumbers[digits[i]] + '" />';
    }

    if (digits.length == 2) {
        contenido = '<img class="" src="' + imgOfNumbers[10] + '" />' + contenido;
    }
    if (digits.length == 1) {
        contenido = '<img class="" src="' + imgOfNumbers[10] + '" />' +
                    '<img class="" src="' + imgOfNumbers[10] + '" />' +
                    contenido;
    }

    $(selector).fadeOut();
    $(selector).html(contenido);
    $(selector).fadeIn();
};


let rollUp = function (objQ, index, numeros, respuestas){
   // objQ.slideUp(100);
    if (respuestas[index] === 9) {
        respuestas[index] = 0;
    } else {
        respuestas[index] +=1;
    }

    let key = respuestas[index];

    objQ.attr('src', numeros[key]);
    objQ.show(1500);
};

let rollDown = function (objQ, index, numeros, respuestas){
   // $(this).hide(1000);
    if (respuestas[index] === 0) {
            respuestas[index] = 9;
    } else {
            respuestas[index] -=1;
    }

     let key = respuestas[index];

    objQ.attr('src', numeros[key]);
    objQ.show(1500);
};


let checkResult = function(app, sco) {

    let answer = app.respuestas[ 0 ] * 100 +
                 app.respuestas[ 1 ] *  10 +
                 app.respuestas[ 2 ];

    if (answer == app.operation.getResultado(app)) {
        app.play(app.settings['audio']['success']);
        app.operation.addPunto();

    } else {
        app.play(app.settings['audio']['fail']);
        app.operation.fallo();
    }

    SettingsManager.writeConfig();
    location.reload();

}
