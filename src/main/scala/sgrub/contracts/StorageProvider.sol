pragma experimental ABIEncoderV2;
pragma solidity ^ 0.7.0;


// client contract implements deliver callback function to receive requested values
contract Client {
    function deliver(uint, bytes memory) public {}
}

contract StorageProvider {

    // client to deliver requests to
    Client c;

    /*data struct;
    value is a dynamic byte array;
    exists = true when added by DO;
    proof contains hashes for verification by DU
    */
    struct data {
        bytes _value;
        bool _exists;
        bytes _proof;
    }

    //on-chain storage
    mapping(uint => data) public datastore;

    //owner of the contract
    address owner;

    // set the owner of this contract to whoever deployed it
    constructor() {
        owner = msg.sender;
    }

    // modifier for functions that can only be executed by the owner of the contract
    modifier onlyOwner() {
        require(msg.sender==owner);
        _;
    }

    // a request event with indexed key and address to filter logs
    event request(uint indexed key, address indexed sender);

    //callback deliver with value from storage if it exists, or emit request for off-chain data
    function gGet(uint key) public {
        //register the function caller to deliver values
        c = Client(msg.sender);

        //get from storage if exists and callback
        if(datastore[key]._exists){
            c.deliver(key, datastore[key]._value);
        }
        //otherwise emit event with requested key and address to deliver to
        else {
            emit request(key, msg.sender);
        }
    }

    //SP can use this to update the data
    function update(uint _key, bytes memory _value, bytes memory _proof) public onlyOwner {
        data memory d = data(_value, true, _proof);
        datastore[_key] = d;
    }

    //SP can use this to delete some data
    function remove(uint key) public onlyOwner {
        delete datastore[key];
    }
}