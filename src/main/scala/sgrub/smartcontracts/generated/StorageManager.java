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
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
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
public class StorageManager extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50600280546001600160a01b031916331790556106d6806100326000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c806309f2bd0314610051578063968d85dd14610066578063ae0d3e2714610079578063e6359f401461008e575b600080fd5b61006461005f36600461050b565b6100a1565b005b610064610074366004610444565b610156565b60015460405190815260200160405180910390f35b61006461009c36600461050b565b61024c565b6001600160c01b0319811660009081526020819052604090206001015460ff161561011b576001600160c01b031981166000818152602081905260409081902090517fccb79a8a9f3ec33a4189d30de745e5c47ed182185622ba2500d8a6250576158e9161010e9161052c565b60405180910390a2610153565b60405133906001600160c01b03198316907fc7e6de367830ad130657352fe6da03a69f9695ed2bf3e862ea3a789ac75e443990600090a35b50565b6002546001600160a01b0316331461016d57600080fd5b600181905560005b83518110156102465760405180604001604052808483815181106101a957634e487b7160e01b600052603260045260246000fd5b60200260200101518152602001600115158152506000808684815181106101e057634e487b7160e01b600052603260045260246000fd5b6020908102919091018101516001600160c01b03191682528181019290925260400160002082518051919261021a92849290910190610296565b50602091909101516001909101805460ff191691151591909117905561023f81610663565b9050610175565b50505050565b6002546001600160a01b0316331461026357600080fd5b6001600160c01b03198116600090815260208190526040812090610287828261031a565b50600101805460ff1916905550565b8280546102a290610628565b90600052602060002090601f0160209004810192826102c4576000855561030a565b82601f106102dd57805160ff191683800117855561030a565b8280016001018555821561030a579182015b8281111561030a5782518255916020019190600101906102ef565b50610316929150610352565b5090565b50805461032690610628565b6000825580601f106103385750610153565b601f01602090049060005260206000209081019061015391905b5b808211156103165760008155600101610353565b6000601f8381840112610378578182fd5b8235602061038d61038883610604565b6105d3565b82815281810190868301865b8581101561041957813589018a603f8201126103b3578889fd5b85810135604067ffffffffffffffff8211156103d1576103d161068a565b6103e2828b01601f191689016105d3565b8281528d828486010111156103f5578b8cfd5b828285018a83013791820188018b9052508552509284019290840190600101610399565b509098975050505050505050565b80356001600160c01b03198116811461043f57600080fd5b919050565b600080600060608486031215610458578283fd5b833567ffffffffffffffff8082111561046f578485fd5b818601915086601f830112610482578485fd5b8135602061049261038883610604565b82815281810190858301838502870184018c10156104ae57898afd5b8996505b848710156104d7576104c381610427565b8352600196909601959183019183016104b2565b50975050870135925050808211156104ed578384fd5b506104fa86828701610367565b925050604084013590509250925092565b60006020828403121561051c578081fd5b61052582610427565b9392505050565b600060208083528184548360028204905060018083168061054e57607f831692505b85831081141561056c57634e487b7160e01b87526022600452602487fd5b878601838152602001818015610589576001811461059a576105c4565b60ff198616825287820196506105c4565b60008b815260209020895b868110156105be578154848201529085019089016105a5565b83019750505b50949998505050505050505050565b604051601f8201601f1916810167ffffffffffffffff811182821017156105fc576105fc61068a565b604052919050565b600067ffffffffffffffff82111561061e5761061e61068a565b5060209081020190565b60028104600182168061063c57607f821691505b6020821081141561065d57634e487b7160e01b600052602260045260246000fd5b50919050565b600060001982141561068357634e487b7160e01b81526011600452602481fd5b5060010190565b634e487b7160e01b600052604160045260246000fdfea26469706673582212201b7aa9876c2062d39bbac02041c289040248c09735ece1a58e13cd82f710522664736f6c63430008020033";

    public static final String FUNC_GGET = "gGet";

    public static final String FUNC_GETDIGEST = "getDigest";

    public static final String FUNC_REMOVE = "remove";

    public static final String FUNC_UPDATE = "update";

    public static final Event DELIVER_EVENT = new Event("deliver", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes8>(true) {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event REQUEST_EVENT = new Event("request", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes8>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected StorageManager(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected StorageManager(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected StorageManager(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected StorageManager(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<DeliverEventResponse> getDeliverEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(DELIVER_EVENT, transactionReceipt);
        ArrayList<DeliverEventResponse> responses = new ArrayList<DeliverEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DeliverEventResponse typedResponse = new DeliverEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.key = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.value = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
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
                typedResponse.value = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DeliverEventResponse> deliverEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DELIVER_EVENT));
        return deliverEventFlowable(filter);
    }

    public List<RequestEventResponse> getRequestEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(REQUEST_EVENT, transactionReceipt);
        ArrayList<RequestEventResponse> responses = new ArrayList<RequestEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RequestEventResponse typedResponse = new RequestEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.key = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RequestEventResponse> requestEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, RequestEventResponse>() {
            @Override
            public RequestEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(REQUEST_EVENT, log);
                RequestEventResponse typedResponse = new RequestEventResponse();
                typedResponse.log = log;
                typedResponse.key = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.sender = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RequestEventResponse> requestEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REQUEST_EVENT));
        return requestEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> gGet(byte[] key) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GGET, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes8(key)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<byte[]> getDigest() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETDIGEST, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<TransactionReceipt> remove(byte[] key) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REMOVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes8(key)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> update(List<byte[]> keys, List<byte[]> values, byte[] _digest) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes8>(
                        org.web3j.abi.datatypes.generated.Bytes8.class,
                        org.web3j.abi.Utils.typeMap(keys, org.web3j.abi.datatypes.generated.Bytes8.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicBytes>(
                        org.web3j.abi.datatypes.DynamicBytes.class,
                        org.web3j.abi.Utils.typeMap(values, org.web3j.abi.datatypes.DynamicBytes.class)), 
                new org.web3j.abi.datatypes.generated.Bytes32(_digest)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static StorageManager load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new StorageManager(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static StorageManager load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new StorageManager(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static StorageManager load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new StorageManager(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static StorageManager load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new StorageManager(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<StorageManager> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(StorageManager.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<StorageManager> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(StorageManager.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<StorageManager> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(StorageManager.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<StorageManager> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(StorageManager.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class DeliverEventResponse extends BaseEventResponse {
        public byte[] key;

        public byte[] value;
    }

    public static class RequestEventResponse extends BaseEventResponse {
        public byte[] key;

        public String sender;
    }
}
