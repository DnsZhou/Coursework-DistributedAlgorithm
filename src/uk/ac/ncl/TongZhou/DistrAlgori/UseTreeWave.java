package uk.ac.ncl.TongZhou.DistrAlgori;

import uk.ac.ncl.TongZhou.DistrAlgori.Entity.ServerNode;
import uk.ac.ncl.TongZhou.DistrAlgori.Entity.Tree;

/**
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created: 9 Oct 2018 17:15:56
 */
public class UseTreeWave {
	public static void main(String[] args) {
		ServerNode server0 = new ServerNode(0);
		ServerNode server1 = new ServerNode(1);
		ServerNode server2 = new ServerNode(2);
		ServerNode server3 = new ServerNode(3);
		ServerNode server4 = new ServerNode(4);
		ServerNode server5 = new ServerNode(5);
		ServerNode server6 = new ServerNode(6);
		ServerNode server7 = new ServerNode(7);

		Tree waveTree = new Tree(server0);
		waveTree.addRelation(server0, server1);
		waveTree.addRelation(server1, server2);
		waveTree.addRelation(server2, server3);
		waveTree.addRelation(server3, server4);
		waveTree.addRelation(server4, server5);
		waveTree.addRelation(server5, server6);
		waveTree.addRelation(server0, server7);
		waveTree.printTree();
	}

}
