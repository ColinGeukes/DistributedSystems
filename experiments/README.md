# SGRuB

## Requirements

* Scala 2.12.* (2.12.12+)
* JVM 1.8 compatible JDK
* SBT 1.4.9+
* geth 1.10.1+
* Python3.6+

## Run experiments

Start the private network and miner, follow the instructions in `geth_private/README.md`.

There is no need to setup contracts beforehand as in the `src`, contract addresses are created clean per experiment.

There are five different tests currently.
```
1: Experiment: Write X Bytes
2: Experiment: Write X Bytes of Y Batches (even/random distributed)
3: Experiment: gGet cost with(out) replica
4: Experiment: Deliver cost
5: Experiment: Static Baselines
```

Running the experiments will create a new `.csv` file in the `experiments/results` folder. If the experiment setup is exactly the same as a previous experiment then it will be overwritten.


## Run plots
The plots are created with Python. They are located in `experiments/plots`.
To run, for example, the `plotA.py` it is sufficient to open a terminal with `python plotA.py` to generate the plot.