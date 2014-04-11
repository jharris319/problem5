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
		head = new node(-1, null, null);
		tail = new node(-2, null, null);
		head.set_next(tail);
		tail.set_prev(head);
	}

	public int size() {
		return size;
	}

	public node get_start() {
		return head.get_next();
	}

	public node get_end() {
		return tail.get_prev();
	}

	public void genList(int flag) throws InterruptedException {
		for (int i = 0; i < flag; i++) {
			insert(i * 10);
			if (flag <= 50) {
				problem5.menu();
				System.out.println(display());
				Thread.sleep(120);
			}
		}
		return;
	}

	public int insert(int value) {
		// Start at [0,0]
		int currentRow = 0;
		node currNode = get_start();

		// Hop skip pointers until we are at the correct row
		while (currNode.get_nextRow() != null) {
			if (currNode.get_nextRow().get_value() < value) {
				currNode = currNode.get_nextRow();
				currentRow++;
			} else break;
		}

		// Search our currentRow for the insert location
		while (currNode.get_next() != null) {
			if (currNode.get_value() == value) {
				System.out.println("\nDuplicates not allowed]");
				return -1;
			}
			if (currNode.get_value() > value) break;
			else currNode = currNode.get_next();
		}

		// Resize when the data structure is a max capacity
		if (size == maxAllocation) {
			maxRowSize++;
			maxAllocation = maxRowSize * maxRowSize;
			//System.out.println("We grew to allow for " + maxAllocation + " nodes!");
		}

		node prevNode = currNode.get_prev();
		node newNode = new node(value,prevNode,currNode);
		prevNode.set_next(newNode);
		currNode.set_prev(newNode);
		size++;
		rowSize[currentRow]++;
		overflow();
		return value;
	}

	public void overflow() {
		// Start at [0,0]
		int currentRow = 0, overflowRow = 0;
		boolean overflow = false;
		node currNode = get_start();

		if (currNode.get_nextRow() == null && size > 1) {
			if (currNode.get_next().get_nextRow() != null) {
				currNode.set_nextRow(currNode.get_next().get_nextRow());
				currNode.get_next().get_nextRow().set_prevRow(currNode);
				currNode.get_next().set_nextRow(null);
			}
		}

		// Check to see if a row is overflowing
		while (currentRow < maxRowSize) {
			if (rowSize[currentRow] > maxRowSize) {
				overflowRow = currentRow;
				overflow = true;
				break;
			}
			currentRow++;
			if (currNode.get_nextRow() != null) currNode = currNode.get_nextRow();
			else break;
		}
		if (overflow) {
			// Determine shift type
			for (int i = overflowRow + 1; i < maxRowSize; i++) {
				// If if any row after the overflow row
				// has an available space, just shift down
				if (rowSize[i] != maxRowSize) {
					shiftDown(overflowRow, currNode);
					return;
				}
			}
			// If all successive rows are full we cannot
			// shift down, so shift up
			shiftUp(overflowRow, currNode);
		}
	}

	public void shiftUp(int currentRow,  node baseNode) {
		node newBaseNode = baseNode.get_next();
		newBaseNode.set_prevRow(baseNode.get_prevRow());
		newBaseNode.set_nextRow(baseNode.get_nextRow());
		baseNode.get_prevRow().set_nextRow(newBaseNode);
		if (baseNode.get_nextRow() != null) baseNode.get_nextRow().set_prevRow(newBaseNode);
		baseNode.set_prevRow(null);
		baseNode.set_nextRow(null);
		rowSize[currentRow - 1]++;
		rowSize[currentRow]--;
		if (rowSize[currentRow - 1] > maxRowSize) shiftUp(currentRow - 1, newBaseNode.get_prevRow());
	}

	public void shiftDown(int currentRow,  node baseNode) {
		node newBaseNode;
		node oldBaseNode;
		if (baseNode.get_nextRow() != null) {
			oldBaseNode = baseNode.get_nextRow();
			newBaseNode = baseNode.get_nextRow().get_prev();
			newBaseNode.set_nextRow(oldBaseNode.get_nextRow());
			if (oldBaseNode.get_nextRow() != null) oldBaseNode.get_nextRow().set_prevRow(newBaseNode);
			oldBaseNode.set_prevRow(null);
			oldBaseNode.set_nextRow(null);
		}
		else newBaseNode = get_end();
		newBaseNode.set_prevRow(baseNode);
		baseNode.set_nextRow(newBaseNode);
		rowSize[currentRow]--;
		rowSize[currentRow + 1]++;
		if (rowSize[currentRow + 1] > maxRowSize) shiftDown(currentRow + 1, baseNode.get_nextRow());
	}

	public int delete(int value) {
		// Start at [0,0]
		int currentRow = 0;
		node delNode = get_start();

		// Hop skip pointers until we are at the correct row
		while (delNode.get_nextRow() != null) {
			if (delNode.get_nextRow().get_value() <= value) {
				delNode = delNode.get_nextRow();
				currentRow++;
			} else break;
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
			System.out.println("\n[Value not found]");
			return -1;
		}

		// Check to see if there is only one item in the row
		// if so, remove the row
		if (rowSize[currentRow] == 1) {
			if (delNode.get_prevRow() != null) delNode.get_prevRow().set_nextRow(delNode.get_nextRow());
			if (delNode.get_nextRow() != null) delNode.get_nextRow().set_prevRow(delNode.get_prevRow());
		}

		else if (delNode.get_prevRow() != null || delNode.get_nextRow() != null) {
			node newBaseNode = delNode.get_next();
			newBaseNode.set_prevRow(delNode.get_prevRow());
			newBaseNode.set_nextRow(delNode.get_nextRow());
			if (delNode.get_prevRow() != null) delNode.get_prevRow().set_nextRow(newBaseNode);
			if (delNode.get_nextRow() != null) delNode.get_nextRow().set_prevRow(newBaseNode);
		}
		delNode.get_prev().set_next(delNode.get_next());
		delNode.get_next().set_prev(delNode.get_prev());
		delNode = null;
		rowSize[currentRow]--;
		size--;

		//Shift rows up if we deleted last item in row
		if (rowSize[currentRow] == 0) {
			for (int i = currentRow; i < maxRowSize; i++) {
				rowSize[i] = rowSize[i + 1];
			}
		}

		// Shrink data structure if needed
		double sqrt = Math.sqrt((float) size);
		if (sqrt % (int) sqrt == 0) shrink();

		return value;
	}

	public void shrink() {
		maxRowSize--;
		maxAllocation = maxRowSize * maxRowSize;
		//System.out.println("We shrunk to allow for " + maxAllocation + " nodes!");

		// Start at [0,0]
		int currentRow = 0;
		node currNode = get_start();

		// Checking to see if number of nodes meets max row size allocation
		while (currentRow < maxRowSize) {

			// pull nodes from below
			if (rowSize[currentRow] < maxRowSize) {
				node oldBaseNode = currNode.get_nextRow();
				if (rowSize[currentRow + 1] == 1) {
					if (oldBaseNode.get_prevRow() != null) oldBaseNode.get_prevRow().set_nextRow(oldBaseNode.get_nextRow());
					if (oldBaseNode.get_nextRow() != null) oldBaseNode.get_nextRow().set_prevRow(oldBaseNode.get_prevRow());

				}
				else {
					node newBaseNode = oldBaseNode.get_next();
					newBaseNode.set_prevRow(oldBaseNode.get_prevRow());
					newBaseNode.set_nextRow(oldBaseNode.get_nextRow());
					if (oldBaseNode.get_prevRow() != null) oldBaseNode.get_prevRow().set_nextRow(newBaseNode);
					if (oldBaseNode.get_nextRow() != null) oldBaseNode.get_nextRow().set_prevRow(newBaseNode);
				}
				oldBaseNode.set_prevRow(null);
				oldBaseNode.set_nextRow(null);
				rowSize[currentRow]++;
				rowSize[currentRow + 1]--;

				//Shift rows up if we deleted last item in row
				if (rowSize[currentRow + 1] == 0) {
					for (int i = currentRow + 1; i < maxRowSize; i++) {
						rowSize[i] = rowSize[i + 1];
					}
				}
			}

			// push nodes to next row
			else if (rowSize[currentRow] > maxRowSize) {
				node oldBaseNode = currNode.get_nextRow();
				if (rowSize[currentRow + 1] == 1) {
					if (oldBaseNode.get_prevRow() != null) oldBaseNode.get_prevRow().set_nextRow(oldBaseNode.get_nextRow());
					if (oldBaseNode.get_nextRow() != null) oldBaseNode.get_nextRow().set_prevRow(oldBaseNode.get_prevRow());
				}

				node newBaseNode = oldBaseNode.get_prev();
				newBaseNode.set_prevRow(oldBaseNode.get_prevRow());
				newBaseNode.set_nextRow(oldBaseNode.get_nextRow());
				if (oldBaseNode.get_prevRow() != null) oldBaseNode.get_prevRow().set_nextRow(newBaseNode);
				if (oldBaseNode.get_nextRow() != null) oldBaseNode.get_nextRow().set_prevRow(newBaseNode);
				oldBaseNode.set_prevRow(null);
				oldBaseNode.set_nextRow(null);
				rowSize[currentRow]--;
				rowSize[currentRow + 1]++;

			}

			if (rowSize[currentRow] == maxRowSize) {
				currentRow++;
				currNode = currNode.get_nextRow();
			}
		}
	}

	public node find(int value) {
		if (size == 0) return null;
		node currNode = get_start();

		// Hop skip pointers until we are at the correct row
		while (currNode.get_nextRow() != null) {
			if (currNode.get_nextRow().get_value() < value) {
				currNode = currNode.get_nextRow();
			} else break;
		}
		// Search our currentRow for the desired value
		while (currNode.get_next() != null) {
			if (currNode.get_value() == value) {
				return currNode;
			} else if (currNode.get_value() > value) {
				return null;
			} else currNode = currNode.get_next();
		}
		return null;
	}

	public void stats() {
		System.out.println("\nSize: " + size);
		System.out.println("Max Row Size: " + maxRowSize);
		System.out.println("Max Allocation: " + maxAllocation);
		System.out.print("Row Sizes: ");
		System.out.print("[ ");
		for (int i = 0; i < maxRowSize; i++) {
			System.out.print(rowSize[i] + " ");
		}
		System.out.println("]");
	}

	public void guide() {
		int prevAllocation = (maxRowSize - 1) * (maxRowSize - 1);
		int nextAllocation = (maxRowSize + 1) * (maxRowSize + 1);
		System.out.println("\nPrev Allocation: " + prevAllocation);
		System.out.println("Next Allocation: " + nextAllocation);
		System.out.println("Will shrink after " + (size - prevAllocation) + " deletions");
		System.out.println("Will grow after " + (nextAllocation - size) + " insertions");
	}

	public void verify() {
		// Start at [0,0]
		int currentRow = 0;
		int itemCount = 0;
		boolean firstRow = true;
		node currNode = get_start();
		while (currNode != tail) {
			itemCount++;
			if (currNode.get_nextRow() != null || currNode.get_prevRow() != null) {
				if (!firstRow) {
					if (itemCount != rowSize[currentRow]) {
						System.out.println("[Anomaly - Aborting]");
						return;
					}
					itemCount = 0;
					currentRow++;
				}
				if (firstRow) {
					firstRow = false;
					itemCount--;
				}
			}
			currNode = currNode.get_next();
		}
		System.out.println("\n[Verified Successfully]");
	}

	public String display() {
		String display = "";
		int currentRow = 0;
		node displayNode = get_start();
		if (size == 0) return "\n[Data Structure Empty]";
		for (int i = 0; i < size; i++) {
			if (displayNode.get_prevRow() != null || displayNode.get_nextRow() != null) {
				display += "\n" + rowSize[currentRow] + " |\t";
				currentRow++;
			}
			display += displayNode.get_value() + "\t";
			displayNode = displayNode.get_next();
		}
		display += "\n\n[Size: " + size + "]";
		return display;
	}
}

