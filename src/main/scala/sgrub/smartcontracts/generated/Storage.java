package sgrub.smartcontracts.generated;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
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
    public static final String BINARY = "608060405234801561001057600080fd5b5061090d806100206000396000f3fe60806040526004361061004a5760003560e01c80632e64cec11461004f5780636057361d1461007257806396146e4a14610094578063b89dd5fd146100c1578063f520090a146100e1575b600080fd5b34801561005b57600080fd5b506000546040519081526020015b60405180910390f35b34801561007e57600080fd5b5061009261008d3660046106f5565b600055565b005b3480156100a057600080fd5b506100b46100af36600461064d565b6100f4565b604051610069919061079b565b3480156100cd57600080fd5b506100926100dc3660046106ba565b6101a7565b6100926100ef36600461066e565b61021a565b6040805160088082528183019092526060916000918291602082018180368337019050509050600091505b60088260ff16101561019e57838260ff166008811061014e57634e487b7160e01b600052603260045260246000fd5b1a60f81b818360ff168151811061017557634e487b7160e01b600052603260045260246000fd5b60200101906001600160f81b031916908160001a905350816101968161086b565b92505061011f565b9150505b919050565b80516021146101fc5760405162461bcd60e51b815260206004820152601760248201527f446967657374206d757374206265203333206279746573000000000000000000604482015260640160405180910390fd5b8051600155602001516002805460ff191660ff909216919091179055565b6002805460009161022e9160ff1690610811565b6102399060026107ae565b9050600080610246610590565b60008060605b8288015160000b6004141580156102635750875183105b156105005760006102748985015190565b90506102816001856107d4565b935061028e6001886107ae565b96508761ffff168761ffff1611156102e6576000805160206108b883398151915260006040516102d1911515815260406020820181905260009082015260600190565b60405180910390a1505050505050505061058c565b8060000b60031415610304576102fd6020856107d4565b93506104fa565b8060000b600214156104fa578215610347576000805160206108b883398151915260006040516102d1911515815260406020820181905260009082015260600190565b60006103538a86015190565b90506103606008866107d4565b94506001600160c01b0319818116908c16146103b4576000805160206108b883398151915260016103908d6100f4565b60405161039e929190610780565b60405180910390a150505050505050505061058c565b60006103c08b87015190565b90506103cd6008876107d4565b955060006103db8c88015190565b90506103e86004886107d4565b96508063ffffffff1667ffffffffffffffff81111561041757634e487b7160e01b600052604160045260246000fd5b6040519080825280601f01601f191660200182016040528015610441576020820181803683370190505b50508b870151945061045963ffffffff8216886107d4565b96506001955060008386846040516020016104779493929190610739565b60405160208183030381529060405280519060200120888a60ff16600481106104b057634e487b7160e01b600052603260045260246000fd5b60200201526104c060018a6107ec565b98506000805160206108b88339815191526001866040516104e2929190610780565b60405180910390a1505050505050505050505061058c565b5061024c565b8460ff166001141561054b576001548451141561054b576000805160206108b8833981519152600182604051610537929190610780565b60405180910390a15050505050505061058c565b6000805160206108b8833981519152600060405161057c911515815260406020820181905260009082015260600190565b60405180910390a1505050505050505b5050565b60405180608001604052806004906020820280368337509192915050565b80356001600160c01b0319811681146101a257600080fd5b600082601f8301126105d6578081fd5b813567ffffffffffffffff808211156105f1576105f16108a1565b604051601f8301601f19908116603f01168101908282118183101715610619576106196108a1565b81604052838152866020858801011115610631578485fd5b8360208701602083013792830160200193909352509392505050565b60006020828403121561065e578081fd5b610667826105ae565b9392505050565b60008060408385031215610680578081fd5b610689836105ae565b9150602083013567ffffffffffffffff8111156106a4578182fd5b6106b0858286016105c6565b9150509250929050565b6000602082840312156106cb578081fd5b813567ffffffffffffffff8111156106e1578182fd5b6106ed848285016105c6565b949350505050565b600060208284031215610706578081fd5b5035919050565b6000815180845261072581602086016020860161083b565b601f01601f19169290920160200192915050565b600085810b60f81b82526001600160c01b03198581166001840152845161076781600986016020890161083b565b9316919092016009810191909152601101949350505050565b60008315158252604060208301526106ed604083018461070d565b600060208252610667602083018461070d565b600061ffff8083168185168083038211156107cb576107cb61088b565b01949350505050565b600082198211156107e7576107e761088b565b500190565b600060ff821660ff84168060ff038211156108095761080961088b565b019392505050565b600061ffff808316818516818304811182151516156108325761083261088b565b02949350505050565b60005b8381101561085657818101518382015260200161083e565b83811115610865576000848401525b50505050565b600060ff821660ff8114156108825761088261088b565b60010192915050565b634e487b7160e01b600052601160045260246000fd5b634e487b7160e01b600052604160045260246000fdfe7e6c7cd483fed8c7bd2cba9eda78879f673df57235bb989cf81e9afb25b8175ba2646970667358221220195d59f1e227fecb5de9ac4f6e102ff33f5dbfb9aabe95bccdf5c14683affeb264736f6c63430008020033";

    public static final String FUNC_BYTES8TOBYTES = "bytes8ToBytes";

    public static final String FUNC_RETRIEVE = "retrieve";

    public static final String FUNC_STORE = "store";

    public static final String FUNC_UPDATEDIGEST = "updateDigest";

    public static final String FUNC_VERIFY = "verify";

    public static final Event VERIFIEDOUTPUT_EVENT = new Event("VerifiedOutput", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<DynamicBytes>() {}));
    ;

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

    public List<VerifiedOutputEventResponse> getVerifiedOutputEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(VERIFIEDOUTPUT_EVENT, transactionReceipt);
        ArrayList<VerifiedOutputEventResponse> responses = new ArrayList<VerifiedOutputEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            VerifiedOutputEventResponse typedResponse = new VerifiedOutputEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.valid = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.value = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<VerifiedOutputEventResponse> verifiedOutputEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, VerifiedOutputEventResponse>() {
            @Override
            public VerifiedOutputEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(VERIFIEDOUTPUT_EVENT, log);
                VerifiedOutputEventResponse typedResponse = new VerifiedOutputEventResponse();
                typedResponse.log = log;
                typedResponse.valid = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.value = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<VerifiedOutputEventResponse> verifiedOutputEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VERIFIEDOUTPUT_EVENT));
        return verifiedOutputEventFlowable(filter);
    }

    public RemoteFunctionCall<byte[]> bytes8ToBytes(byte[] _bytes8) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_BYTES8TOBYTES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes8(_bytes8)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<BigInteger> retrieve() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_RETRIEVE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> store(BigInteger num) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_STORE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(num)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateDigest(byte[] digest) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATEDIGEST, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(digest)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> verify(byte[] key, byte[] proof) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
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

    public static class VerifiedOutputEventResponse extends BaseEventResponse {
        public Boolean valid;

        public byte[] value;
    }
}
