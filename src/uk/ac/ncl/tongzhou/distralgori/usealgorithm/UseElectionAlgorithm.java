
/**
 * @Description  
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created 20:26 25-10-2018
 */
package uk.ac.ncl.tongzhou.distralgori.usealgorithm;

import uk.ac.ncl.tongzhou.distralgori.entity.Tree;

/**
 * @ClassName UseElectionAlgorithm
 * @Description
 * 
 */
public class UseElectionAlgorithm {
	public static void main(String[] args) {
		Tree balanceTree = SetupTree.setUpBalancedBinaryTreeForElection();
		balanceTree.iterateActivateRandomNodesForElection(100);

		Tree unbalanceTree = SetupTree.setUpUnbalancedBinaryTreeForElection();
		unbalanceTree.iterateActivateRandomNodesForElection(100);

		Tree arbitraryTree = SetupTree.setUpArbitraryTreeForElection();
		arbitraryTree.iterateActivateRandomNodesForElection(100);

	}
}
