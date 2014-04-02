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

	public void genList(int flag){
		for(int i = 1; i <= flag; i++){
			insert(i);
		}
		return;
	}

	public void insert(int value) {
		// Start at [0,0]
		int currentRow = 0;
		node currNode = get_start();

		// Resize when the data structure is a max capacity
		if (size == maxAllocation) grow();

		// Hop skip pointers until we are at the correct row
		while (currNode.get_nextRow() != null) {
			if (currNode.get_nextRow().get_value() < value) {
				currNode = currNode.get_nextRow();
				currentRow++;
			}
			else break;
		}
		// Search our currentRow for the insert location
		while (currNode.get_next() != null) {
			if (currNode.get_value() == value) {
				System.out.println("[Duplicates not allowed]");
				return;
			}
			if (currNode.get_value() > value) break;
			else currNode = currNode.get_next();
		}
		// Create a new node set pointers
		node newNode = new node(value, currNode.get_prev(), currNode);
		currNode.get_prev().set_next(newNode);
		currNode.set_prev(newNode);
		size++;
		rowSize[currentRow]++;
		// Check if currentRow is now too large and correct if needed.
		// This may cascade down rows, so we'll need to check each row
		// until we no longer overflow.
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

	public void delete(int value) {
		// Start at [0,0]
		int currentRow = 0;
		node delNode = get_start();

		// Update rowSize to reflect deletion
		node traceNode = get_start();
		while (traceNode != null) {
			if (traceNode.get_nextRow() != null) {
				traceNode = traceNode.get_nextRow();
				currentRow++;
			}
			else break;
		}
		rowSize[currentRow]--;

		// Hop skip pointers until we are at the correct row
		while (delNode.get_nextRow() != null) {
			if (delNode.get_nextRow().get_value() < value) {
				delNode = delNode.get_nextRow();
			}
			else break;
		}
		// Search our currentRow for the delete node
		while (delNode.get_next() != null) {
			if (delNode.get_value() == value) {
				break;
			}
			if (delNode.get_value() > value) break;
			else delNode = delNode.get_next();
		}
		if (delNode.get_value() != value) {
			System.out.println("[Node not found]");
			return;
		}

		node baseNode = delNode;
		if (baseNode.get_prevRow() != null || baseNode.get_nextRow() != null) {
			//delNode is the baseNode
			node newBaseNode = baseNode.get_next();
			if (baseNode.get_prevRow() != null) {
				baseNode.get_prevRow().set_nextRow(newBaseNode);
			}
			if (baseNode.get_nextRow() != null) {
				baseNode.get_nextRow().set_prevRow(newBaseNode);
			}
			newBaseNode.set_prevRow(baseNode.get_prevRow());
			newBaseNode.set_nextRow(baseNode.get_nextRow());
			newBaseNode.set_prev(baseNode.get_prev());
			baseNode.get_prev().set_next(newBaseNode);
			baseNode = newBaseNode;
		}
		else {
			while (baseNode.get_prevRow() == null && baseNode.get_nextRow() == null) {
				baseNode = baseNode.get_prev();
			}
		}

		//Found baseNode on row
		while (baseNode.get_nextRow() != null) {
			node oldBaseNode = baseNode.get_nextRow();
			if (oldBaseNode.get_next() != tail) {
				node newBaseNode = oldBaseNode.get_next();
				newBaseNode.set_prevRow(oldBaseNode.get_prevRow());
				newBaseNode.set_nextRow(oldBaseNode.get_nextRow());
				baseNode.set_nextRow(newBaseNode);
				if (oldBaseNode.get_nextRow() != null) {
					oldBaseNode.get_nextRow().set_prevRow(newBaseNode);
				}
			}
			else {
				baseNode.set_nextRow(null);
			}
			oldBaseNode.set_prevRow(null);
			oldBaseNode.set_nextRow(null);
			if (baseNode.get_nextRow() != null) {
				baseNode = baseNode.get_nextRow();
			}
		}

		//just delete the node
		delNode.get_prev().set_next(delNode.get_next());
		if (delNode.get_next() != tail){
			delNode.get_next().set_prev(delNode.get_prev());
		}
		delNode.set_prev(null);
		delNode.set_next(null);
		delNode = null;
		size--;

		// Shrink data structure if needed
		double sqrt = Math.sqrt((float)size);
		if (sqrt % (int)sqrt == 0) shrink();


	}

	public void grow() {
		int currentRow = 0;
		node currNode = get_start();
		node oldBaseNode, newBaseNode;
		for(int i = 0; i < maxRowSize; i++) {
			if (size > 1) { // Skip if only 1 item
				// If we're at the bottom row, break out
				if (currNode.get_nextRow() == null) break;
				if (currNode.get_prevRow() == null) { // First row
					oldBaseNode = currNode.get_nextRow();
					newBaseNode = oldBaseNode.get_next();
					// Adjust pointers
					currNode.set_nextRow(newBaseNode);
					newBaseNode.set_prevRow(currNode);
					newBaseNode.set_nextRow(oldBaseNode.get_nextRow());
					oldBaseNode.set_prevRow(null);
					oldBaseNode.set_nextRow(null);
					rowSize[currentRow]++;
					rowSize[currentRow + 1]--;
				}
				else { // All other rows
					newBaseNode = currNode.get_nextRow();
					oldBaseNode = newBaseNode;
					// Find the new baseNode
					for (int j = 0; j <= currentRow; j++) {
						newBaseNode = newBaseNode.get_next();
					}
					// Now adjust pointers
					newBaseNode.set_nextRow(currNode.get_nextRow().get_nextRow());
					currNode.get_nextRow().set_prevRow(null);
					currNode.set_nextRow(newBaseNode);
					newBaseNode.set_prevRow(currNode);
					oldBaseNode.set_prevRow(null);
					oldBaseNode.set_nextRow(null);
					rowSize[currentRow] += currentRow + 1;
					rowSize[currentRow + 1] -= currentRow + 1;
				}
				// Move to the next row
				if (currNode.get_nextRow() != null) {
					currNode = currNode.get_nextRow();
					currentRow++;
				}
			}
		}
		// Done restructuring, adjust size variables
		maxRowSize++;
		maxAllocation = maxRowSize * maxRowSize;
	}

	public void shrink() {
		int currentRow = 0;
		node currNode = get_start();
		node oldBaseNode, newBaseNode;
		// Update rowSize to reflect deletion
		node traceNode = get_start();
		int count = 0;
		while (traceNode != null) {
			if (traceNode.get_nextRow() != null) {
				traceNode = traceNode.get_nextRow();
				rowSize[count]--;
				count++;
			}
			else break;
		}
		rowSize[count] = count + 1;
		for (int i = 0; i < maxRowSize - 1; i++) {
			// If we're at the bottom row, break out
			if (currNode.get_nextRow() == null) break;
			if (currNode.get_prevRow() == null) { // First row
				oldBaseNode = currNode.get_nextRow();
				newBaseNode = oldBaseNode.get_prev();
				// Adjust pointers
				currNode.set_nextRow(newBaseNode);
				newBaseNode.set_prevRow(currNode);
				newBaseNode.set_nextRow(oldBaseNode.get_nextRow());
				oldBaseNode.set_prevRow(null);
				oldBaseNode.set_nextRow(null);
			}
			else { // All other rows
				newBaseNode = currNode.get_nextRow();
				oldBaseNode = newBaseNode;
				// Find the new baseNode
				for (int j = 0; j <= currentRow; j++) {
					newBaseNode = newBaseNode.get_prev();
				}
				// Now adjust pointers
				newBaseNode.set_nextRow(currNode.get_nextRow().get_nextRow());
				currNode.get_nextRow().set_prevRow(null);
				currNode.set_nextRow(newBaseNode);
				newBaseNode.set_prevRow(currNode);
				oldBaseNode.set_prevRow(null);
				oldBaseNode.set_nextRow(null);
			}
			// Move to the next row
			if (currNode.get_nextRow() != null) {
				currNode = currNode.get_nextRow();
				currentRow++;
			}
		}


		// Done restructuring, adjust size variables
		maxRowSize--;
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
		node currNode = get_start();

		// Hop skip pointers until we are at the correct row
		while (currNode.get_nextRow() != null) {
			if (currNode.get_nextRow().get_value() < value) {
				currNode = currNode.get_nextRow();
			}
			else break;
		}
		// Search our currentRow for the desired value
		while (currNode.get_next() != null) {
			if (currNode.get_value() == value) {
				return currNode;
			}
			else if (currNode.get_value() > value) {
				return null;
			}
			else currNode = currNode.get_next();
		}
		return null;
	}

	public void stats() {
		System.out.println("Size: " + size);
		System.out.println("Max Row Size: " + maxRowSize);
		System.out.println("Max Allocation: " + maxAllocation);
		System.out.print("[ ");
		for (int i = 0; i < maxRowSize; i++) {
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