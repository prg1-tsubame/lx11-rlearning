package prg1.sutton.bandit

import scala.collection.mutable.ArraySeq

/** 行列を表す型エイリアス */
type Matrix = ArraySeq[ArraySeq[Double]]

/**
  * 行列を生成する
  * @param rows: 行数
  * @param cols: 列数
  * @return 行列
  */
def matrix(rows: Int, cols: Int): Matrix = ArraySeq.tabulate(rows, cols)((r,c) => 0.0)

/**
 * ベクトルを文字列に変換する
 * @param v: ベクトル
 * @return 文字列
 */
def vector2string(v: ArraySeq[Double]): String = v.map(v => f"$v%.3f").mkString(", ")

/**
  * 行列を文字列に変換する
  *
  * @param m: 行列
  * @return 文字列
  */
def matrix2string(m: Matrix): String = m.map(vector2string).mkString("\n")

/**
  * データを CSV ファイルに保存する。
  *
  * @param fname: ファイル名
  * @param labels: CSVファイルのヘッダ
  * @param data: 行列のデータ
  */
def save_csv(fname: String, labels: List[String], data: Matrix): Unit = {
  val pw = new java.io.PrintWriter(new java.io.File(fname))
  pw.write(labels.mkString(",") + "\n")
  data.foreach { row =>
    pw.write(row.mkString(",") + "\n")
  }
  pw.close()
}
