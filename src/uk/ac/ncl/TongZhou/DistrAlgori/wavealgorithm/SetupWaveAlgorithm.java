
/**
 * @Description: 
 * @author: Tong Zhou b8027512@ncl.ac.uk
 * @created: 05:10 23-10-2018
 */
package uk.ac.ncl.TongZhou.DistrAlgori.wavealgorithm;

import uk.ac.ncl.TongZhou.DistrAlgori.Entity.ServerNode;
import uk.ac.ncl.TongZhou.DistrAlgori.Entity.Tree;

/**
 * @ClassName: SetupWaveAlgorithm
 * @Description:
 * 
 */
public class SetupWaveAlgorithm {

	static public Tree setUpBalancedBinaryTree() {
		ServerNode server0 = new ServerNode(0);
		ServerNode server1 = new ServerNode(1);
		ServerNode server2 = new ServerNode(2);
		ServerNode server3 = new ServerNode(3);
		ServerNode server4 = new ServerNode(4);
		ServerNode server5 = new ServerNode(5);
		ServerNode server6 = new ServerNode(6);
		ServerNode server7 = new ServerNode(7);
		ServerNode server8 = new ServerNode(8);

		Tree waveTree = new Tree(server0);
		waveTree.addRelation(server0, server1);
		waveTree.addRelation(server0, server2);
		waveTree.addRelation(server1, server3);
		waveTree.addRelation(server1, server4);
		waveTree.addRelation(server2, server5);
		waveTree.addRelation(server2, server6);
		waveTree.addRelation(server4, server7);
		waveTree.addRelation(server5, server8);
		waveTree.initializeTree();

		System.out.println("¡þ¡þ¡þBalancedBinaryTree generated as follow:");
		waveTree.printTree();

		return waveTree;
	}

	static public Tree setUpUnbalancedBinaryTree() {
		ServerNode server0 = new ServerNode(0);
		ServerNode server1 = new ServerNode(1);
		ServerNode server2 = new ServerNode(2);
		ServerNode server3 = new ServerNode(3);
		ServerNode server4 = new ServerNode(4);
		ServerNode server5 = new ServerNode(5);
		ServerNode server6 = new ServerNode(6);
		ServerNode server7 = new ServerNode(7);
		ServerNode server8 = new ServerNode(8);

		Tree waveTree = new Tree(server0);
		waveTree.addRelation(server0, server1);
		waveTree.addRelation(server0, server2);
		waveTree.addRelation(server1, server3);
		waveTree.addRelation(server1, server4);
		waveTree.addRelation(server4, server5);
		waveTree.addRelation(server4, server6);
		waveTree.addRelation(server6, server7);
		waveTree.addRelation(server7, server8);
		waveTree.initializeTree();

		System.out.println("¡þ¡þ¡þUnbalancedBinaryTree generated as follow:");
		waveTree.printTree();

		return waveTree;
	}

	static public Tree setUpArbitraryTree() {
		ServerNode server0 = new ServerNode(0);
		ServerNode server1 = new ServerNode(1);
		ServerNode server2 = new ServerNode(2);
		ServerNode server3 = new ServerNode(3);
		ServerNode server4 = new ServerNode(4);
		ServerNode server5 = new ServerNode(5);
		ServerNode server6 = new ServerNode(6);
		ServerNode server7 = new ServerNode(7);
		ServerNode server8 = new ServerNode(8);

		Tree waveTree = new Tree(server0);
		waveTree.addRelation(server0, server1);
		waveTree.addRelation(server0, server2);
		waveTree.addRelation(server0, server3);
		waveTree.addRelation(server0, server4);
		waveTree.addRelation(server0, server5);
		waveTree.addRelation(server0, server6);
		waveTree.addRelation(server0, server7);
		waveTree.addRelation(server0, server8);
		waveTree.initializeTree();

		System.out.println("¡þ¡þ¡þArbitraryTree generated as follow:");
		waveTree.printTree();

		return waveTree;
	}
}
