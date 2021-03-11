package sgrub.smartcontracts.generated;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
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
public class Greeter extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161053f38038061053f83398101604081905261002f916100e2565b8051610042906000906020840190610049565b50506101fc565b828054610055906101ab565b90600052602060002090601f01602090048101928261007757600085556100bd565b82601f1061009057805160ff19168380011785556100bd565b828001600101855582156100bd579182015b828111156100bd5782518255916020019190600101906100a2565b506100c99291506100cd565b5090565b5b808211156100c957600081556001016100ce565b600060208083850312156100f4578182fd5b82516001600160401b038082111561010a578384fd5b818501915085601f83011261011d578384fd5b81518181111561012f5761012f6101e6565b604051601f8201601f19908116603f01168101908382118183101715610157576101576101e6565b81604052828152888684870101111561016e578687fd5b8693505b8284101561018f5784840186015181850187015292850192610172565b8284111561019f57868684830101525b98975050505050505050565b6002810460018216806101bf57607f821691505b602082108114156101e057634e487b7160e01b600052602260045260246000fd5b50919050565b634e487b7160e01b600052604160045260246000fd5b6103348061020b6000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c8063a41368621461003b578063cfae321714610050575b600080fd5b61004e6100493660046101b0565b61006e565b005b610058610085565b604051610065919061025a565b60405180910390f35b8051610081906000906020840190610117565b5050565b606060008054610094906102ad565b80601f01602080910402602001604051908101604052809291908181526020018280546100c0906102ad565b801561010d5780601f106100e25761010080835404028352916020019161010d565b820191906000526020600020905b8154815290600101906020018083116100f057829003601f168201915b5050505050905090565b828054610123906102ad565b90600052602060002090601f016020900481019282610145576000855561018b565b82601f1061015e57805160ff191683800117855561018b565b8280016001018555821561018b579182015b8281111561018b578251825591602001919060010190610170565b5061019792915061019b565b5090565b5b80821115610197576000815560010161019c565b6000602082840312156101c1578081fd5b813567ffffffffffffffff808211156101d8578283fd5b818401915084601f8301126101eb578283fd5b8135818111156101fd576101fd6102e8565b604051601f8201601f19908116603f01168101908382118183101715610225576102256102e8565b8160405282815287602084870101111561023d578586fd5b826020860160208301379182016020019490945295945050505050565b6000602080835283518082850152825b818110156102865785810183015185820160400152820161026a565b818111156102975783604083870101525b50601f01601f1916929092016040019392505050565b6002810460018216806102c157607f821691505b602082108114156102e257634e487b7160e01b600052602260045260246000fd5b50919050565b634e487b7160e01b600052604160045260246000fdfea26469706673582212203c52e262a9ae17f6e1cb853017ec3b184d324f958768fa1e7a87e4a1561f7b9564736f6c63430008020033";

    public static final String FUNC_GREET = "greet";

    public static final String FUNC_SETGREETING = "setGreeting";

    @Deprecated
    protected Greeter(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Greeter(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Greeter(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Greeter(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<String> greet() {
        final Function function = new Function(FUNC_GREET, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setGreeting(String _greeting) {
        final Function function = new Function(
                FUNC_SETGREETING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_greeting)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Greeter load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Greeter(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Greeter load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Greeter(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Greeter load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Greeter(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Greeter load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Greeter(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Greeter> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _greeting) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_greeting)));
        return deployRemoteCall(Greeter.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Greeter> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _greeting) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_greeting)));
        return deployRemoteCall(Greeter.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Greeter> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _greeting) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_greeting)));
        return deployRemoteCall(Greeter.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Greeter> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _greeting) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_greeting)));
        return deployRemoteCall(Greeter.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
