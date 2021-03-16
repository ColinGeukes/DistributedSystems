// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.8.2 <0.9.0;
pragma abicoder v2;
pragma experimental ABIEncoderV2;

/**
 * @title Storage
 * @dev Store & retrieve value in a variable
 */
contract Storage {

	struct VerifiedOutput {
		bool valid;
		bytes value;
	}

	uint256 number;

	bytes32 latestDigest;

	/**
     * @dev Store value in variable
     * @param num value to store
     */
	function store(uint256 num) public {
		number = num;
	}

	/**
     * @dev Return value
     * @return value of 'number'
     */
	function retrieve() public view returns (uint256){
		return number;
	}

	function updateDigest(bytes32 digest) public {
		latestDigest = digest;
	}

	function verify(bytes8 key, bytes memory proof) public view returns (VerifiedOutput memory) {
		// labelLength = 8
		VerifiedOutput memory ret;
		uint8 rootNodeHeight = toUint8(latestDigest, 31);
		uint16 maxNodes = 2 * rootNodeHeight + 2;
		uint16 numNodes = 0;
		uint8 labelsHead = 0;
		bytes32[4] memory labels;
		uint256 i = 0;
		bool valueExists = false;
		while (toInt8(proof, i) != 4 && i < proof.length) {
			int8 n = toInt8(proof, i);
			i += 1;
			numNodes += 1;

			if (numNodes > maxNodes) {
				ret.valid = false;
				return ret;
			}

			if (n == 3) { // Label
				labels[labelsHead] = toLabel(proof, i);
				labelsHead += 1;
				i += 32;
			} else if (n == 2) { // Leaf
				if (valueExists) {
					ret.valid = false;
					return ret;
				}
				bytes8 leafKey = toLeafKey(proof, i);
				i += 8;
				if (leafKey != key) {
					ret.valid = false;
					return ret;
				}
				bytes8 nextLeafKey = toLeafKey(proof, i);
				i += 8;
				uint32 valueLength = toUint32(proof, i);
				i += 4;
				bytes memory value = new bytes(valueLength);
				assembly {
					value := mload(add(proof, i))
				}
				i += valueLength;
				ret.value = value;
				valueExists = true;
				labels[labelsHead] = keccak256(abi.encodePacked(int8(0), leafKey, value, nextLeafKey));
				labelsHead += 1;
			} else { // Internal node
				bytes32 labelLeft = labels[labelsHead - 1];
				labelsHead -= 1;
				bytes32 labelRight = labels[labelsHead - 1];
				labelsHead -= 1;
				labels[labelsHead] = keccak256(abi.encodePacked(int8(1), n, labelLeft, labelRight));
				labelsHead += 1;
			}
		}
		if (labelsHead == 1) {
			if (labels[0] == latestDigest) {
				ret.valid = true;
				return ret;
			}
		}

		ret.valid = false;
		return ret;
	}

	function toLeafKey(bytes memory input, uint offset) private pure returns(bytes8 output) {
		assembly {
			output := mload(add(input, offset))
		}
	}

	function toLabel(bytes memory input, uint offset) private pure returns(bytes32 output) {
		assembly {
			output := mload(add(input, offset))
		}
	}

	function toInt8(bytes memory input, uint offset) private pure returns (int8 output) {
		assembly {
			output := mload(add(input, offset))
		}
	}

	function toUint8(bytes32 input, uint offset) private pure returns (uint8 output) {
		assembly {
			output := mload(add(input, offset))
		}
	}

	function toUint32(bytes memory input, uint offset) private pure returns (uint32 output) {
		assembly {
			output := mload(add(input, offset))
		}
	}
}