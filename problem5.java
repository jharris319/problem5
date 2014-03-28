import java.io.*;

public class problem5 {
	public static void main (String args[ ]) throws IOException {
		BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
		String command;
		int flag = 0;
		list L = new list();
		graphGen G = new graphGen();

		menu();
		while(true) {
			System.out.print("At your command: ");
			command = consoleReader.readLine();
			if(command.charAt(0) == 'f' || command.charAt(0) == 'i') flag = Integer.parseInt(command.replaceAll("\\D+",""));
			switch(command.charAt(0)) {
				case 'f':
					//TODO -- also print next/prev/col here
					node findNode = L.find(flag);
					if(findNode == null) System.out.println("[Value not found]");
					else {
						System.out.print("[Found " + findNode.get_value());
						if(findNode.get_prev() != null) System.out.print(" / Prev " + findNode.get_prev().get_value());
						if(findNode.get_next() != null) System.out.print(" / Next " + findNode.get_next().get_value());
						if(findNode.get_prevRow() != null) System.out.print(" / PrevCol " + findNode.get_prevRow().get_value());
						if(findNode.get_nextRow() != null) System.out.print(" / NextCol " + findNode.get_nextRow().get_value());
						System.out.println("]");
					}
					break;
				case 'i':
					L.insert(flag);
					break;
				case 'd':
					System.out.println(L.display());
					break;
				case 's':
					L.stats();
					break;
				case 'v':
					//G.genList(L);
					G.makeDot(L);
					if (System.getProperty("os.name").startsWith("Linux")){
						Runtime.getRuntime().exec("./qgraph-nix.sh sqrt");
					} else if (System.getProperty("os.name").startsWith("Mac")){
						Runtime.getRuntime().exec("./qgraph-mac.sh sqrt");
					} else {
						System.out.println("OS not supported for automatic visualization");
					}
					break;
				case 'q':
					System.exit(0);
					break;
			}
		}
		
	}

	public static void menu() {
		//TODO -- Put clear screen here!
	    System.out.print("Available Commands:\n");
	    System.out.print("    i XXX - Insert value XXX into the data structure\n");
	    System.out.print("    s     - Print statistics of data structure\n");
	    //System.out.print("    d XXX - Delete value XXX from the data structure\n");
	    System.out.print("    f XXX - Find value XXX in the data structure\n");
	    System.out.print("    d     - Display the data structure\n");
	    System.out.print("    v     - Visualize the data structure with graphviz\n");
	    System.out.print("    q     - Quit Problem 5\n");
	}
}
