import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;



class graphGen {
	// public static void main (String[] args){
	// 	genList();
	// 	makeDot();
	// }

	public static void genList(list L){
		for(int i = 1; i < 10; i++){
			L.insert(i);
		}
		return;
	}

	public static void makeDot(node currNode){
		try {
			File file = new File("./sqrt.dot");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			// Writes header for dot file
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			String dotHead = "digraph G {\n\trankdir = LR\n\tnode[shape = circle , height = .6, fixedsize = true];\n\n\t";
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(dotHead);
			// Call to fill dot file with pointers
			fillDot(currNode,bw);
			// Writes footer for dot file to close file
			String dotFoot = "\n}";
			bw.write(dotFoot);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void fillDot(node currNode, BufferedWriter bw){
		while (currNode.get_next() != null && currNode.get_next().get_nextCol() == null){
			String fill = "";
			if (currNode.get_next().get_value() != -2){fill = Integer.toString(currNode.get_value()) + " -> ";}
			else fill = Integer.toString(currNode.get_value()) + ";";
			if (currNode.get_next() != null) currNode = currNode.get_next();
			
			try {
			// String content = "\tThis is the content to write into file\n";
			bw.write(fill);
			System.out.println("Done");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
