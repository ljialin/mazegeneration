Size with boundary: 28x36
Size without boundary: 26x34
Home size: 7x5
Ignore tunnel

Step 1: generate the distribution of the distance (shortest path) between any two accessible locations in the maze.
- represent the human designed mazes by 0 and 1 in txt files
- code a function to find and calculate the shortest path
- go through all the accessible locations and calculate the distances
- plot the density
Step 2: generate new maze using EAs.
- use the distance distribution calculated in Step 1 as optimum
- fitness=square error between the generated one and the optimum
- using EAs to minimise the fitness
Step 3: evaluate the generated maze
- plot the generated maze
- need human tuning or not ?
- human playing experience with the new maze

Additional one: generate mazes without using human designed mazes.
- human design a score function to evaluate the maze and using EAs to generate mazes.
