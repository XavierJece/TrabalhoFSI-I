const tabuleiroArrayPixel = []
/*
    custoTerreno    => init
    corTerreno      => string
    inicio          => boolean
    fim             => boolean
    powerUpIcon     => string
    powerUpValue    => int

*/

const tabuleiroLinha = document.getElementById('ordemLinhas').value
const tabuleiroColuna = document.getElementById('ordemColunas').value

const posicaoLinhaInicio = document.getElementById('posicaoInicioLinhas').value
const posicaoColunaInicio = document.getElementById('posicaoInicioColunas').value

const posicaoLinhaObjetivo = document.getElementById('posicaoObjetivoLinhas').value
const posicaoColunaObjetivo = document.getElementById('posicaoObjetivoColunas').value

const ArrayLegendaColor =  [
    {"r":0, "g":0, "b":0, "a":.3, "value": 1},      //Solido
    {"r":235, "g":168, "b":24, "a": 1, "value": 4}, // Arenoso 235, 168, 24
    {"r":0, "g":0, "b":0, "a":.6, "value": 10},      //Rochoso
    {"r":27, "g":114, "b":5, "a":1, "value": 20},    //Pantano 27, 114, 5, 1
]

const ArrayPowerUps = [
    {"icon": '', "value": 0},                                      //Sem nada
    {"icon": '<i class="fas fa-female"></i>', "value": -5},         //Com Prejuizo
    {"icon": '<i class="fas fa-book-reader"></i>', "value": 5}     //COm lucro
]

function star(){
    if (verificacaoDados()) {
        alert(`Dados Iválidos!!\n
        Inicio L: ${posicaoLinhaInicio} \n
        Inicio C: ${posicaoColunaInicio} \n
        Objetivo L: ${posicaoLinhaObjetivo} \n
        Objetivo C: ${posicaoColunaObjetivo} \n
        Objetivo L: ${posicaoLinhaObjetivo} \n
        Tabuleiro L: ${tabuleiroLinha} \n
        Tabuleiro C: ${tabuleiroColuna}
        `)
    } else {
        criarEstrutura()
        console.log(JSON.parse(JSON.stringify(tabuleiroArrayPixel)));
        renderTabuleiro()
    }
}

function verificacaoDados(){
    // Tabueiro
    if((tabuleiroColuna <= 0) || (tabuleiroLinha <= 0)){
        return false
    }else if((posicaoColunaInicio <= 0) || (posicaoLinhaInicio <= 0)){
        return false
    }else if((posicaoColunaInicio >= tabuleiroColuna) || (posicaoLinhaInicio >= tabuleiroLinha)){
        return false
    }else if((posicaoColunaObjetivo <= 0) || (posicaoLinhaObjetivo <= 0)){
        return false
    }else if((posicaoColunaObjetivo >= tabuleiroColuna) || (posicaoLinhaObjetivo >= tabuleiroLinha)){
        return false
    }else if((posicaoColunaObjetivo == posicaoColunaInicio) && (posicaoLinhaObjetivo == posicaoLinhaInicio)){
        return false
    }

    return true
}

function criarEstrutura(){
    i = 0

    for (let row = 0; row < tabuleiroLinha; row++) {
        for (let column = 0; column < tabuleiroColuna; column++) {
            //Custo de posição de Forma Random
            custoPosicao = Math.floor(Math.random() * (4))
            //Definindo por do nó apartir do custo
            const color = ArrayLegendaColor[custoPosicao]
            const colorString = `${color.r},${color.g},${color.b},${color.a}`

            
            // Veridicando se a posição é a inicial
            if((row == posicaoLinhaInicio-1) && (column == posicaoColunaInicio-1)){
                inicio = true    
            }else{
                inicio = false
            }

            // Verificando se a posição é a final
            if((row == posicaoLinhaObjetivo-1) && (column == posicaoColunaObjetivo-1)){
                fim = true    
            }else{
                fim = false
            }

            // Verificação para Saber se ainda tem powerUps para colocar
            powerUps = Math.floor(Math.random() * (10)) //Custo de posição de Forma Random
            if( (powerUps >= 0) && (powerUps <= 7)){
                powerUps = 0
            }else if(powerUps == 8){
                powerUps = 1
            }else{
                powerUps = 2
            }
            
            // Criando no
            tabuleiroArrayPixel[i] = {
                "custoTerreno": ArrayLegendaColor[custoPosicao].value,    
                "corTerreno": colorString,      
                "inicio": inicio,          
                "fim": fim,             
                "powerUpIcon": ArrayPowerUps[powerUps].icon,     
                "powerUpValue" : ArrayPowerUps[powerUps].value   
            }
            i = i + 1
        }
        
    }
}

function renderTabuleiro(){
    debug = false
    let html = '<table cellpaddig=0 cellspacing=0>'
    
    for (let row = 0; row < tabuleiroLinha; row++) {
        html += '<tr>'

        for (let column = 0; column < tabuleiroColuna; column++) {
            const indiceTabuleiro = column + (tabuleiroColuna * row)

            if (debug) {
                html += '<td>'
                html += `<div class="indiceTabuleiro">${indiceTabuleiro}</div>`
                html += tabuleiroArrayPixel[indiceTabuleiro].custoTerreno
                html += '</td>'   
            } else {
                if(tabuleiroArrayPixel[indiceTabuleiro].inicio){
                    html += '<td>'
                    html += '<i class="fas fa-male"></i>'
                    html += '</td>'
                }else if(tabuleiroArrayPixel[indiceTabuleiro].fim){
                    html += '<td>'
                    html += '<i class="fas fa-user-graduate"></i>'
                    html += '</td>'
                }else{
                    const colorString = tabuleiroArrayPixel[indiceTabuleiro].corTerreno
                    
                    // <i class="fas fa-running"></i>
                    html += `<td style="background-color: rgba(${colorString});">`
                    html += tabuleiroArrayPixel[indiceTabuleiro].powerUpIcon
                    html += '</td>'
                }
            }
        }

        html += '</tr>'
    }

    html += '<table>'

    document.querySelector('#tabuleiro').innerHTML = html
}

star()