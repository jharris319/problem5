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
			if(command.charAt(0) == 'f' || command.charAt(0) == 'i' || command.charAt(0) == 'd' || command.charAt(0) == 'g') flag = Integer.parseInt(command.replaceAll("\\D+",""));
			switch(command.charAt(0)) {

				case 'i':
					L.insert(flag);
					break;
				case 'd':
					break;
				case 'f':
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
				case 'g':
					//TODO -- delete entire data structure before allowing generate
					L = null;
					System.gc();
					L = new list();
					L.genList(flag);
					break;
				case 's':
					L.stats();
					break;
				case 'l':
					System.out.println(L.display());
					break;
				case 'v':
					G.makeDot(L);
					break;
				case 'D':
					L = null;
					System.gc();
					L = new list();
					break;
				case 'q':
					System.exit(0);
					break;
			}
		}
		
	}

	public static void clear(){
	//method 1
		//System.out.print("\f"); might work for Linux
	//method 2
		// try {
		// Runtime.getRuntime().exec("clear");
		// } catch (IOException e) {
		// 	e.printStackTrace();
		// }
	//method 3
		// try {
		// Runtime.getRuntime().exec("./clearScreen.sh");
		// } catch (IOException e) {
		// 	e.printStackTrace();
		// }
	//method 4 working
		// final String ANSI_CLS = "\u001b[2J";
		// final String ANSI_HOME = "\u001b[H";
		// System.out.print(ANSI_CLS + ANSI_HOME);
		// System.out.flush();
	//method 5 working
		System.out.print("\u001b[2J" + "\u001b[H");
	}

	public static void menu() {
		//TODO -- Put clear screen here!
		clear();
	    System.out.print("Available Commands:\n");
	    System.out.print("    i XXX - Insert value XXX into the data structure\n");
	    System.out.print("    d XXX - Delete value XXX from the data structure\n");
	    System.out.print("    f XXX - Find value XXX in the data structure\n");
	    System.out.print("    g XXX - Generate XXX integers in the data structure\n");
	    System.out.print("    s     - Print statistics of data structure\n");
	    System.out.print("    l     - Display the data structure as a list\n");
	    System.out.print("    v     - Visualize the data structure with graphviz\n");
	    //System.out.print("    D     - Delete the entire data structure\n");
	    System.out.print("    q     - Quit Problem 5\n");
	}
}
