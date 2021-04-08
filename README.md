# algebra-booleana
Algoritmo capaz de receber uma String representando uma expressão booleana e calcular seu resultado.

## Entrada

```console
2 0 0 and(not(A) , not(B))
```

A String passada como entrada para o algoritmo segue o seguinte formato:

* o 1° dígito é a quantidade de entradas que a expressão vai receber;
* os dígitos seguintes são as entradas da expressão;
* por fim a expressão em si. A expressão pode ter como entrada um dígito representado por uma letra maiúscula ou uma outra expressão. As letras vão de A até C, cada letra representa uma entrada segundo a ordem posicional, sendo assim A equivale a 1° entrada passada, B a 2° e C a 3°. São permitidas no máximo 3 entradas.

## Saída

```console
1
```

A saída produz um inteiro 1 ou 0 sendo 1 equivalente a verdadeiro e 0 para falso.
