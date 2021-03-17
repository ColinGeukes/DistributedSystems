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
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes8;
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
public class StorageProvider extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506101e8806100206000396000f3fe60806040526004361061001e5760003560e01c80636464738814610023575b600080fd5b61003661003136600461007e565b610038565b005b816001600160c01b0319167fccb79a8a9f3ec33a4189d30de745e5c47ed182185622ba2500d8a6250576158e826040516100729190610149565b60405180910390a25050565b60008060408385031215610090578182fd5b82356001600160c01b0319811681146100a7578283fd5b9150602083013567ffffffffffffffff808211156100c3578283fd5b818501915085601f8301126100d6578283fd5b8135818111156100e8576100e861019c565b604051601f8201601f19908116603f011681019083821181831017156101105761011061019c565b81604052828152886020848701011115610128578586fd5b82602086016020830137856020848301015280955050505050509250929050565b6000602080835283518082850152825b8181101561017557858101830151858201604001528201610159565b818111156101865783604083870101525b50601f01601f1916929092016040019392505050565b634e487b7160e01b600052604160045260246000fdfea2646970667358221220e51f6127f19c4ca23483435fa25fba6a852d299eee92a445f23b0ddf506ccb6e64736f6c63430008020033";

    public static final String FUNC_EMITDELIVER = "emitDeliver";

    public static final Event DELIVER_EVENT = new Event("deliver", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes8>(true) {}, new TypeReference<DynamicBytes>() {}));
    ;

    @Deprecated
    protected StorageProvider(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected StorageProvider(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected StorageProvider(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected StorageProvider(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<DeliverEventResponse> getDeliverEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(DELIVER_EVENT, transactionReceipt);
        ArrayList<DeliverEventResponse> responses = new ArrayList<DeliverEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DeliverEventResponse typedResponse = new DeliverEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.key = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.proof = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<DeliverEventResponse> deliverEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, DeliverEventResponse>() {
            @Override
            public DeliverEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(DELIVER_EVENT, log);
                DeliverEventResponse typedResponse = new DeliverEventResponse();
                typedResponse.log = log;
                typedResponse.key = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.proof = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DeliverEventResponse> deliverEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DELIVER_EVENT));
        return deliverEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> emitDeliver(byte[] key, byte[] proof) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_EMITDELIVER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes8(key), 
                new org.web3j.abi.datatypes.DynamicBytes(proof)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static StorageProvider load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new StorageProvider(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static StorageProvider load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new StorageProvider(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static StorageProvider load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new StorageProvider(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static StorageProvider load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new StorageProvider(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<StorageProvider> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(StorageProvider.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<StorageProvider> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(StorageProvider.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<StorageProvider> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(StorageProvider.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<StorageProvider> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(StorageProvider.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class DeliverEventResponse extends BaseEventResponse {
        public byte[] key;

        public byte[] proof;
    }
}
