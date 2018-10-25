package uk.ac.ncl.tongzhou.distralgori.usealgorithm;

import uk.ac.ncl.tongzhou.distralgori.entity.Tree;

/**
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created: 9 Oct 2018 17:15:56
 */
public class UseWaveAlgorithm {
	public static void main(String[] args) {
		Tree balanceTree = SetupTree.setUpBalancedBinaryTree();
		balanceTree.iterateActivateRandomNodes(10);

		 Tree unbalanceTree = SetupTree.setUpUnbalancedBinaryTree();
		 unbalanceTree.iterateActivateRandomNodes(10);
		
		 Tree arbitraryTree = SetupTree.setUpArbitraryTree();
		 arbitraryTree.iterateActivateRandomNodes(10);

	}

}
