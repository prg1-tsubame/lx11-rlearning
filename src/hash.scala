import scala.collection.mutable.ArraySeq

/**
 * ハッシュ法の実装例
 */
object Hash {

    /**
     * 小説「雪国」の一節に現れる単語列
     */
    val 雪国 = List("国境", "の", "長い", "トンネル", "を", "抜ける", "と", "雪国", "で", "あつ", "た")

    /**
     * ハッシュ値を格納する配列。
     */
    val occurrences = ArraySeq.fill(31)(0)

    /**
     * 単語のハッシュ値を計算する。
     * 
     * @param word 単語
     * @return 単語のハッシュ値
     */
    def hash(word: String): Int = word.hashCode() % occurrences.size

    /**
     * ハッシュ値と単語の出現頻度を表示する例題。
     * ハッシュ値の衝突回避をしていないために「国境」と「の」の出現頻度が正しくカウントされないことに注意。
     */
    @main def run_hash = {
        println("雪国に出現する語のハッシュ値：")
        雪国.foreach(word => println((word, hash(word))))
        println()

        // Construct the hash table
        雪国.foreach(word => {
            val h = hash(word)
            occurrences(h) = occurrences(h) + 1
        })

        println(" \n雪国に出現する語の出現頻度：")
        雪国.foreach(word => println((word, occurrences(hash(word)))))
        println()
    }
}

/**
 * オープンアドレス法によってキーの衝突を回避するハッシュ法の実装例
 */
object OpenAddressing {

    /**
     * ハッシュ表の大きさ
     */
    val HASH_SIZE = 31

    /**
     * ハッシュ表：登録された要素の配列として構成される。
     */
    val entries: ArraySeq[String]  = ArraySeq.fill(HASH_SIZE)("")

    /**
     * ハッシュ表に登録された要素の出現回数の配列。
     */
    val occurrences: ArraySeq[Int] = ArraySeq.fill(HASH_SIZE)(0)

    /**
     * ハッシュ値を計算する。この定義は Hash オブジェクトの hash と同じ。
     * 
     * @param word ハッシュ値を計算する単語
     * @return ハッシュ値
     */
    def hash(word: String): Int = word.hashCode() % HASH_SIZE

    /**
     * 単語をハッシュ表に挿入する。オープンアドレス法を使用。
     * 
     * @param word 挿入する単語
     */
    def new_occurrence(word: String): Unit = {
        // ハッシュ値
        var h = hash(word)

        /*
         * ハッシュ表のエントリーを探す。与えた単語とは異なる単語が格納されている場合は、ハッシュ値が衝突するので、次の線形探索を続ける。未使用のエントリーが見つかった場合は、そのエントリーに単語を挿入する。
         * この実装はハッシュ表が十分に大きいことを仮定している。キーの数がハッシュ表のエントリー数よりも大きい場合は無限ループになるので注意を要する。その可能性がある場合は終了判定を厳格にすること
         */
        while (entries(h) != "" && entries(h) != word) h = (h + 1) % HASH_SIZE
        entries(h) = word
        occurrences(h) = occurrences(h) + 1
    }

    /**
     * ハッシュ表に単語を挿入し、その出現回数を更新する。
     */
    @main def run_open_adressing = {
        Hash.雪国.foreach(word => new_occurrence(word))
        for (i <- 0 until HASH_SIZE) {
            if (entries(i) != "") println(f"$i ${entries(i)}(${occurrences(i)})")
        }
    }
}
