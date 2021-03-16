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
    public static final String BINARY = "608060405234801561001057600080fd5b50610778806100206000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c80632e64cec1146100515780636057361d14610067578063c3014eb71461007c578063f520090a1461008f575b600080fd5b6000546040519081526020015b60405180910390f35b61007a6100753660046104c5565b600055565b005b61007a61008a3660046104c5565b600155565b6100a261009d3660046104dd565b6100af565b60405161005e91906105ef565b60408051808201909152600081526060602082015260408051808201909152600081526060602082015260006100e8600154601f015190565b905060006100f782600261069a565b610102906002610675565b60ff1690506000806101126104a7565b6000805b818a015160000b60041415801561012d5750895182105b1561046757600061013e8b84015190565b905061014b60018461065d565b9250610158600187610637565b95508661ffff168661ffff16111561017f57505060008752509495506104a1945050505050565b8060000b600314156101d657828b0151848660ff16600481106101b257634e487b7160e01b600052603260045260246000fd5b60200201526101c2600186610675565b94506101cf60208461065d565b9250610461565b8060000b600214156103615781156101fd57505060008752509495506104a1945050505050565b60006102098c85015190565b905061021660088561065d565b93506001600160c01b0319818116908e161461024257505060008852509596506104a195505050505050565b600061024e8d86015190565b905061025b60088661065d565b945060006102698e87015190565b905061027660048761065d565b955060008163ffffffff1667ffffffffffffffff8111156102a757634e487b7160e01b600052604160045260246000fd5b6040519080825280601f01601f1916602001820160405280156102d1576020820181803683370190505b5050508d8601516102e863ffffffff83168861065d565b6020808f018390526040519198506001975061030d91600091879185918891016105a8565b60405160208183030381529060405280519060200120888a60ff166004811061034657634e487b7160e01b600052603260045260246000fd5b602002015261035660018a610675565b985050505050610461565b60008461036f6001886106c3565b60ff166004811061039057634e487b7160e01b600052603260045260246000fd5b602002015190506103a26001876106c3565b95506000856103b26001896106c3565b60ff16600481106103d357634e487b7160e01b600052603260045260246000fd5b602002015190506103e56001886106c3565b604051600160f81b6020820152600085900b60f81b6021820152602281018490526042810183905290975060620160405160208183030381529060405280519060200120868860ff166004811061044c57634e487b7160e01b600052603260045260246000fd5b602002015261045c600188610675565b965050505b50610116565b8360ff1660011415610492576001548351141561049257505060018652509394506104a19350505050565b50506000865250939450505050505b92915050565b60405180608001604052806004906020820280368337509192915050565b6000602082840312156104d6578081fd5b5035919050565b600080604083850312156104ef578081fd5b82356001600160c01b031981168114610506578182fd5b9150602083013567ffffffffffffffff80821115610522578283fd5b818501915085601f830112610535578283fd5b8135818111156105475761054761072c565b604051601f8201601f19908116603f0116810190838211818310171561056f5761056f61072c565b81604052828152886020848701011115610587578586fd5b82602086016020830137856020848301015280955050505050509250929050565b600085810b60f81b82526001600160c01b0319858116600184015284516105d68160098601602089016106e6565b9316919092016009810191909152601101949350505050565b600060208252825115156020830152602083015160408084015280518060608501526106228160808601602085016106e6565b601f01601f1916929092016080019392505050565b600061ffff80831681851680830382111561065457610654610716565b01949350505050565b6000821982111561067057610670610716565b500190565b600060ff821660ff84168060ff0382111561069257610692610716565b019392505050565b600060ff821660ff84168160ff04811182151516156106bb576106bb610716565b029392505050565b600060ff821660ff8416808210156106dd576106dd610716565b90039392505050565b60005b838110156107015781810151838201526020016106e9565b83811115610710576000848401525b50505050565b634e487b7160e01b600052601160045260246000fd5b634e487b7160e01b600052604160045260246000fdfea264697066735822122020716f83ed241f9b6bb82abbbcf3198759d59675e31642fb1c5548a0a27d14ab64736f6c63430008020033";

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

    public RemoteFunctionCall<VerifiedOutput> verify(byte[] key, byte[] proof) {
        final Function function = new Function(FUNC_VERIFY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes8(key), 
                new org.web3j.abi.datatypes.DynamicBytes(proof)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<VerifiedOutput>() {}));
        return executeRemoteCallSingleValueReturn(function, VerifiedOutput.class);
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
