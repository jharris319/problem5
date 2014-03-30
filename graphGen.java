import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class graphGen {

	public void makeDot(list L){
		if (L.size() == 0){
			System.out.println("Nothing to visualize");
			return;
		}
		try {
			File file = new File("./sqrt.dot");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			// Writes header for dot file
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			String dotHead = "digraph G {\n\trankdir = LR\n\tnode[shape = circle , height = .6, fixedsize = true];\n\tedge[dir=both];\n\n\t";
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(dotHead);
			// Call to fill dot file with pointers
			fillDot(L,bw);
			// Writes footer for dot file to close file
			String dotFoot = "\n}";
			bw.write(dotFoot);
			bw.close();
			if (System.getProperty("os.name").startsWith("Linux")){
				Runtime.getRuntime().exec("./qgraph-nix.sh sqrt");
			} else if (System.getProperty("os.name").startsWith("Mac")){
				Runtime.getRuntime().exec("./qgraph-mac.sh sqrt");
			} else {
				System.out.println("OS not supported for automatic visualization");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void fillDot(list L, BufferedWriter bw){
		String fill = "";
		if (L.size() == 1) fill += L.get_start().get_value() + ";\n";
		node currNode = L.get_start();
		while (currNode != L.get_end()){
			fill += currNode.get_value();
			if (currNode.get_next() == L.get_end()) {
				fill += ";\n";
				break;
			}
			if (currNode.get_next().get_prevRow() != null){
				fill += ";\n\t";
				fill += currNode.get_value() + " -> " + currNode.get_next().get_value() +" [constraint=false];\n\t";
				fill += currNode.get_next().get_prevRow().get_value() + " -> " + currNode.get_next().get_value() +" [constraint=false];\n\t";
			} else fill += " -> ";
			currNode = currNode.get_next();
		}
		try {
			bw.write(fill);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
