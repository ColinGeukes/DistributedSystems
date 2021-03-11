pragma experimental ABIEncoderV2;
pragma solidity ^ 0.7.0;

contract StorageProvider {

    //array of merkleTree hashes to form the proof
    struct proof {
        bytes[] hashes;
    }

    /*data struct;
    value is a dynamic byte array;
    exists = true when added by DO;
    proof contains hashes for verification by DU
    */
    struct data {
        bytes _value;
        bool _exists;
        proof _proof;
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

    //get from storage or request from provider
    function gGet(uint key) public view returns(data memory) {
        //get from storage if exists
        if(datastore[key]._exists){
            return datastore[key];
        }
        //otherwise request from off-chain storage
        else {
            return request(key);
        }
    }

    // fetch off-chain data
    function request(uint) internal pure returns (data memory) {
        //TODO: send request to off chain storage and return data + proof
        bytes memory v = "some_data";
        bytes[] memory h = new bytes[](1);
        h[0] = v;
        proof memory p = proof(h);
        return data(v, true, p);
    }


    //SP can use this to update the data
    function update(uint _key, bytes memory _value, bytes[] memory _proof) public onlyOwner {
        data memory d = data(_value, true, proof(_proof));
        datastore[_key] = d;
    }

    //SP can use this to delete some data
    function remove(uint key) public onlyOwner {
        delete datastore[key];
    }
}