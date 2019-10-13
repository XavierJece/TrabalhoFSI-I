const tabuleiroArrayPixel = []
const tabuleiroLinha = document.getElementById('ordemLinhas').value
const tabuleiroColuna = document.getElementById('ordemColunas').value

const posicaoLinhaInicio = document.getElementById('posicaoInicioLinhas').value
const posicaoColunaInicio = document.getElementById('posicaoInicioColunas').value

const posicaoLinhaObjetivo = document.getElementById('posicaoObjetivoLinhas').value
const posicaoColunaObjetivo = document.getElementById('posicaoObjetivoColunas').value

const ArrayLegendaColor =  [
    {"r":0, "g":0, "b":0, "a":.3},      //Solido
    {"r":235, "g":168, "b":24, "a": 1}, // Arenoso 235, 168, 24
    {"r":0, "g":0, "b":0, "a":.6},      //Rochoso
    {"r":27, "g":114, "b":5, "a":1},    //Pantano 27, 114, 5, 1
]

const ArrayPowerUps = [
    '',                                //Sem nada
    '<i class="fas fa-female"></i>',    //Com Prejuizo
    '<i class="fas fa-book-reader"></i>'     //COm lucro
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
    const totalDePosicoes = tabuleiroLinha * tabuleiroColuna
    const tempoParaPowerUp = 0;

    for (let i = 0; i < totalDePosicoes; i++) {
        custoPosicao = Math.floor(Math.random() * (4)) //Custo de posição de Forma Random

        // Verificação para Saber se ainda tem powerUps para colocar
        powerUps = Math.floor(Math.random() * (10)) //Custo de posição de Forma Random
        if( (powerUps >= 0) && (powerUps <= 5)){
            powerUps = 0
        }else if((powerUps > 5) && (powerUps <= 6)){
            powerUps = 1
        }else{
            powerUps = 2
        }

        tabuleiroArrayPixel[i] = {
            "powerUps": powerUps, 
            "custoTerreno": custoPosicao
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
            const custoPosicao = tabuleiroArrayPixel[indiceTabuleiro].custoTerreno
            if (debug) {
                html += '<td>'
                html += `<div class="indiceTabuleiro">${indiceTabuleiro}</div>`
                html += custoPosicao
                html += '</td>'   
            } else {
                if((row == posicaoLinhaInicio-1) && (column == posicaoColunaInicio-1)){
                    html += '<td>'
                    html += '<i class="fas fa-male"></i>'
                    html += '</td>'
                }else if((row == posicaoLinhaObjetivo-1) && (column == posicaoColunaObjetivo-1)){
                    html += '<td>'
                    html += '<i class="fas fa-user-graduate"></i>'
                    html += '</td>'
                }else{
                    const color = ArrayLegendaColor[custoPosicao]
                    // console.log(color)
                    const colorString = `${color.r},${color.g},${color.b},${color.a}`
                    const icon = tabuleiroArrayPixel[indiceTabuleiro].powerUps
                    const iconString = ArrayPowerUps[icon]
                    
                    // <i class="fas fa-running"></i>
                    html += `<td style="background-color: rgba(${colorString});">`
                    html += iconString
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