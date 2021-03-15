package sgrub.smartcontracts.generated;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class Storage extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50610769806100206000396000f3fe60806040526004361061003f5760003560e01c80632e64cec1146100445780636057361d14610067578063c3014eb714610089578063f520090a146100a9575b600080fd5b34801561005057600080fd5b506000546040519081526020015b60405180910390f35b34801561007357600080fd5b506100876100823660046104b6565b600055565b005b34801561009557600080fd5b506100876100a43660046104b6565b600155565b6100bc6100b73660046104ce565b6100c9565b60405161005e91906105e0565b6040805180820190915260008152606060208201526040805180820190915260008152606060208201526000610102600154601f015190565b9050600061011182600261068b565b61011c906002610666565b60ff16905060008061012c610498565b6000805b818a015160000b60041461045857600061014a8b84015190565b905061015760018461064e565b9250610164600187610628565b95508661ffff168661ffff16111561018b5750506000875250949550610492945050505050565b8060000b600314156101e4578a830151808560ff8816600481106101bf57634e487b7160e01b600052603260045260246000fd5b60200201526101cf600187610666565b95506101dc60208561064e565b935050610452565b8060000b6002141561035257811561020b5750506000875250949550610492945050505050565b8a83015161021a60088561064e565b93506001600160c01b0319818116908e1614610246575050600088525095965061049295505050505050565b8b84015161025560088661064e565b8d81015190955061026760048761064e565b955060008163ffffffff1667ffffffffffffffff81111561029857634e487b7160e01b600052604160045260246000fd5b6040519080825280601f01601f1916602001820160405280156102c2576020820181803683370190505b5050508d8601516102d963ffffffff83168861064e565b6020808f01839052604051919850600197506102fe9160009187918591889101610599565b60405160208183030381529060405280519060200120888a60ff166004811061033757634e487b7160e01b600052603260045260246000fd5b602002015261034760018a610666565b985050505050610452565b6000846103606001886106b4565b60ff166004811061038157634e487b7160e01b600052603260045260246000fd5b602002015190506103936001876106b4565b95506000856103a36001896106b4565b60ff16600481106103c457634e487b7160e01b600052603260045260246000fd5b602002015190506103d66001886106b4565b604051600160f81b6020820152600085900b60f81b6021820152602281018490526042810183905290975060620160405160208183030381529060405280519060200120868860ff166004811061043d57634e487b7160e01b600052603260045260246000fd5b602002015261044d600188610666565b965050505b50610130565b8360ff1660011415610483576001548351141561048357505060018652509394506104929350505050565b50506000865250939450505050505b92915050565b60405180608001604052806004906020820280368337509192915050565b6000602082840312156104c7578081fd5b5035919050565b600080604083850312156104e0578081fd5b82356001600160c01b0319811681146104f7578182fd5b9150602083013567ffffffffffffffff80821115610513578283fd5b818501915085601f830112610526578283fd5b8135818111156105385761053861071d565b604051601f8201601f19908116603f011681019083821181831017156105605761056061071d565b81604052828152886020848701011115610578578586fd5b82602086016020830137856020848301015280955050505050509250929050565b600085810b60f81b82526001600160c01b0319858116600184015284516105c78160098601602089016106d7565b9316919092016009810191909152601101949350505050565b600060208252825115156020830152602083015160408084015280518060608501526106138160808601602085016106d7565b601f01601f1916929092016080019392505050565b600061ffff80831681851680830382111561064557610645610707565b01949350505050565b6000821982111561066157610661610707565b500190565b600060ff821660ff84168060ff0382111561068357610683610707565b019392505050565b600060ff821660ff84168160ff04811182151516156106ac576106ac610707565b029392505050565b600060ff821660ff8416808210156106ce576106ce610707565b90039392505050565b60005b838110156106f25781810151838201526020016106da565b83811115610701576000848401525b50505050565b634e487b7160e01b600052601160045260246000fd5b634e487b7160e01b600052604160045260246000fdfea2646970667358221220e1682033e90c4b5bc9aa9eb0771f43430ed3df6a5adccc84863372dbeb5f3f9364736f6c63430008020033";

    public static final String FUNC_RETRIEVE = "retrieve";

    public static final String FUNC_STORE = "store";

    public static final String FUNC_UPDATEDIGEST = "updateDigest";

    public static final String FUNC_VERIFY = "verify";

    @Deprecated
    protected Storage(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Storage(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Storage(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Storage(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<BigInteger> retrieve() {
        final Function function = new Function(FUNC_RETRIEVE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> store(BigInteger num) {
        final Function function = new Function(
                FUNC_STORE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(num)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateDigest(byte[] digest) {
        final Function function = new Function(
                FUNC_UPDATEDIGEST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(digest)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> verify(byte[] key, byte[] proof) {
        final Function function = new Function(
                FUNC_VERIFY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes8(key), 
                new org.web3j.abi.datatypes.DynamicBytes(proof)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Storage load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Storage(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Storage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Storage(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Storage load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Storage(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Storage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Storage(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Storage> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Storage.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Storage> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Storage.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Storage> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Storage.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Storage> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Storage.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class VerifiedOutput extends DynamicStruct {
        public Boolean valid;

        public byte[] value;

        public VerifiedOutput(Boolean valid, byte[] value) {
            super(new org.web3j.abi.datatypes.Bool(valid),new org.web3j.abi.datatypes.DynamicBytes(value));
            this.valid = valid;
            this.value = value;
        }

        public VerifiedOutput(Bool valid, DynamicBytes value) {
            super(valid,value);
            this.valid = valid.getValue();
            this.value = value.getValue();
        }
    }
}
