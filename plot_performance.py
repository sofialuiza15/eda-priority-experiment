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

    for teste in df['testName'].unique():
        plt.figure(figsize=(10, 6))
        dados = df[df['testName'] == teste]
        
        sns.lineplot(data=dados, x='datasize', y='msSec', hue='structure', marker='o', linewidth=2)
        
        plt.title(f'Desempenho: {teste}', fontsize=14, fontweight='bold')
        plt.xlabel('N (Elementos)')
        plt.ylabel('Tempo (ms)')
        plt.xscale('log') # Escala logarítmica para N
        
        nome_img = f"{OUTPUT_DIR}/resultado_{teste.lower().replace(' ', '_')}.png"
        plt.savefig(nome_img, bbox_inches='tight')
        plt.close()
        print(f"Gráfico gerado: {nome_img}")

if __name__ == "__main__":
    gerar_graficos()