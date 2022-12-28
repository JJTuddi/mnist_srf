import re
import pandas as pd

expression = re.compile(r"Model=(\w+) took (\d+.\d*) milliseconds to predict")


def extractFromLogs():
    result = {}
    with open("logs.log", "r") as f:
        for line in map(lambda x: x.strip(), f.read().split("\n")):
            find = re.findall(expression, line)
            if find is None or len(find) == 0:
                continue
            find = find[0]
            if find[0] not in result:
                result[find[0]] = []
            result[find[0]].append(float(find[1]))
    return result


if __name__ == '__main__':
    pd.DataFrame.from_dict(extractFromLogs()).describe().to_csv("report.csv")