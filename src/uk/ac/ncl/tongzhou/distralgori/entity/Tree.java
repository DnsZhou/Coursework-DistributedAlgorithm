package uk.ac.ncl.tongzhou.distralgori.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created: 9 Oct 2018 17:15:56
 */
public class Tree {
	private WaveNode rootNode;
	private List<WaveNode> allNodes;

	public Tree(WaveNode rootNodeServer) {
		allNodes = new ArrayList<>();
		rootNode = rootNodeServer;
		allNodes.add(rootNode);
	}

	public WaveNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(WaveNode rootNode) {
		this.rootNode = rootNode;
	}

	public void printTree() {
		printTree(this.rootNode, 0);
		System.out.println();
	}

	private static void printTree(WaveNode node, int iteration) {
		for (int i = 0; i < iteration; i++) {
			System.out.print("  |");
		}
		if (iteration > 0) {
			System.out.print("--");
		}
		System.out.print(node + "\n");
		if (node.hasChild()) {
			iteration++;
			for (WaveNode child : node.getChildren()) {
				printTree(child, iteration);
			}
		}
	}

	public boolean addRelation(WaveNode fatherNode, WaveNode childNode) {
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
		activateRandomNodes(false);
	}

	public void activateRandomNodes(boolean isInitiator) {
		Random amountRandom = new Random();
		int randServerAmount = amountRandom.nextInt(allNodes.size()) + 1;

		List<WaveNode> randServers = new ArrayList<>();
		for (int i = 0; i < randServerAmount; i++) {
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
		if (isInitiator) {
			System.out.print(randServerAmount + " Nodes selected as initiator: ");
			randServers.forEach(node -> System.out.print(node.getId() + ", "));
			System.out.print("\n");
			randServers.forEach(node -> ((ElectionNode) node).activate(true));
		}

		else {
			System.out.print(randServerAmount + " Nodes to be activated: ");
			randServers.forEach(node -> System.out.print(node.getId() + ", "));
			System.out.print("\n");
			randServers.forEach(WaveNode::activate);
		}
	}

	private WaveNode findNodeById(final int id) {
		return allNodes.stream().filter(node -> node.getId() == id).findFirst().get();
	}

	public int iterateActivateRandomNodes(int iteratetimes) {
		for (int i = 1; i <= iteratetimes; i++) {
			System.out.print("====Iteration " + i + " Start, ");
			activateRandomNodes();
			System.out.println();
			if (this.allNodes.stream().filter(node -> node.status == StatusType.WAVE_DECIDE).count() == 2) {
				System.out.println("Algorithm finished.\n");
				return i;
			}
		}
		System.out.println("\n");
		return 0;
	}

	public int iterateActivateRandomNodesForElection(int iteratetimes) {
		for (int i = 1; i <= iteratetimes; i++) {
			System.out.print("====Iteration " + i + " Start, ");
			activateRandomNodes(i == 1);
			System.out.println();
			if (this.allNodes.stream().filter(
					node -> node.status == StatusType.ELECTION_LEADER || node.status == StatusType.ELECTION_LOST)
					.count() == this.allNodes.size()) {
				System.out.println("Algorithm finished.\n");
				return i;
			}
		}
		System.out.println("\n");
		return 0;
	}

	public void initializeTree() {
		allNodes.forEach(node -> node.initializeRecp());
	}
}
