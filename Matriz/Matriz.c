#include "MATRIZ.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void criaMatriz(Matriz *m){
    m->data = (int*)malloc(m->l*m->c*sizeof(int));
}

void apagaMatriz(Matriz *m){
    free(m->data);
    free(m);
}

void alteraElementoNaMatriz(Matriz *m, int lin, int col, int valor){
    int index = lin * m->c + col;
    m->data[index] = valor;
}

int consultaElementoNaMatriz(Matriz *m,int lin,int col){
    int index = lin * m->c + col;
    return m->data[index];
}

void imprimeMatriz(Matriz *m){
    for(int i = 0;i < m->c*m->l;i++){
        printf("%i ",m->data[i]);
        i++;
        if(i%m->c == 0){
            printf("\n");
        }
        i--;
    }printf("\n");
}

Matriz* getTransporta(Matriz *m){
    Matriz* res = (Matriz*)malloc(sizeof(Matriz));
    res->c = m->l; 
    res->l = m->c;
    res->data = (int*)malloc(res->c*res->l*sizeof(int));
    for(int lin = 0;lin<res->l;lin++){
        for(int col = 0;col<res->c;col++){
            int index = lin*res->c + col;
            res->data[index] = m->data[col*res->l+lin];
        }
    }
    return res;
}

Matriz* somaMatriz(Matriz *m1, Matriz *m2){
    if(m1->c != m2->c || m1->l != m2->l){
        printf("As matrizes não são de mesma ordem.");
        return NULL;
    }
    Matriz* res = (Matriz*)malloc(sizeof(Matriz));
    res->c = m1->c; 
    res->l = m1->l;
    res->data = (int*)malloc(res->c*res->l*sizeof(int));
    for(int lin = 0;lin<m1->c;lin++){
        for(int col = 0;col<m1->l;col++){
            int index = lin*res->c + col;
            res->data[index] = m1->data[index] + m2->data[index];
        }
    }
    return res;
}
