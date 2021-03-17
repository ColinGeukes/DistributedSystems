// SPDX-License-Identifier: GPL-3.0

pragma experimental ABIEncoderV2;
pragma solidity ^ 0.8.2;


contract StorageManager {

    /*data struct;
    value is a dynamic byte array;
    exists = true when added by DO;
    proof contains hashes for verification by DU
    */
    struct data {
        bytes _value;
        bool _exists;
    }

    //on-chain storage
    mapping(bytes8 => data) datastore;
    bytes32 public digest;

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
    event request(bytes8 indexed key, address indexed sender);

    // a deliver event with indexed key and value to filter for
    event deliver(bytes8 indexed key, bytes value);

    // callback deliver with value from storage if it exists, or emit request for off-chain data
    function gGet(bytes8 key) public {
        // get from storage if exists and callback
        if(datastore[key]._exists){
            emit deliver(key, datastore[key]._value);
        }
        // otherwise emit event with requested key and address to deliver to
        else {
            emit request(key, msg.sender);
        }
    }

    // DO can use this to update the data
    function update(bytes8[] memory keys, bytes[] memory values, bytes32 _digest) public onlyOwner {
        digest = _digest;
        for (uint i=0; i<keys.length; ++i){
            datastore[keys[i]] = data(values[i], true);
        }
    }

    // DO can use this to delete some data
    function remove(bytes8 key) public onlyOwner {
        delete datastore[key];
    }
}