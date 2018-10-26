package uk.ac.ncl.tongzhou.distralgori.usealgorithm;

import uk.ac.ncl.tongzhou.distralgori.entity.Tree;

/**
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created: 9 Oct 2018 17:15:56
 */
public class UseWaveAlgorithm {
	public static void main(String[] args) {
		Tree balanceTree = SetupTree.setUpBalancedBinaryTreeForWave();
		balanceTree.iterateActivateRandomNodes(100);

		 Tree unbalanceTree = SetupTree.setUpUnbalancedBinaryTreeForWave();
		 unbalanceTree.iterateActivateRandomNodes(100);
		
		 Tree arbitraryTree = SetupTree.setUpArbitraryTreeForWave();
		 arbitraryTree.iterateActivateRandomNodes(100);

	}

}
