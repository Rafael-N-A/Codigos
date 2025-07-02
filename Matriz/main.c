#include "MATRIZ.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>


int main(){
    Matriz* matrizA = (Matriz*)malloc(sizeof(Matriz));
    matrizA->c = 3;
    matrizA->l = 3;
    criaMatriz(matrizA);
    int acc = 1;
    for(int i = 0;i<matrizA->c*matrizA->l;i++){
        matrizA->data[i] = acc;
        acc = acc+2;
    }
    imprimeMatriz(matrizA);
    printf("Valor na [3,2] = %i\n",consultaElementoNaMatriz(matrizA,2,1));
    alteraElementoNaMatriz(matrizA,2,1,1);
    printf("Valor na [3,2] = %i\n",consultaElementoNaMatriz(matrizA,2,1));
    
    Matriz* transposta = getTransporta(matrizA);
    imprimeMatriz(transposta);
    Matriz* resultadoSoma = somaMatriz(matrizA,transposta);
    imprimeMatriz(resultadoSoma);
    
    apagaMatriz(resultadoSoma);
    apagaMatriz(transposta);
    apagaMatriz(matrizA);
}
