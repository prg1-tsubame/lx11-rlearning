package prg1.sutton.bandit

import scala.util.Random

/**
  * 多腕バンディット問題の環境を表すクラス
  * 
  * @param k: アクションの数
  */
class MultiArmedBandit(val k: Int) {
  /** アクションの真の価値 */
  var actionValues: Array[Double] = Array.fill(k)(0.0)
  
  /** 最適なアクションのインデックス */
  var optimal: Int = 0

  /** 多腕バンディット問題の状態を初期化する */
  def reset(): Unit = {
    actionValues = Array.fill(k)(0.0)
    optimal = 0
  }

  /**
    * アクションを引いて報酬を得る
    *
    * @param action
    * @return (reward, isOptimal): 報酬と最適なアクションかどうかのタプル
    */
  def pull(action: Int): (Double, Boolean) = (0.0, true)
}

/**
  * GaussianBandit クラスがモデルするk腕バンディット問題は、各アクションの真の価値が正規分布に従うと仮定し、さらに報酬も正規分布に従うと仮定する。
  *
  * @param k: アクションの数
  * @param mu: 正規分布の平均
  * @param sigma: 正規分布の標準偏差
  */
class GaussianBandit(k: Int, mu: Double = 0.0, sigma: Double = 1.0) extends MultiArmedBandit(k) {

  reset()

  override def reset(): Unit = {
    actionValues = Array.fill(k)(Random.nextGaussian() * sigma + mu)
    optimal = actionValues.zipWithIndex.maxBy(_._1)._2
  }

  override def pull(action: Int): (Double, Boolean) = {
    val reward = Random.nextGaussian() + actionValues(action)
    (reward, action == optimal)
  }
}
