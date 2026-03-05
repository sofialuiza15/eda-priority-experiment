import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import os

# 1. Carregar os dados ignorando espaços extras
df = pd.read_csv('results/benchmark.csv', skipinitialspace=True)

# 2. GARANTIR QUE OS DADOS SÃO NÚMEROS (Resolve o ziguezague)
df['datasize'] = pd.to_numeric(df['datasize'], errors='coerce')
df['msSec'] = pd.to_numeric(df['msSec'], errors='coerce')
df = df.dropna() # Remove qualquer linha com erro

# 3. ORDENAR (Vital para as linhas não cruzarem a tela)
df = df.sort_values(by=['testName', 'structure', 'datasize'])

# 4. Criar pasta de gráficos
if not os.path.exists('plots'):
    os.makedirs('plots')

# 5. Configurar o estilo
sns.set_theme(style="whitegrid")
testes = df['testName'].unique()

for teste in testes:
    plt.figure(figsize=(10, 6))
    data_teste = df[df['testName'] == teste]
    
    # O segredo: hue='structure' separa por cor, x='datasize' ordena o eixo X
    sns.lineplot(
        data=data_teste, 
        x='datasize', 
        y='msSec', 
        hue='structure', 
        style='structure', # Estilo diferente para cada linha
        markers=True, 
        dashes=False,
        linewidth=2.5
    )
    
    plt.title(f'Performance: {teste}', fontsize=14)
    plt.xlabel('Tamanho da Amostra (N)')
    plt.ylabel('Tempo (ms)')
    
    # Escala logarítmica para ver bem de 1k até 1M
    plt.xscale('log') 
    
    plt.savefig(f"plots/resultado_{teste.lower().replace(' ', '_')}.png")
    plt.close()

print("🚀 Gráficos corrigidos! Agora as linhas devem estar separadas por estrutura.")