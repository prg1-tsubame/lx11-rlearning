package prg1.sutton.bandit

import scala.util.Random

/**
 * 多腕バンディット問題の環境を表すクラス
 * 
 * @param bandit: 多腕バンディット問題
 * @param agents: エージェントのリスト
 * @param label: 環境のラベル
 **/
class Environment(val bandit: MultiArmedBandit, val agents: List[Agent], val label: String = "多腕Bandit") {

  /**
   * 多腕バンディット問題を解く
   * 
   * @param trials: 試行回数
   * @param experiments: 実験回数
   * @return scores: 報酬の平均値
   * @return optimal: 最適なアクションを選択した比率
   **/
  def run(trials: Int = 100, experiments: Int = 1): (Matrix, Matrix) = {
    val scores  = matrix(trials, agents.length)
    val optimal = matrix(trials, agents.length)

    for (_ <- 0 until experiments) {
      bandit.reset(); agents.foreach(_.reset())
      for (t <- 0 until trials) {
        for ((agent, i) <- agents.zipWithIndex) {
          val action = agent.choose()
          val (reward, isOptimal) = bandit.pull(action)
          agent.observe(reward)

          scores(t)(i) += reward
          if (isOptimal) optimal(t)(i) += 1
        }
      }
    }
    (scores.map(r => r.map(_ / experiments)), optimal.map(r => r.map(_ / experiments)))
  }
}