package sgrub.smartcontracts.generated;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
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
import org.web3j.tuples.generated.Tuple3;
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
    public static final String BINARY = "608060405234801561001057600080fd5b50600280546001600160a01b03191633179055610737806100326000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c80634cc8221514610051578063525058d814610066578063a7b1e17014610079578063cd9b19cc1461008c575b600080fd5b61006461005f3660046104fc565b6100b7565b005b610064610074366004610514565b610106565b6100646100873660046104fc565b610194565b61009f61009a3660046104fc565b610265565b6040516100ae939291906105c9565b60405180910390f35b6002546001600160a01b031633146100ce57600080fd5b6000818152600160205260408120906100e782826103a0565b60018201805460ff191690556101016002830160006103a0565b505050565b6002546001600160a01b0316331461011d57600080fd5b60408051606081018252838152600160208083018290528284018590526000878152918152929020815180519293849361015a92849201906103dc565b5060208281015160018301805460ff19169115159190911790556040830151805161018b92600285019201906103dc565b50505050505050565b600080546001600160a01b03191633178155818152600160208190526040909120015460ff1615610234576000805482825260016020526040918290209151630ab982ff60e11b81526001600160a01b039091169163157305fe916101fd918591600401610600565b600060405180830381600087803b15801561021757600080fd5b505af115801561022b573d6000803e3d6000fd5b50505050610262565b604051339082907f9ad8a98020b6dad44acbf88ecd6cf7536823e301cab64c14479f81dc2980898e90600090a35b50565b600160205260009081526040902080548190610280906106b0565b80601f01602080910402602001604051908101604052809291908181526020018280546102ac906106b0565b80156102f95780601f106102ce576101008083540402835291602001916102f9565b820191906000526020600020905b8154815290600101906020018083116102dc57829003601f168201915b5050506001840154600285018054949560ff90921694919350915061031d906106b0565b80601f0160208091040260200160405190810160405280929190818152602001828054610349906106b0565b80156103965780601f1061036b57610100808354040283529160200191610396565b820191906000526020600020905b81548152906001019060200180831161037957829003601f168201915b5050505050905083565b5080546103ac906106b0565b6000825580601f106103be5750610262565b601f0160209004906000526020600020908101906102629190610460565b8280546103e8906106b0565b90600052602060002090601f01602090048101928261040a5760008555610450565b82601f1061042357805160ff1916838001178555610450565b82800160010185558215610450579182015b82811115610450578251825591602001919060010190610435565b5061045c929150610460565b5090565b5b8082111561045c5760008155600101610461565b600082601f830112610485578081fd5b813567ffffffffffffffff808211156104a0576104a06106eb565b604051601f8301601f19908116603f011681019082821181831017156104c8576104c86106eb565b816040528381528660208588010111156104e0578485fd5b8360208701602083013792830160200193909352509392505050565b60006020828403121561050d578081fd5b5035919050565b600080600060608486031215610528578182fd5b83359250602084013567ffffffffffffffff80821115610546578384fd5b61055287838801610475565b93506040860135915080821115610567578283fd5b5061057486828701610475565b9150509250925092565b60008151808452815b818110156105a357602081850181015186830182015201610587565b818111156105b45782602083870101525b50601f01601f19169290920160200192915050565b6000606082526105dc606083018661057e565b841515602084015282810360408401526105f6818561057e565b9695505050505050565b600083825260206040818401528184548360028204905060018083168061062857607f831692505b85831081141561064657634e487b7160e01b87526022600452602487fd5b60408801839052606088018180156106655760018114610676576106a0565b60ff198616825287820196506106a0565b60008b815260209020895b8681101561069a57815484820152908501908901610681565b83019750505b50949a9950505050505050505050565b6002810460018216806106c457607f821691505b602082108114156106e557634e487b7160e01b600052602260045260246000fd5b50919050565b634e487b7160e01b600052604160045260246000fdfea2646970667358221220d36db3a6051a43d6577ccf111099f4df1c339ab905c76f259e044efc5983f2b364736f6c63430008020033";

    public static final String FUNC_DATASTORE = "datastore";

    public static final String FUNC_GGET = "gGet";

    public static final String FUNC_REMOVE = "remove";

    public static final String FUNC_UPDATE = "update";

    public static final Event REQUEST_EVENT = new Event("request", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}));
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

    public List<RequestEventResponse> getRequestEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(REQUEST_EVENT, transactionReceipt);
        ArrayList<RequestEventResponse> responses = new ArrayList<RequestEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RequestEventResponse typedResponse = new RequestEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.key = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
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
                typedResponse.key = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
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

    public RemoteFunctionCall<Tuple3<byte[], Boolean, byte[]>> datastore(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DATASTORE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}, new TypeReference<Bool>() {}, new TypeReference<DynamicBytes>() {}));
        return new RemoteFunctionCall<Tuple3<byte[], Boolean, byte[]>>(function,
                new Callable<Tuple3<byte[], Boolean, byte[]>>() {
                    @Override
                    public Tuple3<byte[], Boolean, byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<byte[], Boolean, byte[]>(
                                (byte[]) results.get(0).getValue(), 
                                (Boolean) results.get(1).getValue(), 
                                (byte[]) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> gGet(BigInteger key) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GGET, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(key)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> remove(BigInteger key) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REMOVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(key)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> update(BigInteger _key, byte[] _value, byte[] _proof) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_key), 
                new org.web3j.abi.datatypes.DynamicBytes(_value), 
                new org.web3j.abi.datatypes.DynamicBytes(_proof)), 
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

    public static RemoteCall<StorageProvider> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(StorageProvider.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<StorageProvider> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(StorageProvider.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<StorageProvider> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(StorageProvider.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class RequestEventResponse extends BaseEventResponse {
        public BigInteger key;

        public String sender;
    }
}
