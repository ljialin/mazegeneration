import java.io.*;
import java.util.ArrayList;

import javax.swing.JFrame;

import maze.PacmanMaze;
import paint.PacManMazeDrawer;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String filename = "./mazes/pacman/level3.txt";
		int[][] maze = new int[29][26];

		// Reading

		FileReader fr;
		try {
			fr = new FileReader(filename);

			BufferedReader br = new BufferedReader(fr);
			String line;
			int i = 0;
			//		      System.out.println(filename);
			while ((line = br.readLine()) != null){
				System.out.println(line);
				int j = 0;
				for (char ch: line.toCharArray()) {
					maze[i][j] = (int) (ch - '0');
					j++;
				}
				i++;
			}
			br.close();

			PacmanMaze pacmanMaze = new PacmanMaze(maze);
			System.out.println(pacmanMaze);

//			int[][] shortestPath = pacmanMaze.calShortestPath(7,0);
//
//			for(int x=0;x<shortestPath.length;x++)
//			{ for(int y=0;y<shortestPath[0].length;y++)
//				{
//					System.out.print(shortestPath[x][y]+"\t");
//
//				}
//			System.out.println();
//			}

			pacmanMaze.fillIdxCellHashMap();
			pacmanMaze.updateNbAccessibleCell();

      ArrayList<Integer> distDistribution = pacmanMaze.distDistribution();
      try{
        PrintWriter writer = new PrintWriter(filename+".dat", "UTF-8");
        for (i=0; i<distDistribution.size(); i++) {
          writer.println(distDistribution.get(i));
					System.out.println(distDistribution.get(i));
				}
        writer.close();
      } catch (IOException e) {
        // do something
      }


    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
	}

}
