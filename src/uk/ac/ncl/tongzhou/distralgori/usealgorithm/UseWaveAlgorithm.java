
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
public class UseWaveAlgorithm {
	public static void main(String[] args) {
		System.out.println("========Start Wave Algorithm");
		Tree balanceTree = SetupTree.setUpBalancedBinaryTreeForWave();
		balanceTree.iterateActivateRandomNodes(100);

		Tree unbalanceTree = SetupTree.setUpUnbalancedBinaryTreeForWave();
		unbalanceTree.iterateActivateRandomNodes(100);

		Tree arbitraryTree = SetupTree.setUpArbitraryTreeForWave();
		arbitraryTree.iterateActivateRandomNodes(100);

	}

}
