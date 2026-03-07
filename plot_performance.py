import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import os

CSV_PATH = 'results/benchmark.csv'
OUTPUT_DIR = 'plots'

def gerar_graficos():
    if not os.path.exists(CSV_PATH):
        print(f"O Arquivo {CSV_PATH} não foi encontrado.")
        return

    if not os.path.exists(OUTPUT_DIR):
        os.makedirs(OUTPUT_DIR)

    df = pd.read_csv(CSV_PATH)
    sns.set_theme(style="whitegrid")

    for cenario in df['cenario'].unique():
        dados = df[df['cenario'] == cenario]

        # ── Gráfico de tempo ──────────────────────────────────────────────────
        plt.figure(figsize=(10, 6))
        sns.lineplot(data=dados, x='tamanho', y='mediaMs', hue='estrutura', marker='o', linewidth=2)
        plt.title(f'Tempo de Execução: {cenario}', fontsize=14, fontweight='bold')
        plt.xlabel('N (Elementos)')
        plt.ylabel('Tempo médio (ms)')
        plt.xscale('log')

        nome_tempo = f"{OUTPUT_DIR}/tempo_{cenario.lower().replace(' ', '_')}.png"
        plt.savefig(nome_tempo, bbox_inches='tight')
        plt.close()
        print(f"Gráfico gerado: {nome_tempo}")

        # ── Gráfico de memória (só se os dados existirem e não forem NaN) ─────
        if 'mediaMemoriaBytes' in dados.columns:
            # remove apenas NaN; mantém zeros para não perder pontos
            dados_mem = dados.dropna(subset=['mediaMemoriaBytes'])

            if not dados_mem.empty:
                plt.figure(figsize=(10, 6))
                sns.lineplot(data=dados_mem, x='tamanho', y='mediaMemoriaBytes', hue='estrutura', marker='o', linewidth=2)
                plt.title(f'Uso de Memória: {cenario}', fontsize=14, fontweight='bold')
                plt.xlabel('N (Elementos)')
                plt.ylabel('Memória média (bytes)')
                plt.xscale('log')

                nome_mem = f"{OUTPUT_DIR}/memoria_{cenario.lower().replace(' ', '_')}.png"
                plt.savefig(nome_mem, bbox_inches='tight')
                plt.close()
                print(f"Gráfico gerado: {nome_mem}")

if __name__ == "__main__":
    gerar_graficos()