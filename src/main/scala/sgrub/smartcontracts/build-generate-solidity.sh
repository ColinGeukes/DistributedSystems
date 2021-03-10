solc ${1}.sol --bin --abi --optimize -o ./bin/;
web3j generate solidity -b ./bin/${1}.bin -a ./bin/${1}.abi -o ../.. -p sgrub.smartcontracts.generated