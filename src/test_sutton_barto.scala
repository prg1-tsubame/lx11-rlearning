package prg1.sutton.bandit

import scala.util.Random

trait Example {
  val label: String
  val bandit: MultiArmedBandit
  val agents: List[Agent]
}

object EpsilonGreedyExample extends Example {
  val label: String = "2.2 - Action-Value Methods"
  val bandit: GaussianBandit = new GaussianBandit(10)
  val agents: List[Agent] = List(
    new Agent(bandit, new GreedyPolicy()),
    new Agent(bandit, new EpsilonGreedyPolicy(0.01)),
    new Agent(bandit, new EpsilonGreedyPolicy(0.1)),
    new Agent(bandit, new RandomPolicy()),
  )
}

object OptimisticInitialValueExample extends Example {
  val label: String = "2.5 - Optimistic Initial Values"
  val bandit: GaussianBandit = new GaussianBandit(10)
  val agents: List[Agent] = List(
    new Agent(bandit, new EpsilonGreedyPolicy(0.1)),
    new Agent(bandit, new GreedyPolicy(), prior = 5)
  )
}

@main def test_sutton_barto: Unit = {
  val experiments = 500
  val trials = 10000

  for (example <- List[Example](EpsilonGreedyExample, OptimisticInitialValueExample)) {
    val env = new Environment(example.bandit, example.agents, example.label)

    println(f"${"#" * 70}")
    println(s"#  ${example.label}")
    println(example.bandit.actionValues.map(v => f"$v%.3f").mkString(", "))
    println(example.bandit.optimal)
    println(f"${"-" * 70}")

    val (scores, optimal) = env.run(trials, experiments)

    val agent_names = example.agents.map(_.policy.toString).toList
    save_csv(s"result/${example.label}-score.csv", agent_names, scores)
    save_csv(s"result/${example.label}-optimal.csv", agent_names, optimal)

    println("Policies: " + agent_names.mkString(", "))
    println(f"Scores: ${vector2string(scores.last)}")
    println(f"Optimal: ${vector2string(optimal.last)}\n")
  }
}
