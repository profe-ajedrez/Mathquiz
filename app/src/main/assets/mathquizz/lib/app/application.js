"use strict";

class Suma {

    constructor() {}

    getResultado(app) {
        return app.firstNumber + app.secondNumber;
    }

    getLimit(app) {
        return app.limits['top'] - app.firstNumber;
    }

    getGrafico(app) {
        return app.settings['plusName'];
    }

    getFirstNumber(app) {
        return generateNumber(app.limits['bottom'], app.limits['top']);
    }

    getSecondNumber(app) {
        return generateNumber(app.limits['bottom'], this.getLimit(app));
    }

    getPuntos(app) {
        return SettingsManager.getPuntosSuma();
    }

    addPunto(app) {
        SettingsManager.setPuntosSuma( SettingsManager.getPuntosSuma() +1 );
    }

    fallo(app) {
            SettingsManager.setFallosSuma( SettingsManager.getFallosSuma() +1 );
    }
}

class Resta {

    constructor() {}

    getResultado(app) {
        return app.firstNumber - app.secondNumber;
    }

    getLimit(app) {
        return app.firstNumber;
    }

    getGrafico(app) {
        return app.settings['restaName'];
    }

    getFirstNumber(app) {
            return generateNumber(app.limits['bottom'], app.limits['top']);
        }

    getSecondNumber(app) {
            return generateNumber(app.limits['bottom'], this.getLimit(app));
    }

    getPuntos(app) {
        return SettingsManager.getPuntosResta();
    }

    addPunto(app) {
        SettingsManager.setPuntosResta( SettingsManager.getPuntosResta() +1 );
    }

    fallo(app) {
                SettingsManager.setFallosResta( SettingsManager.getFallosResta() +1 );
    }
}

class Producto {

    constructor() {}

    getResultado(app) {
        return app.firstNumber * app.secondNumber;
    }

    getLimit(app) {
        return Math.floor(app.limits['top'] / app.firstNumber);
    }

    getGrafico(app) {
        return app.settings['multiName'];
    }

    getFirstNumber(app) {
            return generateNumber(app.limits['bottom'], app.limits['top'] / 2);
        }

    getSecondNumber(app) {
            return generateNumber(app.limits['bottom'], this.getLimit(app));
    }

    getPuntos(app) {
        return SettingsManager.getPuntosMulti();
    }

    addPunto(app) {
        SettingsManager.setPuntosMulti( SettingsManager.getPuntosMulti() +1 );
    }

    fallo(app) {
                SettingsManager.setFallosMulti( SettingsManager.getFallosMulti() +1 );
    }
}

class Division {

    constructor() {}

    getResultado(app) {
        return app.firstNumber / app.secondNumber;
    }

    getLimit(app) {
        return app.firstNumber;
    }

    getGrafico(app) {
        return app.settings['diviName'];
    }

    getFirstNumber(app) {
            return generateNumber(app.limits['bottom'], app.limits['top']);
        }

    getSecondNumber(app) {
        var num = 0;
        while (true){

            num = generateNumber(app.limits['bottom'], this.getLimit(app));
            if (app.firstNumber % num ===0) {
                break;
            }
        }
        return num;
    }

    getPuntos(app) {
        return SettingsManager.getPuntosDiv();
    }

    addPunto(app) {
        SettingsManager.setPuntosDiv( SettingsManager.getPuntosDiv() +1 );
    }

    fallo(app) {
                SettingsManager.setFallosDiv( SettingsManager.getFallosDiv() +1 );
    }


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

    this.puntos = 0;
/*
    if (localStorage.getItem('score')) {
        this.puntos = Number(localStorage.getItem('score'));
    } else {
        this.puntos = 0;
        localStorage.setItem("score", this.puntos);
    }
*/
    this.selectorCheck = '#check';

    this.controlResp = {};

    this.limits = options['limits'];

  }


  setAritmetica() {
      switch(SettingsManager.getOperacionActiva()) {
              case 0: this.operation = new Suma();
                      break;
              case 1: this.operation = new Resta();
                      break;
              case 2: this.operation = new Producto();
                       break;;
              case 3: this.operation = new Division();
                      break;
              default:this.operation = new Suma();
                      break;
      }
  }


  setOperation(oper) {
    this.operation = oper;
  }

  rollNumbers(){
      this.firstNumber = this.operation.getFirstNumber(this);
      this.secondNumber = this.operation.getSecondNumber(this);
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

   /*  this.controls['toolbar'] = {'reload': $('#reload'),
                                 'sound': $('#sound'),
                                 'facebbok': $('facebook')};
*/
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


     let config = $('#settings');
     let closeDialog = $('#close');

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
      loadOperatorToContainer(this.selectorOpe, this.settings['operatorsPath'], this.operation.getGrafico(this));
  }

  loadBarra(){
      loadOperatorToContainer(this.selectorBarra, this.settings['operatorsPath'], this.settings['barraName']);
  }

  loadAnswer(){
      loadNumberToContainer(this.respuestas, this.selectorResult, this.settings['numeros']);
  }

  loadScore(){
      $(this.selectorScore).html(this.operation.getPuntos());
  }

  initialize(){
      this.setAritmetica();
      this.rollNumbers();
      this.decomposeNumbers();
      this.operation.getPuntos();
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

     console.log(SettingsManager.isSoundActive());

     if ( SettingsManager.isSoundActive() ) {
        AndAud.playAudio(sonido);
     }
  }

}