package prg1.sutton.bandit

import scala.util.Random
import scala.collection.mutable.ArraySeq

/**
 * 抽象クラス Agent は、k-選択問題のエージェントの基本的な振る舞いを定義している。
 * 
 * @param bandit: 多腕バンディット
 * @param policy: 行動選択方策
 * @param prior: 価値推定値の初期値
 **/
class Agent(val bandit: MultiArmedBandit, val policy: Policy, val prior: Double = 0.0) {

  /** 選択可能なアクションの数 */
  val k = bandit.k

  /**
    * アクションの価値推定値
    */
  protected var _valueEstimates: ArraySeq[Double] = ArraySeq.fill(k)(prior)

  /** アクションの試行回数 */
  var actionAttempts: ArraySeq[Int] = ArraySeq.fill(k)(0)

  /** エージェントの試行回数 */
  var t: Int = 0

  /** 最後に選択したアクション */
  var lastAction: Option[Int] = None

  /** エージェントの状態を初期化する */
  def reset(): Unit = {
    _valueEstimates = ArraySeq.fill(k)(prior)
    actionAttempts = ArraySeq.fill(k)(0)
    lastAction = None
    t = 0
  }

  /** policy にしたがって次のアクションを選択する */
  def choose(): Int = {
    val action = policy.choose(this)
    lastAction = Some(action)
    action
  }

  /** 報酬を観測し、価値推定値を更新する
   * @param reward: 報酬
   */
  def observe(reward: Double): Unit = {
    lastAction match {
      case Some(action) => {
        actionAttempts(action) += 1
        val g = 1.0 / actionAttempts(action)  // 報酬の割引率
        _valueEstimates(action) += g * (reward - _valueEstimates(action))
        t += 1
      }
      case None => ()
    }
  }

  /** アクションの価値推定値を取得する */
  def valueEstimates: ArraySeq[Double] = _valueEstimates
}
