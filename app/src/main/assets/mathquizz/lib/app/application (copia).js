"use strict";

const EASY = 0;
const MEDIUM = 1;
const HARD = 2;
const ADICION = 0, SUSTRACCION = 1, PRODUCTO = 2, DIVISION = 3;

let settings = {'numeros': ['./img/gui/numbers/cero.png',
                            './img/gui/numbers/uno.png',
                            './img/gui/numbers/dos.png',
                            './img/gui/numbers/tres.png',
                            './img/gui/numbers/cuatro.png',
                            './img/gui/numbers/cinco.png',
                            './img/gui/numbers/seis.png',
                            './img/gui/numbers/siete.png',
                            './img/gui/numbers/ocho.png',
                            './img/gui/numbers/nueve.png',
                            './img/gui/numbers/vacio.png'],

                'buttonsPath': './img/gui/buttons/',
                'operatorsPath': './img/gui/operators/',
                'plusName': 'plus.png',
                'barraName':'barra.png',
                'dificulty': EASY,
                'music': true,
                'audio': {'success': 'mathquizz/sfx/success.wav',
                          'fail': 'mathquizz/sfx/fail.wav',
                          'button': 'mathquizz/sfx/button_sfx.wav'}
                };


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

    if (answer == app.operation()) {
        app.play(app.settings['audio']['success']);
        app.puntos += 1;
        sco.html(app.puntos);
        localStorage.setItem("score", app.puntos);

    } else {
        app.play(app.settings['audio']['fail']);
    }
    location.reload();

}


class suma {



}


class Game {

  constructor(options) {
    this.firstNumber = 0;
    this.secondNumber = 0;
    this.digits1 = [ 0, 0, 0 ];
    this.digits2 = [ 0, 0, 0 ];
    this.respuestas = [ 0, 0, 0 ];
    this.controls = {};
    this.operation = function(){};
    this.operadorName = '';

    this.settings = options['settings'];
    this.selectorN1 = '#numero-1';
    this.selectorOpe= '#operador';
    this.selectorN2 = '#numero-2';
    this.selectorBarra = '#barrita';
    this.selectorResult = '#resultado';
    this.selectorScore = '#score';

    if (localStorage.getItem('score')) {
        this.puntos = Number(localStorage.getItem('score'));
    } else {
        this.puntos = 0;
        localStorage.setItem("score", this.puntos);
    }
    this.selectorCheck = '#check';

    this.controlResp = {};

    this.limits = options['limits'];
    this.secondNumberLimit = function(num){ return this.limits['top'] - num; };
  }

  setOperation(oper) {
    this.operation = oper;
    if (this.operation == ADICION) {
        this.operation = function(){ return this.firstNumber + this.secondNumber; };
        this.operadorName = this.settings['plusName'];

        this.secondNumberLimit = function(num1){ return this.limits['top'] - num1; };

    } else if (this.operation == SUSTRACCION) {
        this.operation = function(){ return this.firstNumber - this.secondNumber; };

        this.secondNumberLimit = function(num1){ return num1; };

    } else if (this.operation == PRODUCTO) {
             this.operation = function(){ return this.firstNumber * this.secondNumber; };

             this.secondNumberLimit = function(num1){ return num1; };

    } else if (this.operation == DIVISION) {
             this.operation = function(){ return Math.floor(this.firstNumber / this.secondNumber); };

             this.secondNumberLimit = function(num1){ return Math.floor(this.limits['top'] / num1); };
    }
  }

  rollNumbers(){
      this.firstNumber = generateNumber(this.limits['bottom'], this.limits['top']);
      this.secondNumber = generateNumber(this.limits['bottom'],
                                         this.secondNumberLimit( this.firstNumber ));
  }

  decomposeNumbers(){
      this.digits1 = decomposeNumber( this.firstNumber );
      this.digits2 = decomposeNumber( this.secondNumber );
  }

  bind(){

     this.controls['up'] = {'unidad': $('#control-up').find('img:eq(2)'),
                            'decena': $('#control-up').find('img:eq(1)'),
                            'centena': $('#control-up').find('img:eq(0)') };

     this.controls['down'] = {'unidad': $('#control-down').find('img:eq(2)'),
                              'decena': $('#control-down').find('img:eq(1)'),
                              'centena': $('#control-down').find('img:eq(0)') };

     this.controls['toolbar'] = {'reload': $('#reload'),
                                 'sound': $('#sound'),
                                 'facebbok': $('facebook')};

     this.controlResp = {'unidad': $('#resultado').find('img:eq(2)'),
                         'decena': $('#resultado').find('img:eq(1)'),
                         'centena': $('#resultado').find('img:eq(0)')};

     var uni = this.controlResp['unidad'];
     var dec = this.controlResp['decena'];
     var cen = this.controlResp['centena'];
     var nums = this.settings['numeros'];
     var resps = this.respuestas;
     var dis = this;

     this.controls['up']['unidad'].on('click', function() {
         dis.play(dis.settings['audio']['button']);
         rollUp(uni, 2, nums, resps);
     });

     this.controls['down']['unidad'].on('click', function() {
              dis.play(dis.settings['audio']['button']);
              rollDown(uni, 2, nums, resps);
     });

     this.controls['up']['decena'].on('click', function() {
              dis.play(dis.settings['audio']['button']);
              rollUp(dec, 1, nums, resps);
     });

     this.controls['down']['decena'].on('click', function() {
              dis.play(dis.settings['audio']['button']);
              rollDown(dec, 1, nums, resps);
     });

     this.controls['up']['centena'].on('click', function() {
                   dis.play(dis.settings['audio']['button']);
                   rollUp(cen, 0, nums, resps);
     });

     this.controls['down']['centena'].on('click', function() {
                   dis.play(dis.settings['audio']['button']);
                   rollDown(cen, 0, nums, resps);
     });


     var sco = $(this.selectorScore);
     this.controls['check'] = $(this.selectorCheck);
     this.controls['check'].on('click', function(){
         checkResult(dis, sco);
     });
  }

  unBind(){
           this.controls['up']['unidad'].unbind('click');

           this.controls['down']['unidad'].unbind('click');

           this.controls['up']['decena'].unbind('click');

           this.controls['down']['decena'].unbind('click');

           this.controls['up']['centena'].unbind('click');

           this.controls['down']['centena'].unbind('click');
  }

  loadFirstNumber(){
      loadNumberToContainer(this.digits1, this.selectorN1, this.settings['numeros']);
  }

  loadSecondNumber(){
      loadNumberToContainer(this.digits2, this.selectorN2, this.settings['numeros']);
  }

  loadOperator(){
      loadOperatorToContainer(this.selectorOpe, this.settings['operatorsPath'], this.settings['plusName']);
  }

  loadBarra(){
      loadOperatorToContainer(this.selectorBarra, this.settings['operatorsPath'], this.settings['barraName']);
  }

  loadAnswer(){
      loadNumberToContainer(this.respuestas, this.selectorResult, this.settings['numeros']);
  }

  loadScore(){
      $(this.selectorScore).html(this.puntos);
  }

  initialize(){
      this.rollNumbers();
      this.decomposeNumbers();

      this.setOperation(ADICION);
  }

  render() {
     this.loadScore();
     this.loadFirstNumber();
     this.loadOperator();
     this.loadSecondNumber();
     this.loadBarra();
     this.loadAnswer();
     this.bind();
  }

  play(sonido){
     //this.sfx.attr('src', sonido)[0];
     //this.sfx[0].play();

     AndAud.playAudio(sonido);
  }

}