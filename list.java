public class list {
	private node head;
	private node tail;
	private int size;

	public list() {
		size = 0;
		head = new node(-1,null,null);
		tail = new node(-2,null,null);
	}

	public int size() {
		return size;
	}

	public void insert(int value) {
		if(size == 0) {
			insertFirst(value);
			return;
		}
		else {
			node currNode = head;
			while(currNode.get_next() != null) {
				System.out.println(currNode.get_value());
				if(currNode.get_value() > value) break;
				else currNode = currNode.get_next();
			}
			insertBefore(currNode,value);
			return;
		}
	}

	public void insertFirst(int x) {
		if (size == 0) {
			node newNode = new node(x,head,tail);
			newNode.set_rank(1);
			head.set_next(newNode);
			tail.set_prev(newNode);
			size++;
		}
		else {
			node tempNode = head.get_next();
			node newNode = new node(x,head,tempNode);
			tempNode.set_prev(newNode);
			head.set_next(newNode);
			size++;
		}
	}

	public void insertBefore(node currNode,int value) {
		if(currNode == head.get_next()) {insertFirst(value); return;}
		node newNode = new node(value,currNode.get_prev(),currNode);
		if (currNode.get_prev() != null) currNode.get_prev().set_next(newNode);
		currNode.set_prev(newNode);
		size++;
	}

	public node find(int value) {
		if(size == 0) return null;
		node currNode = head;
		while(currNode != null) {
			if(currNode.get_value() == value) return currNode;
			currNode = currNode.get_next();
		}
		return currNode;
	}

	public String display() {
		String display = "[";
		node displayNode = head.get_next();
		if (size == 0) return "[]";
		for (int i = 0; i < size; i++) {
			display += " " + displayNode.get_value() + " ";
			displayNode = displayNode.get_next();
		}
		display += "] Size: " + size;
		return display;
	}
}