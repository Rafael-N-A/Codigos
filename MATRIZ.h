#ifndef MATRIZ_H
#define MATRIZ_H

typedef struct {
    int l,c;
    int *data;
}Matriz;

//Aloca uma nova matriz
void criaMatriz(Matriz *m);

//Desaloca a matriz
void apagaMatriz(Matriz *m);

//Insere novo valor na posição indicada
void alteraElementoNaMatriz(Matriz *m, int lin, int col, int valor);

//Retorna o valor da Matriz na posição indicada
int consultaElementoNaMatriz(Matriz *m,int lin,int col);

//Imprime a Matriz
void imprimeMatriz(Matriz *m);

//Retorna a transposta da matriz
Matriz* getTransporta(Matriz *m);

//Retorna a soma das matrizes
Matriz* somaMatriz(Matriz *m1, Matriz *m2);

#endif