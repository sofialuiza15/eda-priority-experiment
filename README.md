# eda-priority-experiment

Considerando as propriedades de ordenação e acesso a elementos caracterizados na disciplina, o projeto tem como objetivo comparar o desempenho e a eficiência de memória entre a fila de prioridade (Heap Binária) e a Árvore de Busca (TreeMap) na linguagem Java. Explorando o comportamento dessas estruturas em cenários de grande fluxo de dados e operações de busca e remoção, onde a eficiência de cada uma será analisada e posta à prova.

As estruturas analisadas serão:
 - Heap Binária (PriorityQueue)
 - TreeMap (Árvore PV)

**Objetivo**

	Temos como objetivo central do nosso projeto, realizar uma análise comparativa do desempenho e eficiência de memória entre TreeMap e Heap com fila de prioridade. A proposta busca analisar experimentalmente as duas estruturas de dados sob efeitos de grandes fluxos de informações, como entradas grandes, pequenas, operações de inserção e remoção, e verificação do próximo elemento da fila a sair sem removê-lo (peek). Com isso, pretendemos que seja possível identificar particularidades, como as vantagens e desvantagens, e tempo médio de resposta para operações. A realização será avaliada por meio de gráficos e métricas reais em nanosegundos (visto que, operações em Java são muito rápidas). A comparação é objetivada em visualizar quais cenários essas estruturas se superam em eficiência.

**Metodologia** 

 1- Implementação das estruturas
 
  Para este projeto utilizaremos as implementações nativas da Java Collection(TreeMap e PriorityQueue) com o objetivo de utilizar estruturas já otimizadas e testadas que trarão melhor confiabilidade para os resultados. A estrutura Heap já está implementada na própria PriorityQueue, eliminando a necessidade de uma implementação própria.

 2- Definição dos cenários

  Os testes foram organizados em cinco cenários principais: inserções puras (simulando carregamento inicial de dados), remoções puras (esvaziamento completo da estrutura), operações mistas com proporções equilibradas (uso comum do sistema), operações de peek (consulta sem remoção) e carga totalmente aleatória (simulando situações de stress). As entradas serão geradas aleatoriamente para evitar vieses e garantir imparcialidade, utilizando tamanhos em progressão logarítmica — 1.000, 10.000, 100.000 e 1.000.000 de elementos — permitindo observar desde pequenos volumes até testes de escalabilidade e possíveis gargalos, além de facilitar a visualização em gráficos. Cada teste será repetido cinco vezes, número considerado mínimo para confiabilidade estatística, possibilitando calcular média e desvio padrão, reduzir impactos de variações da máquina virtual e identificar possíveis outliers, mantendo equilíbrio entre precisão e tempo de execução.
 
 3- Definição de métricas 

  Estabelecemos métricas comumente utilizadas na avaliação de algoritmos e estruturas, a fim de que sejam classificadas para determinados usos, de modo que tenham aproveitamento máximo. Como métrica de comparação entre as estruturas (heap e treemap) utilizadas como fila de prioridade, determinamos medir o desempenho em determinados cenários, o tempo de execução em operações específicas e o uso de memória por ambas as estruturas. Para que essas métricas sejam avaliadas corretamente, teremos métodos implementados para salvar, medir e comparar os dados recolhidos
