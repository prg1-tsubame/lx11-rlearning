# 強化学習器を用いた "k-armed bandit" 問題の実験

1. `sbt` を起動して `run` コマンドを実行。3種の単純な評価戦略を比較する。
    1. Greedy戦略: ε=0
    1. ε-greedy戦略: ε=0.01
    1. ε-greedy戦略: ε=0.1
    1. Random戦略: ε=1
   結果は `result` ディレクトリに保存される。

2. `bandits.ipynb` を実行して結果を分析する。この実行には Jupyter Lab, Pandas, Plotly が必要。これらを含んだ Python 環境を `venv/` に用意するために、以下のコマンドを実行する。
    ~~~
    python3 -m venv venv
    source venv/bin/activate
    pip install -r requirements.txt
    jupyter lab
    ~~~

    最後のコマンドがブラウザの上で Jupyter Lab （Python のプログラミング環境）を開く。ここで `bandits.ipynb` を開き、**Run All** を実行することで結果を確認できるはず。