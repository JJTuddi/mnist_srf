import pandas as pd

csv = pd.read_csv("report.csv")

columns = list(csv.columns.copy())
columns[0] = ' '
letters = list(map(lambda x: chr(ord('a') + x[0]), enumerate(columns)))

table_rows = "\\\\\n\t\t\\hline\n".join(["\t\t" + " & ".join(map(str, list(csv.iloc[i]))) for i in range(len(csv.index)) ]) + "\\\\"

result = f'''
\\begin{{table}}
\t\\begin{{tabular}}{{{f'|{ "|".join(letters) }|'}}}
\t\t\\hline
\t\t{ " & ".join(columns) } \\\\
\t\t\\hline
\t\t\\hline
{ table_rows }
\t\t\\hline
\t\\end{{tabular}}
\t\\caption{{\\label{{tab:widgets}}An example table.}}
\\end{{table}}
'''

print(result)