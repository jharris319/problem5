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

	public boolean isFirst(node tempNode) {
		if (size == 0) return false;
		return (tempNode.get_value() == head.get_value());
	}

	public boolean isLast(node tempNode) {
		if (size == 0) return false;
		return (tempNode.get_value() == tail.get_value());
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

	public void insertLast(int x) {
		if (size == 0) {
			node newNode = new node(x,head,tail);
			size++;
		}
		else {
			node tempNode = tail.get_prev();
			node newNode = new node(x,tempNode,tail);
			tempNode.set_next(newNode);
			size++;
		}
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

	public void insertBefore(node currNode,int value) {
		if(currNode == head.get_next()) {insertFirst(value); return;}
		node newNode = new node(value,currNode.get_prev(),currNode);
		if (currNode.get_prev() != null) currNode.get_prev().set_next(newNode);
		currNode.set_prev(newNode);
		size++;
	}

//---CURRENT PROGRESS LINE---//

	// public void insertAfter(int x) {
	// 	if (size == 0) insertLast(x);
	// 	if (currNode == null) return;
	// 	node newNode = new node(x,currNode,currNode.get_next());
	// 	if (currNode.get_next() != null) currNode.get_next().set_prev(newNode);
	// 	currNode.set_next(newNode);
	// 	size++;
	// }

	// public void replace(int x) {
	// 	if (currNode != null) currNode.set_value(x);
	// }

	// public int removeFirst() {
	// 	node tempNode = head.get_next().get_next();
	// 	if (currNode == head.get_next()) currNode = null;
	// 	int tempValue = head.get_next().get_value();
	// 	head.set_next(head.get_next().get_next());
	// 	tempNode.set_prev(head);
	// 	size--;
	// 	if (internalRank >= 0) internalRank--;
	// 	if (currNode == null) internalRank = 0;
	// 	return tempValue;
	// }

	// public int removeLast() {
	// 	node tempNode = tail.get_prev().get_prev();
	// 	if (currNode == tail.get_prev()) currNode = null;
	// 	int tempValue = tail.get_prev().get_value();
	// 	tail.set_prev(tail.get_prev().get_prev());
	// 	tempNode.set_next(tail);
	// 	if (currNode == null) internalRank = 0;
	// 	size--;
	// 	return tempValue;
	// }

	// public int remove() {
	// 	int tempValue = currNode.get_value();
	// 	currNode.get_next().set_prev(currNode.get_prev());
	// 	currNode.get_prev().set_next(currNode.get_next());
	// 	size--;
	// 	currNode = null;
	// 	internalRank = 0;
	// 	return tempValue;
	// }

	// public int removeAndRetreat() {
	// 	int tempValue = currNode.get_value();
	// 	node tempNode = currNode.get_prev();
	// 	currNode.get_next().set_prev(currNode.get_prev());
	// 	currNode.get_prev().set_next(currNode.get_next());
	// 	internalRank--;
	// 	if (currNode == null) internalRank = 0;
	// 	size--;
	// 	currNode = tempNode;
	// 	return tempValue;
	// }

	// public int removeAndAdvance() {
	// 	if (currNode == head) return 0;
	// 	if (currNode == tail) return 0;
	// 	if (currNode == null) return 0;
	// 	int tempValue = currNode.get_value();
	// 	node tempNode = currNode.get_next();
	// 	currNode.get_next().set_prev(currNode.get_prev());
	// 	currNode.get_prev().set_next(currNode.get_next());
	// 	if (currNode == null) internalRank = 0;
	// 	size--;
	// 	currNode = tempNode;
	// 	return tempValue;
	// }

	public String display() { // blah
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