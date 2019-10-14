const tabuleiroArrayPixel = []
const arrayCaminho = []
/*
    custoTerreno    => init
    corTerreno      => string
    inicio          => boolean
    fim             => boolean
    powerUpIcon     => string
    powerUpValue    => int
    heuristica      => int

*/

const rowTabuleiro = document.getElementById('ordemLinhas').value
const columnTabuleiro = document.getElementById('ordemColunas').value

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
    {"icon": '<i class="fas fa-female"></i>', "value": 5},         //Com Prejuizo
    {"icon": '<i class="fas fa-book-reader"></i>', "value": -5}     //COm lucro
]

function star(){
    if (!verificacaoDados()) {
        alert(`Dados Iválidos!!\n
        Inicio L: ${posicaoLinhaInicio}
        Inicio C: ${posicaoColunaInicio} \n
        Objetivo L: ${posicaoLinhaObjetivo}
        Objetivo C: ${posicaoColunaObjetivo} \n
        Tabuleiro L: ${rowTabuleiro}
        Tabuleiro C: ${columnTabuleiro}
        `)
    } else {
        criarEstrutura()
        console.log(JSON.parse(JSON.stringify(tabuleiroArrayPixel)));
        renderTabuleiro()

        busca()

        console.log(JSON.parse(JSON.stringify(arrayCaminho)))
    }
}

function verificacaoDados(){
    // Tabueiro
    if((columnTabuleiro <= 0) || (rowTabuleiro <= 0)){
        // alert(`IF 1`)
        return false
    }else if((posicaoColunaInicio <= 0) || (posicaoLinhaInicio <= 0)){
        // alert(`IF 2`)
        return false
    }else if((posicaoColunaInicio-1 >= columnTabuleiro) || (posicaoLinhaInicio-1 >= rowTabuleiro)){
        // alert(`IF 3`)
        return false
    }else if((posicaoColunaObjetivo <= 0) || (posicaoLinhaObjetivo <= 0)){
        // alert(`IF 4`)
        return false
    }else if((posicaoColunaObjetivo-1 >= columnTabuleiro) || (posicaoLinhaObjetivo-1 >= rowTabuleiro)){
        // alert(`IF 5`)
        return false
    }else if((posicaoColunaObjetivo == posicaoColunaInicio) && (posicaoLinhaObjetivo == posicaoLinhaInicio)){
        // alert(`IF 6`)
        return false
    }

    return true
}

function criarEstrutura(){
    i = 0

    for (let row = 0; row < rowTabuleiro; row++) {
        for (let column = 0; column < columnTabuleiro; column++) {
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

            // Definindo Heuristica
            heuristica = definirHeuristica(row, column, posicaoLinhaObjetivo, posicaoColunaObjetivo)
            
            // Criando no
            tabuleiroArrayPixel[i] = {
                "heuristica": heuristica,
                "custoTerreno": ArrayLegendaColor[custoPosicao].value,    
                "corTerreno": colorString,      
                "inicio": inicio,          
                "fim": fim,             
                "icon": ArrayPowerUps[powerUps].icon,     
                "powerUpValue": ArrayPowerUps[powerUps].value,
                "posicao": i
            }
            i = i + 1
        }
        
    }
}
 
function definirHeuristica(rowAtual, colAtual, rowObjetivo, colObjetivo){
    
    // Pitágoras
    let heuristica = Math.pow(((rowObjetivo - 1) - rowAtual), 2) +  Math.pow(((colObjetivo - 1) - colAtual), 2)
    // heuristica =  heuristica +   ((rowObjetivo - 1) - rowAtual) +  ((colObjetivo - 1) - colAtual)
    // heuristica = heuristica - (rowObjetivo / rowAtual) + (colObjetivo / colAtual)
    // heuristica = heuristica + (rowAtual / colAtual) + (colAtual / rowAtual)
    heuristica =Math.floor(heuristica)
    heuristica = Math.abs(heuristica)

    return heuristica
}

function renderTabuleiro(){
    debug = false
    let html = '<table cellpaddig=0 cellspacing=0>'
    
    for (let row = 0; row < rowTabuleiro; row++) {
        html += '<tr>'

        for (let column = 0; column < columnTabuleiro; column++) {
            const indiceTabuleiro = column + (columnTabuleiro * row)

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
                    // html += tabuleiroArrayPixel[indiceTabuleiro].icon
                    // html += tabuleiroArrayPixel[indiceTabuleiro].heuristica
                    html += tabuleiroArrayPixel[indiceTabuleiro].posicao
                    html += '</td>'
                }
            }
        }

        html += '</tr>'
    }

    html += '<table>'

    document.querySelector('#tabuleiro').innerHTML = html
}

// ********** Algoritimos de Busca ********************

// ---------- A* ----------
function busca(){
    
    let noInicio = null
    for (let i = 0; i < tabuleiroArrayPixel.length; i++) {
        
        if(tabuleiroArrayPixel[i].inicio){
            noInicio = tabuleiroArrayPixel[i]
        }
    }

    estrela(noInicio, 0)
}


function estrela(no, posicao){
    arrayCaminho[posicao] = no

    if(no.fim || (posicao == 3)){
        alert('ENCONTROU')
        return true
    }else{
        // definindo Visinhos
        if(no.posicao - columnTabuleiro >= 0){
            noCima = tabuleiroArrayPixel[Math.floor(no.posicao - columnTabuleiro)]
            console.log("Teste", JSON.parse(JSON.stringify(noCima)))

            // Calculando a F(x) (função custo) do proximo ponto
            valorParaCima = noCima.custoTerreno + noCima.heuristica + noCima.powerUpValue
        }else{
            noCima = null
            valorParaCima = 100000
        }

        if(no.posicao + columnTabuleiro < rowTabuleiro){
            noBaixo = tabuleiroArrayPixel[Math.floor(no.posicao + columnTabuleiro)]

            // Calculando a F(x) (função custo) do proximo ponto
            valorParaBaixo = noBaixo.custoTerreno + noBaixo.heuristica + noBaixo.powerUpValue
        }else{
            noBaixo = null
            valorParaBaixo = 100000
        }

        if(
            (no.posicao - 1 >= 0) &&
            ((no.posicao % 10) != 0)
        ){
            noEsquerda = tabuleiroArrayPixel[Math.floor(no.posicao - 1)]
            console.log("noEsquerda ", JSON.parse(JSON.stringify(noEsquerda)))

            // Calculando a F(x) (função custo) do proximo ponto
            valorParaEsquerna = noEsquerda.custoTerreno + noEsquerda.heuristica + noEsquerda.powerUpValue
        }else{
            noEsquerda = null
            valorParaEsquerna = 100000
        }

        if(
            (no.posicao + 1 < columnTabuleiro) &&
            (((no.posicao + 1) % 10) != 0)
        ){
            noDireita = tabuleiroArrayPixel[Math.floor(no.posicao + 1)]
            
            // Calculando a F(x) (função custo) do proximo ponto
            valorParaDireita = noDireita.custoTerreno + noDireita.heuristica + noDireita.powerUpValue
        }else{
            noDireita = null
            valorParaDireita = 100000
        }

        // verificando para qual deve caminhar

        console.log("cima ", valorParaCima)
        console.log("baixo ", valorParaBaixo)
        console.log("esquerda ", valorParaEsquerna)
        console.log("direita ", valorParaDireita)
        console.log("---")

        if(
            (valorParaCima < valorParaBaixo) &&
            (valorParaCima < valorParaEsquerna) &&
            (valorParaCima < valorParaDireita)
        ){
            estrela(noCima, (posicao + 1))
        }else if(
            (valorParaBaixo < valorParaCima) &&
            (valorParaBaixo < valorParaEsquerna) &&
            (valorParaBaixo < valorParaDireita)
        ){
            estrela(noBaixo, (posicao + 1))
        }else if(
            (valorParaEsquerna < valorParaCima) &&
            (valorParaEsquerna < valorParaBaixo) &&
            (valorParaEsquerna < valorParaDireita)
        ){
            estrela(noEsquerda, (posicao + 1))
            
        }else{
            estrela(noDireita, (posicao + 1))
        }

        return true
        
    }

    // console.log(JSON.parse(JSON.stringify(noCima)))
    // console.log(JSON.parse(JSON.stringify(noBaixo)))
    // console.log(JSON.parse(JSON.stringify(noEsquerda)))
    // console.log(JSON.parse(JSON.stringify(noDireita)))
}


// ********** START ********************
star()