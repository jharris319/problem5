import java.io.*;

public class problem5 {
	public static void main (String args[ ]) throws IOException {
		BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
		String command;
		int flag = 0;
		list L = new list();

		menu();
		while(true) {
			System.out.print("At your command:");
			command = consoleReader.readLine();
			if(command.charAt(0) == 'i') flag = Integer.parseInt(command.replaceAll("\\D+",""));
			switch(command.charAt(0)) {
				case 'i':
					L.insert(flag);
					break;
				case 'd':
					System.out.println(L.display());
					break;
				case 's':
					System.out.println(L.size());
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
	    System.out.print("    s     - Print size of data structure\n");
	    //System.out.print("    d XXX - Delete value XXX from the data structure\n");
	    System.out.print("    f XXX - Find value XXX in the data structure\n");
	    System.out.print("    d     - Display the data structure\n");
	    System.out.print("    q     - Quit Problem2\n");
	}
}
