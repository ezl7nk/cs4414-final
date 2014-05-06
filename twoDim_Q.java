

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class twoDim_Q {
	static boolean contains;
	static int n, m;
	static String p;
	static int solutionCount=0;
	//static LinkedList<int[]> solutionList = new LinkedList<int[]>();;
	public static void main(String[] args){
		long startTime= System.nanoTime();


		/*Scanner input = new Scanner(System.in);
		System.out.print("Number of rows ");
		n= Integer.parseInt(input.next());
		input = new Scanner(System.in);
		System.out.println("Number of columns: ");
		m=Integer.parseInt(input.next());
		input = new Scanner(System.in);
		System.out.println("Type of piece: ");
		p=input.next(); 
		/*n = 6; 
		m= 4;
		p= "Q";*/
		JFrame gui = new JFrame("GUI");
		n = Integer.parseInt(JOptionPane.showInputDialog(gui,"# of rows: "));
		m = Integer.parseInt(JOptionPane.showInputDialog(gui,"# of columns: "));
		p = (String) JOptionPane.showInputDialog(gui,"Piece: [Q/R/B/N] ");
				
		LinkedList<int[]> solutionList_i = new LinkedList<int[]>();
		LinkedList<String[]> solutionList_s = new LinkedList<String[]>();
		int diag1[];
		int diag2[];
		int finalSolution[];

		if(n>m){
			int temp=n;
			n=m;
			m=temp;
			System.out.println("Swapped n and m");
		}


		if(p.equals("Q")){
			diag1 = new int[n];
			diag2= new int[n];
			finalSolution= new int[n]; 
			for(int i=0; i<n; i++)
			{
				diag1[i] = -1;
				diag2[i] = -1;
				finalSolution[i] = -1;
			}

			solutionList_i=twoDim_Q_recurse(0, finalSolution, solutionList_i, diag1, diag2);
		}
		else if(p.equals("R")){

			finalSolution= new int[n]; 

			for(int i=0; i<n; i++)
			{
				finalSolution[i] = -1;
			}
			solutionList_i = twoDim_R_recurse(0,finalSolution, solutionList_i);
		}
		else if(p.equals("B")){


			diag1 = new int[n+m-1];
			String finalSolution2[];
			if(  (m-  ((m-(n-1))*2)  )   <0){
				finalSolution2= new String[m]; // accomdate for x and y coordinates 
			}
			else{
				finalSolution2= new String[m+(m-((m-(n-1))*2))];
			}
			for(int i=0; i<diag1.length; i++)
			{
				diag1[i] = -1;
			}

			for(int i=0; i<finalSolution2.length; i++)
			{	    
				finalSolution2[i] = ("" + -1);
			}
			solutionList_s = twoDim_B_recurse(0, 0, solutionList_s, finalSolution2, diag1);
		}

		else if(p.equals("N")){
			boolean[][] spaceAttacked = new boolean[n][m];
			solutionList_s = twoDim_N_recurse(0, 0, spaceAttacked, solutionList_s);
		}

		//System.out.println("There are " + solutionCount + " unique solutions. They are: ");
	
		
		/*
	    for(int[] z: solutionList_i)
		{
	    	for(int y=0; y<z.length; y++)
	    		System.out.print(z[y]);

			//printNiceBoard_i(z);
	    	System.out.println();
		}
	    
	    for(String[] z: solutionList_s)
		{
	    	for(int y=0; y<z.length; y++)
	    		System.out.println(z[y]);

			printNiceBoard_s(z);

		}   */

		//JFrame gui = new JFrame("GUI");
		String print;
		while(true) {
			int inp = Integer.parseInt(JOptionPane.showInputDialog(gui,
					"There are " + solutionCount + " unique solutions for " + p + ", " + n + "x" + 
							m + ".\n Please enter a number between 1 and " + solutionCount + " to view a solution.",JOptionPane.PLAIN_MESSAGE));
			if(inp == -1)
				break;
			
			//String s="";
			if(p.equals("Q") || p.equals("R")){
				int t[] = solutionList_i.get(inp-1);
				print = printNiceBoard_i(t,p);
			}
			else{
				String t[] = solutionList_s.get(inp-1);
				print = printNiceBoard_s(t,p);
				

			}

			JOptionPane.showMessageDialog(gui,print);
		}
		JOptionPane.showMessageDialog(gui,"This computation took: " +(System.nanoTime()- startTime) + " nanoseconds");
		System.exit(0);
	}


	// QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQqqq



	private static LinkedList<int[]> twoDim_Q_recurse(int c, int[] solution, LinkedList<int[]> A, int[] diag1, int[] diag2){
		contains = false;
		
		if (c == n)
			return A;
		//System.out.println(n);
		for(int r=0; r<m; r++){ //go by row
			
			//System.out.println("c:" + c + " r: " + r);
			for(int k = 0; k < c; k++){ // go through the arrays to check if the row is valid
				if(solution[k] == r || diag1[k] == r-c+(n-1) || diag2[k] == r+c){
					contains= true;
					break;
				}		
			}
			if(contains==false)
			{ //if all ways are clear, add
				//System.out.print(solution[0]+"" + solution[1]+""+solution[2]+""+solution[3]);
				//System.out.println("IN c:" + c + " r: " + r);
				solution[c]= r;
				diag1[c] = r-c+(n-1);
				diag2[c] = r+c;
				A = twoDim_Q_recurse(c+1, solution, A, diag1, diag2);
			}
			contains=false;
		}
		int ret[] = new int[solution.length];

		//we've exhausted the rows we can put queens for the specified column
		if(solution[n-1] != -1){
		//	System.out.println("KDFHDKLHF");
			String temp = "";
			for(int i =0; i< solution.length; i++) {
				temp=temp.concat("" + solution[i]);
			//	System.out.print(solution[i]);
			}
			//System.out.println();

			for(int i = 0; i < solution.length; i++)
				ret[i] = solution[i];
			A.add(ret);

			solutionCount++;
		}
		//System.out.println();

		solution[c] =-1;
		//diag1[c-1]=-1;
		//diag2[c-1]=-1;
		return A;
	}





	/// RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR




	private static LinkedList<int[]> twoDim_R_recurse(int c, int[] solution, LinkedList<int[]> A){
		contains = false;
		if (c == n)
			return A;
		for(int r=0; r<n; r++){ //go by row
			//System.out.println("c:" + c + " r: " + r);
			for(int k = 0; k < c; k++){ // go through the arrays to check if the row is valid
				if(solution[k] == r){
					contains= true;
					break;
				}		
			}
			if(contains==false)
			{ //if all ways are clear, add
				//System.out.print(solution[0]+"" + solution[1]+""+solution[2]+""+solution[3]);
				solution[c]= r;
				A = twoDim_R_recurse(c+1, solution, A);
			}
			contains=false;
		}  
		//we've exhausted the rows we can put queens for the specified column
		int ret[] = new int[solution.length];
		//we've exhausted the rows we can put rook for the specified column
		if(solution[n-1] != -1){
			//String temp = "";
			/*for(int i =0; i< solution.length; i++)
				//temp=temp.concat("" + solution[i]);
				System.out.print(solution[i]);
			System.out.println();*/

			for(int i = 0; i < solution.length; i++)
				ret[i] = solution[i];
			A.add(ret);

			solutionCount++;
		}
		//System.out.println();

		solution[c] =-1;
		//diag1[c-1]=-1;
		//diag2[c-1]=-1;
		return A;
	}

	//BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB


	private static LinkedList<String[]> twoDim_B_recurse(int c, int r, LinkedList<String[]> A, String[] solution, int[] otherDiag){
		contains = false;
		//System.out.println("First C: " + c + " R: " + r);
		if (solution[solution.length-1] != ("" +(-1)) ) //n+m-1 is the number of diagonals
			return A; 

		while(c>(-1) && r<m){ //  go through each square in the diagonal
			//System.out.println("C: " + c + " R: " + r);
			for(int k = 0; k < solution.length; k++){ // go through the arrays to check if the row is valid
				if(otherDiag[k] == (r-c+(n-1))){
					contains= true;
					break;
				}		
			}
			if(contains==false){ // clear
				//System.out.println("Inside");			
				solution[r+c]= (c + "" + r);
				otherDiag[r+c] = (r-c+(n-1));
				//System.out.println("Stuff: "+ (r-c+(n-1)));
				//System.out.println(solution);

				// Proceed to next diagonal. 
				if( (c+r+1) < n){ // If still on the top, go to the next column
					//System.out.println("First if");
					A = twoDim_B_recurse(c+r+1, 0, A, solution, otherDiag);
				}
				else if( (c+r+1) >=n){ // If you've reached the side, go down the column
					//System.out.println("n-1"+(n-1) + "");   
					//System.out.println("Second if " + (n-1));
					A = twoDim_B_recurse(n-1, ( (c+r+1)-(n-1) ), A, solution, otherDiag);
					//System.out.println("Second: C: " + c + " R: " + r);
				}
			}
			contains=false;
			c--; // iterate to next space in diagonal by subtracting 1 from column
			r++; // iterate to next space in diagonal by adding 1 to the row

		}


		String ret[] = new String[solution.length];

		if(!solution[solution.length-1].equals("" + (-1 ))){  //good solution
			//System.out.println("Solution:" +solution);
			/*
			String temp = "";
			for(int i =0; i< solution.length; i++)
				temp=temp.concat("" + solution[i]);*/
			//solutionList.add(temp);

			for(int i = 0; i < solution.length; i++)
				ret[i] = (solution[i]);
			A.add(ret);

			solutionCount++;
			//solutionList.add(solution);
		}

		//System.out.println("Passed bottom" + solution);
		solution[r+c] = ("" +(-1));
		otherDiag[r+c] = (-1);
		return A;

	}

	// KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK
	private static LinkedList<String[]> twoDim_N_recurse(int c, int r, boolean spaceAttacked[][], LinkedList<String[]> A){
		ArrayList<String> solution = new ArrayList<String>();
		//String solution="";
		for(c=0; c<n; c++)
		{
			for(r=0; r<m; r++)
			{
				//System.out.println("C:"+ c + "R:" + r);
				if((c+r)%2 == 0){

					solution.add(c + "" + r);

					if(c+2 >= 0 && c+2 < n && r+1 >= 0 && r+1 < m) //c+2 r+1
						spaceAttacked[c+2][r+1]= true;
					if(c+1 >= 0 && c+1 < n && r+2 >= 0 && r+2 < m) //c+1 r+2
						spaceAttacked[c+1][r+2]= true;
					if(c+2 >= 0 && c+2 < n && r-1 >= 0 && r-1 < m) //c+2 r-1
						spaceAttacked[c+2][r-1]= true;					
					if(c+1 >= 0 && c+1 < n && r-2 >= 0 && r-2 < m) //c+1 r-2
						spaceAttacked[c+1][r-2]= true;					
					if(c-2 >= 0 && c-2 < n && r+1 >= 0 && r+1 < m) //c-2 r+1
						spaceAttacked[c-2][r+1]= true;						
					if(c-1 >= 0 && c-1 < n && r+2 >= 0 && r+2 < m) //c-1 r+2
						spaceAttacked[c-1][r+2]= true;						 
					if(c-2 >= 0 && c-2 < n && r-1 >= 0 && r-1 < m) //c-2 r-1
						spaceAttacked[c-2][r-1]= true;						
					if(c-1 >= 0 && c-1 < n && r-2 >= 0 && r-2 < m) //c-1 r-2
						spaceAttacked[c-1][r-2]= true;					

				}					
			}
		}

		for(c=0; c<n; c++)
		{
			for(r=0; r<m; r++)
			{
				if((c+r)%2 ==1){
					if(spaceAttacked[c][r]==false){
						solution.add(c + "" + r);

						if(c+2 > 0 && c+2 < n && r+1 > 0 && r+1 < m) //c+2 r+1
							spaceAttacked[c+2][r+1]= true;
						if(c+1 > 0 && c+1 < n && r+2 > 0 && r+2 < m) //c+1 r+2
							spaceAttacked[c+1][r+2]= true;
						if(c+2 > 0 && c+2 < n && r-1 > 0 && r-1 < m) //c+2 r-1
							spaceAttacked[c+2][r-1]= true;					
						if(c+1 > 0 && c+1 < n && r-2 > 0 && r-2 < m) //c+1 r-2
							spaceAttacked[c+1][r-2]= true;					
						if(c-2 > 0 && c-2 < n && r+1 > 0 && r+1 < m) //c-2 r+1
							spaceAttacked[c-2][r+1]= true;						
						if(c-1 > 0 && c-1 < n && r+2 > 0 && r+2 < m) //c-1 r+2
							spaceAttacked[c-1][r+2]= true;						 
						if(c-2 > 0 && c-2 < n && r-1 > 0 && r-1 < m) //c-2 r-1
							spaceAttacked[c-2][r-1]= true;						
						if(c-1 > 0 && c-1 < n && r-2 > 0 && r-2 < m) //c-1 r-2
							spaceAttacked[c-1][r-2]= true;		

					}
				}
			}
		}
		
		String[] solutionArray = new String[solution.size()];
		for(int i=0; i<solutionArray.length; i++)
			solutionArray[i]=solution.get(i);
		//fill the solution array
		A.add(solutionArray);
		solutionCount++;
		return A;

	}

	static private void printBoard(int[] board){
		//Iterator<String> itr = solutionList.iterator();
		/*for(String z: solutionList)
		{
			System.out.println(z);
		}*/
		for(int z:board)
			System.out.print(z);//good solution
		System.out.println();	

	}

	static private String printNiceBoard_i(int[] coordinates, String piece){
		String ret2 = "";
		for(int a: coordinates)
			ret2=ret2.concat("" +a);
		ret2=ret2.concat("\n");
		
		// Draw board
		for(int c=0; c<n; c++){
			if(c==0){
				for(int t=0; t<m+3+(m/5); t++)
					ret2 = ret2.concat("=");
				ret2 = ret2.concat("\n");	
			}

			ret2 = ret2.concat("[ ");	
			// ISSUE 1: wtf need gui
			// ISSUE 2: double digits
			int ct = 0;
			for(int d=0; d<m; d++) {
				if (d == coordinates[c]) {
					ret2 = ret2.concat(piece);
					ct++;
				}
				else
					ret2 = ret2.concat("O");
			}
			ct = 0;

			ret2 = ret2.concat(" ]");
			ret2 = ret2.concat("\n");

			if(c==n-1){
				for(int t=0; t<m+3+ (m/5); t++)
					ret2 = ret2.concat("=");
				ret2 = ret2.concat("\n");	
			}

		}
		return ret2;
	}
	static private String printNiceBoard_s(String[] coordinates, String piece){
		String ret2 = "";
		boolean found = false;
		for(String a: coordinates)
			ret2=ret2.concat(a);
		ret2=ret2.concat("\n");
		// Draw board
		
		for(int c=0; c<n; c++){
			if(c==0){
				for(int t=0; t<m+3+(m/5); t++)
					ret2 = ret2.concat("=");
				ret2 = ret2.concat("\n");	
			}

			ret2 = ret2.concat("[ ");	
			// ISSUE 1: wtf need gui
			// ISSUE 2: double digits
			int ct = 0;
			for(int d=0; d<m; d++) {
				for(String s: coordinates){
					if(s.contains(""+c+d) ) {
						//System.out.println("C:" + c + " R: " + d);
						found = true;
						break;
					}
				}
				if(found==true ) {
					ret2 = ret2.concat(piece);
					ct++;
				}
				else
					ret2 = ret2.concat("O");
				found= false;
			}
			ct = 0;

			ret2 = ret2.concat(" ]");
			ret2 = ret2.concat("\n");

			if(c==n-1){
				for(int t=0; t<m+3+(m/5); t++)
					ret2 = ret2.concat("=");
				ret2 = ret2.concat("\n");	
			}

		}
		return ret2;
	}

		// Put piece in


	
}
