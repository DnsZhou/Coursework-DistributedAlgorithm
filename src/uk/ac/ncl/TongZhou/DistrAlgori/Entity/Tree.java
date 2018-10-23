package uk.ac.ncl.TongZhou.DistrAlgori.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created: 9 Oct 2018 17:15:56
 */
public class Tree {
	private ServerNode rootNode;
	private List<ServerNode> allNodes;

	public Tree(ServerNode rootNodeServer) {
		allNodes = new ArrayList<>();
		rootNode = rootNodeServer;
		allNodes.add(rootNode);
	}

	public ServerNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(ServerNode rootNode) {
		this.rootNode = rootNode;
	}

	public void printTree() {
		printTree(this.rootNode, 0);
		System.out.println();
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

	public boolean addRelation(ServerNode fatherNode, ServerNode childNode) {
		boolean hasNode;
		hasNode = rootNode.hasNode(fatherNode);
		if (!hasNode)
			throw new IllegalArgumentException("Add relation failed: Could not find father node " + fatherNode);
		else {
			fatherNode.addChildren(childNode);
			allNodes.add(childNode);
		}
		return hasNode;
	}

	/**
	 * @Title: activateRandomNodes
	 * @Description: Activate random amounts(up to the amounts of all nodes) of
	 *               nodes to excute the algorithm
	 */
	public void activateRandomNodes() {
		Random amountRandom = new Random();
		int randServerAmount = amountRandom.nextInt(allNodes.size()) + 1;
		System.out.println(randServerAmount+" Nodes to be activate");
		List<ServerNode> randServers = new ArrayList<>();
		for (; randServerAmount > 0; randServerAmount--) {
			Random serverIdRandom = new Random();
			int randServerId;
			boolean duplicateFlag;
			do {
				duplicateFlag = false;
				randServerId = serverIdRandom.nextInt(allNodes.size());
				for (int id : randServers.stream().map(serverNode -> serverNode.getId()).collect(Collectors.toList())) {
					if (randServerId == id)
						duplicateFlag = true;
				}
			} while (duplicateFlag);
			randServers.add(this.findNodeById(randServerId));
		}
		randServers.forEach(ServerNode::activate);
	}

	private ServerNode findNodeById(final int id) {
		return allNodes.stream().filter(node -> node.getId() == id).findFirst().get();
	}

	public void iterateActivateRandomNodes(int iteratetimes) {
		for (int i = 1; i <= iteratetimes; i++) {
			System.out.print("¡þ¡þ¡þIteration " + i + " Start, ");
			activateRandomNodes();
			System.out.println();
		}
		System.out.println("\n");

	}
	
	public void initializeTree() {
		allNodes.forEach(node -> node.initializeRecp());
	}
}
