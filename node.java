public class node {
	private int value;
	private node prev;
	private node next;
	private node prevRow;
	private node nextRow;

	public node (int Value, node Prev, node Next) {
		value = Value;
		prev = Prev;
		next = Next;
	}

	public void set_value(int Value) {value = Value;}

	public void set_prev(node Prev) {prev = Prev;}

	public void set_next(node Next) {next = Next;}

	public void set_prevRow(node PrevRow) {prevRow = PrevRow;}

	public void set_nextRow(node NextRow) {nextRow = NextRow;}

	public int get_value() {return value;}

	public node get_prev() {return prev;}

	public node get_next() {return next;}

	public node get_prevRow() {return prevRow;}

	public node get_nextRow() {return nextRow;}
}
