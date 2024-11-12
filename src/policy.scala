package prg1.sutton.bandit

import scala.util.Random

/**
  * 行動選択方策を表す抽象クラス
  */
abstract class Policy {
  /**
    * 次のアクションを選択する
    *
    * @return 選択されたアクションのインデックス
    */
  def choose(agent: Agent): Int

  override def toString: String = "Generic Policy"
}

/**
  * 多腕バンディット問題におけるε-Greedy 戦略は、アクションの選択にあたって普段は価値推定値 (valueEstimates) に沿って最も価値が高いと評価されているものを選択する。しかし、一定の小さな確率（ε）でギャンブルをし、無作為に選択する。
  * 
  * @param epsilon: ε-Greedy 戦略の確率
  */
class EpsilonGreedyPolicy(val epsilon: Double) extends Policy {
  override def toString: String = s"ε-Greedy (ε=$epsilon)"

  /**
    * ε-Greedy 戦略に基づいてアクションを選択する。
    * 
    * @param agent: エージェント
    * @return action: 選択されたアクションのインデックス
    */
  def choose(agent: Agent): Int = {

    if (Random.nextDouble() < epsilon) {
      Random.nextInt(agent.valueEstimates.length)
    } else {
      val maxValue = agent.valueEstimates.max

      // 価値推定値が最大となるアクションのインデックスを取得。一意に定まらないのですべて収集してから無作為にひとつを選択する。
      val bestActions = agent.valueEstimates.zipWithIndex.collect {
        case (value, index) if value == maxValue => index
      }
      bestActions(Random.nextInt(bestActions.length))  // bestActions から無作為にひとつを選択
    }
  }
}

/**
  * 貪欲 (greedy) 法は ε = 0、すなわちギャンブルを行わずに常に価値推定値が最大となるアクションを選択する。
  */
class GreedyPolicy extends EpsilonGreedyPolicy(0.0) {
  override def toString: String = "Greedy"
}

/**
  * ランダム (random) 法は ε = 1、すなわち完全にいかれたギャンブラー。
  */
class RandomPolicy extends EpsilonGreedyPolicy(1.0) {
  override def toString: String = "Random"
}
