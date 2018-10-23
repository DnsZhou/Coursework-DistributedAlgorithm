package uk.ac.ncl.TongZhou.DistrAlgori.wavealgorithm;

import uk.ac.ncl.TongZhou.DistrAlgori.Entity.ServerNode;
import uk.ac.ncl.TongZhou.DistrAlgori.Entity.Tree;

/**
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created: 9 Oct 2018 17:15:56
 */
public class UseWaveAlgorithm {
	public static void main(String[] args) {
		Tree balanceTree = SetupWaveAlgorithm.setUpBalancedBinaryTree();
		balanceTree.iterateActivateRandomNodes(10);
		
//		Tree unbalanceTree = SetupWaveAlgorithm.setUpUnbalancedBinaryTree();
//		unbalanceTree.iterateActivateRandomNodes(10);
//		
//		Tree arbitraryTree = SetupWaveAlgorithm.setUpArbitraryTree();
//		arbitraryTree.iterateActivateRandomNodes(10);
		
	}

}
