#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

//Grupo: Rafael Nogueira Arruda, Davi Santos Sandim e Kaio Phelipe Silva Machado

void imprimeMenu(){
    system("clear");
    printf("Bem-vindo ao Sudoku!\n");
    printf("1. Novo Jogo\n");
    printf("0. Sair\n");
}


//Cria um sudoku apartir do arquivo com os jogos
void criaSudoku(int jogavel[][9],int resolvido[][9]){
    FILE *arq = fopen("sudokujogos","r");
    srand(time(NULL));
    char a = (rand() % 25)+65;
    char aux;
    while(feof(arq) == 0){
        fscanf(arq,"%c\n",&aux);
        if(a == aux){
        char jog[83];
        int n = 0;
        fgets(jog,83,arq); // le todo os numeros do sudoku resolvido 
        for(int i = 0;i<9;i++){
            for(int j = 0;j<9;j++){
                int conv = jog[n] - '0'; // Converte os char em inteiros
                resolvido[i][j] = conv; // Ordena eles na matriz 9x9
                n++;
            }
        }
        fgets(jog,82,arq); // le o sudoko jogavel e faz a mesma coisa que acima
        n = 0;
        for(int i = 0;i<9;i++){
            for(int j = 0;j<9;j++){
                int conv = jog[n] - '0';
                jogavel[i][j] = conv*10;
                n++;
            }
        }
        break;
        }
    }
    fclose(arq);
}

//Validar todos os numeros com base em comparacao
int verificarfinal(int jogavel[][9],int resolvido[][9]){
    for(int i = 0;i<9;i++){
        for(int j = 0;j<9;j++){
            if(jogavel[i][j] > 9){
                if(jogavel[i][j]/10 != resolvido[i][j]){
                    return 0;
                }
            }else if(jogavel[i][j] != resolvido[i][j]){
                return 0;
            }
        }
    }
    return 1;
}

void realizarJogada(int tabuleiro[9][9], int linha, int coluna,int numero){
    if(tabuleiro[linha][coluna] > 9){
        printf("--Esse é um espaço de Dica Inicial\n");
    }else{
        tabuleiro[linha][coluna] = numero;
    }
}

void removerJogada(int tabuleiro[9][9], int linha, int coluna) {
    if (tabuleiro[linha][coluna] != 0 || tabuleiro[linha][coluna] > 9) {
        if(tabuleiro[linha][coluna] > 9){
            printf("--Jogada não pode ser removida (Dica Inicial).\n");
        }else{
            tabuleiro[linha][coluna] = 0;
            printf("--Jogada removida com sucesso!\n");
        }
    } else {
        printf("--Nenhuma jogada para remover nessa posição!\n");
    }
}

//imprime o tabuleiro
void imprimirtabuleiro(int tabuleiro[][9],int *e){
    int aux = 1;// Colunas
    printf("   "); // Espaçamentos
    for(int i = 0;i<9;i++){ // Ajustar as colunas
        if(i == 2 || i == 5 || i == 8){
            printf(" %i  ",aux);
        }else{
            printf(" %i ",aux);
        }
        aux++;
    }printf("\n");
    printf("  ");
    for(int i = 0;i<10;i++){ // Primeira Separação
        printf("---");
    }printf("-\n");
    aux = 1;//Linhas
    for(int i = 0;i<9;i++){ // Ajustar as linhas
        printf("%i |",aux);
    for(int j = 0;j<9;j++){ // Caso 0, de não ter numero
        if(tabuleiro[i][j] == 0){
                if(j == 2 || j == 5 || j == 8){
                printf(" . |");
            }else{
                printf(" . ");
            }
        }else{ // Caso que ha numero
            if(j == 2 || j == 5 || j == 8){
                if(tabuleiro[i][j]>9){
                    printf(" %i |",tabuleiro[i][j]/10);
                }else{
                    printf(" %i |",tabuleiro[i][j]);
                }
            }else{
                if(tabuleiro[i][j]>9){
                    printf(" %i ",tabuleiro[i][j]/10);
                }else{
                    printf(" %i ",tabuleiro[i][j]);
                }
            }
        }
    }printf("\n");
    if(i == 2 || i == 5 ||i == 8){ // Formatacao da separacao dos subgrids (3x3)
        printf("  ");
        for(int j = 0;j<10;j++){
            printf("---");
        }printf("-\n");
    }
        aux++;
    }
    switch(*e){//printa os erros
        case 0:printf("Erro:[- - -]\n");
            break;
        case 1:printf("Erro:[X - -]\n");
            break;
        case 2:printf("Erro:[X X -]\n");
            break;
        case 3:printf("Erro:[X X X]\n");
        break;
    }
}

int main(){
    int opcao;
    imprimeMenu();
    printf("Opção: ");
    scanf("%i",&opcao);
    if(opcao == 0){
        printf("Saiu\n");
        return 1;
    }
    int sudokuJogavel[9][9],sudokuResolvido[9][9];
    int erro = 0;
    int linha,coluna,numero;
    criaSudoku(sudokuJogavel,sudokuResolvido);
    imprimirtabuleiro(sudokuJogavel,&erro);
    while(opcao != 0){
        if(erro > 2){ //Verifica o erro
            break;
        }
        printf("1 -> Jogada | 2-> Remover | 3-> Dica | 4 -> Verificar | 0 -> Saída");
        printf("\nOpção: ");
        scanf("%i",&opcao);
        if(opcao == 1){
            while(1){//Enquanto nao colocar um num valido
            printf("Linha (1-9), Coluna (1-9) e Número (1-9): ");
            scanf("%d %d %d", &linha, &coluna, &numero);
            if(numero >= 1 && numero <= 9 && coluna <= 9 && coluna >= 1 && linha <= 9 && linha>= 1){
                realizarJogada(sudokuJogavel,linha - 1,coluna - 1,numero);
                imprimirtabuleiro(sudokuJogavel,&erro);
                break;
            }else{
                printf("--Número invalido\n");
            }
            }
        }
        if(opcao == 2){
            while(1){
            printf("Remover na Linha (1-9) e Coluna (1-9): ");
            scanf("%d %d", &linha, &coluna);
            if(coluna <= 9 && coluna >= 1 && linha <= 9 && linha>= 1){
                removerJogada(sudokuJogavel,linha - 1,coluna - 1);
                imprimirtabuleiro(sudokuJogavel,&erro);
                break;
            }else{
                printf("--Número invalido\n");
            }
            }
        }
        if(opcao == 3){
            printf("Dica para Linha (1-9), Coluna (1-9): ");//Coloca o num certo na posicao desejada
            scanf("%d %d", &linha, &coluna);
            if(coluna <= 9 && coluna >= 1 && linha <= 9 && linha>= 1){
                sudokuJogavel[linha-1][coluna-1] = sudokuResolvido[linha-1][coluna-1];
                imprimirtabuleiro(sudokuJogavel,&erro);
            }else{
                imprimirtabuleiro(sudokuJogavel,&erro);
                printf("--Número invalido\n");
            }
        }
        if(opcao == 4){//Verifica se o jogo terminou e está certo
            if(verificarfinal(sudokuJogavel,sudokuResolvido)){
                imprimirtabuleiro(sudokuJogavel,&erro);
                printf("Você ganhou!\n");
                return 1;
            }else{
                erro++;
                imprimirtabuleiro(sudokuJogavel,&erro);
                printf("Ainda não está completo ou há erros\n");
                
            }
        }
    }
    if(erro > 2){
        printf("--Você perdeu por erros.\n");
    }
}