package fr.unice.polytech.pfce;

public class HeapMinNoArray {
	
	class Node implements Comparable<Node>{
		int val;
		Node left;
		Node right;
		Node parrent;
		boolean potato = false;
		@Override
		public int compareTo(Node o) {
			return val-o.val;
		}
		public Node(int n){
			val = n;
		}
		
		public void insert(Node n) {
			if(left==null) {
				left = n;
				n.parrent = this;
			}if (right==null) {
				right=n;
				n.parrent=this;
			}else if(potato) {
				left.insert(n);
				potato = !potato;
			}else {
				right.insert(n);
				potato = !potato;
			}
			
			percolate();
		}
		
		public void percolate() {
			/*if (right.val < left.val) {//It's not a sorted binary tree;
				tmp=right;
				right = left;
				left = tmp;
			}*/
			if(left!=null && left.val < val) {
				left.parrent=parrent;
				parrent = left;
				left = left.left;
				parrent.left=this;
				parrent.percolate();
				percolate();
			}
			if(right!=null && right.val < val) {
				right.parrent=parrent;
				parrent = right;
				right = right.right;
				parrent.right=this;
				parrent.percolate();
				percolate();
			}
			
		}
		public Node find(int n) {
			Node r = null;
			if(val==n) {
				return this;
			}
			if(left!=null) {
				r=left.find(n);
			}
			if(r==null && right!=null) {
				r=right.find(n);
			}
			return r;
		}
		
		public int findMax(int m) {
			int max = m;
			if(val > max) {
				max = val;
			}
			if(left!=null)
				max = left.findMax(max);
			if(right!=null)
				max = right.findMax(max);
			return max;
		}
		public int findMax() {
			int max = val;
			if(left!=null)
				max = left.findMax(max);
			if(right!=null)
				max = right.findMax(max);
			return max;
		}
		
	}
	Node root;
	public HeapMinNoArray() {
		
	}
	public HeapMinNoArray(int n) {
		root = new Node(n);
	}
	
	public void insert(int n) {
		if(root==null) {
			root = new Node(n);
			return;
		}
		root.insert(new Node(n));
	}
	
	private Node findNode(int n) {
		return root.find(n);
	}
	
	public void remove(int n) {
		Node toDelete = findNode(n);
		if(toDelete==null) {
			return;
		}
		if(toDelete.right==null && toDelete.left==null) {
			if(toDelete.parrent==null) {
				root = null;
				return;
			}
			if(toDelete.parrent.left==toDelete) {
				toDelete.parrent.left=null;
				return;
			}else {
				toDelete.parrent.right=null;
				return;
			}
		}
		while(toDelete.left != null && toDelete.right != null) {
			if(toDelete.left.val < toDelete.right.val) {
				toDelete.left.parrent=toDelete.parrent;
				toDelete.parrent = toDelete.left;
				toDelete.left = toDelete.left.left;
				toDelete.parrent.left=toDelete;
			}else {
				toDelete.right.parrent=toDelete.parrent;
				toDelete.parrent = toDelete.right;
				toDelete.right = toDelete.right.right;
				toDelete.parrent.right=toDelete;
			}
		}
		if(toDelete.right==null) {
			toDelete.left.parrent=toDelete.parrent;
			toDelete.parrent = toDelete.left;
			toDelete.left = toDelete.left.left;
			toDelete.parrent.left=null;
		}else if (toDelete.left==null) {
			toDelete.right.parrent=toDelete.parrent;
			toDelete.parrent = toDelete.right;
			toDelete.right = toDelete.right.right;
			toDelete.parrent.right=null;
		}
		root.percolate();
	}
	
	public int getMin() {
		return root.val;
	}
	
	public int getMax() {
		return root.findMax();
	}

}
