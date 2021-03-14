# Private Blockchain instructions

## Initialize the private network

```shell
geth --datadir node01 init genesis.json
geth --identity "node01" --datadir node01 --ipcdisable --port 30301 --http --http.api personal,eth,net,web3,miner --http.port 8101
```

Attach to the network and start mining:
```shell
geth attach http://127.0.0.1:8101
miner.setEtherbase(eth.accounts[0])
miner.start(1)
```

For the purposes of our test setup, a number of accounts have been made in advance, their passwords are all "password".
To list the accounts, attach to the node and run:
```shell
eth.accounts
```

