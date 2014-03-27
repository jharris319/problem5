public class node {
	private int value;
	//private int rank;
	private node prev;
	private node next;
	private node prevCol;
	private node nextCol;

	public node (int Value, node Prev, node Next) {
		value = Value;
		prev = Prev;
		next = Next;
	}

	public void set_value(int Value) {
		value = Value;
	}

	public void set_prev(node Prev) {
		prev = Prev;
	}

	public void set_next(node Next) {
		next = Next;
	}

	public void set_prevCol(node PrevCol) {
		prevCol = PrevCol;
	}

	public void set_nextcol(node NextCol) {
		nextCol = NextCol;
	}

	// public void set_rank(int Rank) {
	// 	rank = Rank;
	// }

	public int get_value() {
		return value;
	}

	public node get_prev() {
		return prev;
	}

	public node get_next() {
		return next;
	}

	public node get_prevCol() {
		return prevCol;
	}

	public node get_nextCol() {
		return nextCol;
	}

	// public int get_rank() {
	// 	return rank;
	// }
}
