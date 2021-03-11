# Requirements

Solidity compiler:
```
sudo add-apt-repository ppa:ethereum/ethereum
sudo apt-get update
sudo apt-get install solc
```

Web3j CLI:
```
curl -L get.web3j.io | sh && source ~/.web3j/source.sh
```

# Usage

Put your Solidity `<yourcontract>.sol` file in the `smartcontracts` folder.
Then, from the `smartcontracts` folder:
```
./build-generate-solidity.sh <yourcontract>
```

Example Scala code:
```
val web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545/
val credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");

// Deploy contract
val contract = Greeter.deploy(
  web3, credentials, new DefaultGasProvider(), "Hi").send();
// If the contract was already deployed, instead...
val contract = Greeter.load("someaddress", web3, credentials, new DefaultGasProvider())

// Call a procedure on the contract
contract.greet()
```