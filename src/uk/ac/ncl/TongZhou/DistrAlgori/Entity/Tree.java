package uk.ac.ncl.TongZhou.DistrAlgori.Entity;

/**
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created: 9 Oct 2018 17:15:56
 */
public class Tree {
	private ServerNode rootNode;

	public Tree(ServerNode rootNodeServer) {
		rootNode = rootNodeServer;
	}

	public ServerNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(ServerNode rootNode) {
		this.rootNode = rootNode;
	}

	public void printTree() {
		printTree(this.rootNode, 0);
	}

	private static void printTree(ServerNode node, int iteration) {
		for (int i = 0; i < iteration; i++) {
			System.out.print("  |");
		}
		if (iteration > 0) {
			System.out.print("--");
		}
		System.out.print(node + "\n");
		if (node.hasChild()) {
			iteration++;
			for (ServerNode child : node.getChildren()) {
				printTree(child, iteration);
			}
		}
	}

	public boolean addRelation(ServerNode fatherNode, ServerNode childrenNode) {
		boolean hasNode;
		hasNode = rootNode.hasNode(fatherNode);
		if (!hasNode)
			throw new IllegalArgumentException("Add relation failed: Could not find father node " + fatherNode);
		else
			fatherNode.addChildren(childrenNode);
		return hasNode;
	}


}
