public class list {
	private node head;
	private node tail;
	private int size;
	private int maxAllocation;
	private int maxRowSize;
	private int[] rowSize;

	public list() {
		size = 0;
		maxAllocation = 1;
		maxRowSize = 1;
		rowSize = new int[1000];
		head = new node(-1,null,null);
		tail = new node(-2,null,null);
		head.set_next(tail);
		tail.set_prev(head);
	}

	public int size() {return size;}

	public node get_start() {return head.get_next();}

	public node get_end() {return tail;}

	public void insert(int value) {
		int currentRow = 0;
		if (size == maxAllocation) resize(); //increment maxRowSize and maxAllocation = square rowSize
		node currNode = get_start();

		while (currNode.get_nextRow() != null) {
			if (currNode.get_nextRow().get_value() < value) {
				currNode = currNode.get_nextRow();
				currentRow++;
			}
			else break;
		}

		while (currNode.get_next() != null) {
			if (currNode.get_value() == value) {
				System.out.println("[Duplicates not allowed]");
				return;
			}
			if (currNode.get_value() > value) break;
			else currNode = currNode.get_next();
		}
		node newNode = new node(value, currNode.get_prev(), currNode);
		currNode.get_prev().set_next(newNode);
		currNode.set_prev(newNode);
		size++;
		rowSize[currentRow]++;
		while (true) {
			if (rowSize[currentRow] > maxRowSize) {
				node baseElement = newNode;
				while (baseElement.get_nextRow() == null && baseElement.get_prevRow() == null && baseElement.get_prev() != head)
					baseElement = baseElement.get_prev();
				fixRowSize(baseElement);
				rowSize[currentRow]--;
				rowSize[currentRow + 1]++;
				currentRow++;
			}
			else break;
		}
	}

	public void resize() {

		node currNode = get_start();
		for(int i = 0; i < maxRowSize; i++) {
			//first bit
			if (size > 2) {
				if (currNode.get_prevRow() == null) currNode.set_nextRow(currNode.get_nextRow().get_next());

				else {
					//second part
					currNode.get_nextRow().set_prevRow(currNode.get_nextRow().get_prev().get_prevRow());
					currNode.get_nextRow().get_prev().set_prevRow(null);
					currNode.get_nextRow().set_nextRow(currNode.get_nextRow().get_prev().get_nextRow());
					currNode.get_nextRow().get_prev().set_nextRow(null);
				}
				//move down
				if (currNode.get_nextRow() != null) currNode = currNode.get_nextRow();
				else return;
			}
		}
		maxRowSize++;
		maxAllocation = maxRowSize * maxRowSize;
	}

	public void fixRowSize(node baseElement) {
		node endElement = baseElement;
		for (int i = 0;i < maxRowSize;i++) {
			endElement = endElement.get_next();
		}
		baseElement.set_nextRow(endElement);
		endElement.set_prevRow(baseElement);
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

	public void stats() {
		System.out.println("Size: " + size);
		System.out.println("Max Row Size: " + maxRowSize);
		System.out.println("Max Allocation: " + maxAllocation);
		System.out.print("[ ");
		for (int i = 0; i <= maxRowSize; i++) {
			System.out.print(rowSize[i] + " ");
		}
		System.out.println("]");
	}

	public String display() {
		String display = "[";
		node displayNode = get_start();
		if (size == 0) return "[]";
		for (int i = 0; i < size; i++) {
			display += " " + displayNode.get_value() + " ";
			displayNode = displayNode.get_next();
		}
		display += "] Size: " + size;
		return display;
	}
}