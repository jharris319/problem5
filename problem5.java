import java.io.*;

public class problem5 {
	public static void main (String args[ ]) throws IOException, InterruptedException {
		BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br;
		String command,line;
		int flag = 0;
		list L = new list();
		graphGen G = new graphGen();

		menu();
		while(true) {
			System.out.print("\nAt your command: ");
			command = consoleReader.readLine();
			if (command.equals("")) command = "junk";
			if (command.charAt(0) == 'f' || command.charAt(0) == 'i' || command.charAt(0) == 'd' || command.charAt(0) == 'o' || command.charAt(0) == 'g' || command.charAt(0) == 'r') {
				String strip = command.replaceAll("\\D+","");
				if (strip.equals("")) command = "junk";
				else flag = Integer.parseInt(strip);
			}
			switch(command.charAt(0)) {

				case 'i':
					menu();
					if (L.insert(flag) != -1) System.out.println("\n[Inserted: " + flag + "]");
					break;
				case 'd':
					menu();
					if (L.delete(flag) != -1) System.out.println("\n[Deleted: " + flag + "]");
					break;
				case 'o':
					br = new BufferedReader(new FileReader("randInts.txt"));
					line = br.readLine();
					for (int i = 0; i < flag; i++) {
						L.delete(Integer.parseInt(line));
						if (flag <= 50) {
							menu();
							System.out.println(L.display());
							Thread.sleep(120);
						}
						line = br.readLine();
					}
					System.out.println("\n[Obliterated " + flag + " items]");
					break;
				case 'f':
					menu();
					node findNode = L.find(flag);
					if(findNode == null) System.out.println("[Value not found]");
					else {
						System.out.print("[Found " + findNode.get_value());
						if(findNode.get_prev() != null) System.out.print(" / Prev " + findNode.get_prev().get_value());
						if(findNode.get_next() != null) System.out.print(" / Next " + findNode.get_next().get_value());
						if(findNode.get_prevRow() != null) System.out.print(" / PrevRow " + findNode.get_prevRow().get_value());
						if(findNode.get_nextRow() != null) System.out.print(" / NextRow " + findNode.get_nextRow().get_value());
						System.out.println("]");
					}
					break;
				case 'g':
					menu();
					L = null;
					System.gc();
					L = new list();
					L.genList(flag);
					break;
				case 'r':
					menu();
					L = null;
					System.gc();
					L = new list();
					randGen.main(flag,0,10,flag/2);
					br = new BufferedReader(new FileReader("randInts.txt"));
					while ((line = br.readLine()) != null) {
						L.insert(Integer.parseInt(line));
						if (flag <= 50) {
							menu();
							System.out.println(L.display());
							Thread.sleep(120);
						}
					}
					break;
				case 's':
					menu();
					L.stats();
					break;
				case '?':
					menu();
					L.guide();
					break;
				case 'l':
					menu();
					System.out.println(L.display());
					break;
				case 'v':
					menu();
					G.makeDot(L);
					break;
				case 'V':
					menu();
					L.verify();
					break;
				case 'D':
					menu();
					L = null;
					System.gc();
					L = new list();
					break;
				case 'a':
					advancedMenu();
					break;
				case 'q':
					System.out.print("\u001b[2J" + "\u001b[H");
					System.out.println("Thanks for flying 100P!");
					System.exit(0);
					break;
                case 'z':
                    System.out.println("\n[Debugging]");
                    break;
				default:
					menu();
					System.out.println("\n[Invalid Input!]");
					break;
			}
		}
		
	}

	public static void menu() {
		System.out.print("\u001b[2J" + "\u001b[H");
		System.out.print("Problem 5: Square Root Data Structure\n\n");
	    System.out.print("Available Commands:\n");
	    System.out.print("    i XXX - Insert value XXX into the data structure\n");
	    System.out.print("    d XXX - Delete value XXX from the data structure\n");
	    System.out.print("    f XXX - Find value XXX in the data structure\n");
	    System.out.print("    s     - Print statistics of data structure\n");
		System.out.print("    ?     - Print restructure hints\n");
	    System.out.print("    l     - Display the data structure as a list\n");
	    System.out.print("    v     - Visualize the data structure with graphviz\n");
	    System.out.print("    D     - Delete the entire data structure\n");
		System.out.print("    a     - Show advanced menu\n");
	    System.out.print("    q     - Quit Problem 5\n");
	}

	public static void advancedMenu() {
		menu();
		System.out.print("\nAdvanced Commands:\n");
		System.out.print("    o XXX - Obliterate XXX random values from the data structure\n");
		System.out.print("    g XXX - Generate XXX integers in the data structure in order\n");
		System.out.print("    r XXX - Generate XXX random integers in the data structure\n");
		System.out.print("    V     - Verify the data structure\n");
	}
}
