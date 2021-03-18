// SPDX-License-Identifier: GPL-3.0

pragma solidity ^0.8.2;

// client contract implements deliver callback function to receive requested values
contract StorageProvider {
    // a deliver event with indexed key and value to filter for
    event deliver(bytes8 indexed key, bytes proof);

    function emitDeliver(bytes8 key, bytes memory proof) public {
        emit deliver(key, proof);
    }
}
