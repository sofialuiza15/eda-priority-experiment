# EDA — Experimento: Fila de Prioridade

Benchmark comparando duas implementações de fila de prioridade:
- **PQHeap** — baseada em heap binário
- **PQTreeMap** — baseada em árvore (TreeMap)

Os testes medem tempo de execução e uso de memória em cinco cenários, com três tamanhos de entrada (1.000, 10.000 e 100.000 elementos).

---

## Pré-requisitos

### Java
- Java 17 ou superior
- Gradle (via wrapper `./gradlew`, já incluído no projeto)

### Python
- Python 3.8 ou superior
- Dependências listadas em `requirements.txt`

---

## Instalação

### 1. Dependências Python

```bash
# Crie e ative um ambiente virtual
python3 -m venv venv
source venv/bin/activate

# Instale as dependências
pip install -r requirements.txt
```

> Se receber erro de `externally-managed-environment`, instale o venv primeiro:
> ```bash
> sudo apt install python3.12-venv
> ```

### 2. Dependências Java

As dependências Java (incluindo JOL para medição de memória) são baixadas automaticamente pelo Gradle na primeira execução.

---

## Como rodar

### 1. Executar os benchmarks

```bash
./gradlew clean run
```

Isso compila o projeto, roda todos os testes e exporta os resultados para:
- `results/benchmark.csv`
- `results/benchmark.json`

### 2. Gerar os gráficos

Com o ambiente virtual ativado:

```bash
source venv/bin/activate
python3 plot.py
```

Os gráficos são salvos na pasta `plots/`.

---

## Estrutura dos testes

| Teste | Descrição |
|---|---|
| Inserção Pura | Insere n elementos aleatórios. Mede tempo e memória. |
| Remoção Pura | Insere n elementos e remove todos. Mede tempo. |
| Peek Puro | Consulta o topo da fila 100.000 vezes. Mede tempo. |
| Operações Mistas | Alterna insert e remove aleatoriamente. Mede tempo. |
| Carga Pesada | 50% insert, 30% remove, 20% peek. Mede tempo. |

Cada teste usa:
- **5 repetições de warmup** (sem medição) para aquecer a JVM
- **10 repetições medidas** para cálculo de média e desvio padrão

---

## Configuração

As configurações dos experimentos ficam no topo de `src/main/java/tests/Main.java`:

```java
static final int[] TAMANHOS      = { 1_000, 10_000, 100_000 };
static final int   WARMUP        = 5;
static final int   REPETICOES    = 10;
static final int   REPETICOES_PEEK = 100_000;
```

---

## Observações

- Ainda falta consertar o peek.
- A medição de memória usa [JOL (Java Object Layout)](https://openjdk.org/projects/code-tools/jol/), que percorre o grafo completo de objetos da estrutura para calcular o tamanho real.
- O benchmark é projetado para minimizar interferências externas, mas resultados podem variar dependendo do ambiente de execução (CPU, RAM, carga do sistema).
- Resultados são exportados em CSV e JSON para facilitar análise posterior.
- Pode aparecer um aviso `WARNING: Unable to attach Serviceability Agent` durante a execução — é inofensivo e não afeta os resultados.