all: Matriz.o
	gcc Matriz.o main.c -o main

Matriz.o: MATRIZ.h
	gcc -c Matriz.c

clean:
	rm -rf *.o
