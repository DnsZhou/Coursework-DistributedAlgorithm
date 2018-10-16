package uk.ac.ncl.TongZhou.DistrAlgori.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Tong Zhou b8027512@ncl.ac.uk
 * @created: 9 Oct 2018 17:15:56
 */
public class ServerNode{
	private int id;
	private Map<Integer, Boolean> recp;
	private Object data;
	private ServerNode parent;
	private List<ServerNode> children;

	public ServerNode(int id) {
		this.id = id;
		this.children = new ArrayList<ServerNode>();
	}

	public int getId() {
		return id;
	}
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ServerNode getParent() {
		return parent;
	}

	public void setParent(ServerNode parent) {
		this.parent = parent;
	}

	public List<ServerNode> getChildren() {
		return children;
	}

	public void setChildren(List<ServerNode> children) {
		this.children = children;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setRecp(Map<Integer, Boolean> recp) {
		this.recp = recp;
	}

	@Override
	public String toString() {
		return "ServerNode [id=" + id + "]";
	}

	public Map<Integer, Boolean> getRecp() {
		return recp;
	}

	public boolean addToRecp(Integer serverId, Boolean value) {
		if (this.getRecp().get(serverId) != null) {
			this.getRecp().put(serverId, value);
			return true;
		} else
			return false;
	}

	public ServerNode findNode(ServerNode targetNode) {
		ServerNode tempNode;
		if (this == targetNode)
			return this;
		else
			for (ServerNode child : children) {
				tempNode = child.findNode(targetNode);
				if (tempNode != null)
					return tempNode;
			}
		return null;
	}

	public boolean hasChild() {
		return this.children != null && this.children.size() > 0;
	}

	public void addChildren(ServerNode child) {
		this.getChildren().add(child);
	}
	
	public void refreshRecp() {

	}
}
