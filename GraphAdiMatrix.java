
import java.util.Stack;

public class GraphAdiMatrix implements Graph {
	private int[][] edgeMatrix;
	private int size;

	public GraphAdiMatrix() {
		edgeMatrix = new int[0][0];
		size = 0;
	}

	public GraphAdiMatrix(int vertices) {
		size = vertices;
		edgeMatrix = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				edgeMatrix[i][j] = -1;
			}
		}
	}

	@Override
	public void addEdge(int v1, int v2) {
		// if v1 or v2 is valid number
		if (v1 < 0 || v2 < 0 || v1 > edgeMatrix.length || v2 > edgeMatrix.length) {
			System.out.print("Input vertex not exist");
		}
		edgeMatrix[v1][v2] = 1;
	}

	/**
	 * 
	 * @param vertex
	 *            calculate how many edge from other vertices to given vertex
	 * @return inDegree
	 */
	public int inDegree(int vertex) {
		int degree = 0;
		for (int i = 0; i < edgeMatrix.length; i++) {
			if (edgeMatrix[i][vertex] != 0) {
				degree++;
			}
		}
		return degree;
	}

	/**
	 * 
	 * @param incident
	 *            find inDegree of vertex is 0
	 * @return vertex
	 */
	public int findZero(int[] incident) {
		for (int i = 0; i < incident.length; i++) {
			if (incident[i] == 0) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void topologicalSort() {
		int[] incident = new int[edgeMatrix.length];
		int count = 0;
		for (int i = 0; i < edgeMatrix.length; i++) {
			incident[i] = inDegree(i);
		}
		Stack<Integer> stack = new Stack<Integer>();

		int position = findZero(incident);
		if (position == 0) {
			stack.push(position);
		}

		while (!stack.isEmpty()) {
			int vertex = stack.pop();
			System.out.print(vertex + " ");
			count = count + 1;
			for (int i = 0; i < edgeMatrix.length; i++) {
				incident[i]--;
				if (incident[i] == 0) {
					stack.push(i);
				}
			}
		}
		if (count != size) {
			System.out.println("Graph has a circle");
		}
		System.out.println("");
	}

	@Override
	public void addEdge(int v1, int v2, int weight) {
		edgeMatrix[v1][v2] = weight;
		edgeMatrix[v2][v1] = weight;
	}

	@Override
	public int getEdge(int v1, int v2) {
		return edgeMatrix[v1][v2];
	}

	@Override
	public int createSpanningTree() {

		int next = 0;
		int total = size;
		int minCost = Integer.MAX_VALUE;
		boolean[] known = new boolean[size];
		int[] cost = new int[size];
		int[] path = new int[size];

		// initial
		for (int i = 0; i < size; i++) {
			known[i] = false;
			cost[i] = Integer.MAX_VALUE;
			path[i] = -1;
		}

		// start from beginning

		// known[0] = true;
		cost[0] = 0;
		path[0] = 0;

		while (total > 0) {
			minCost = Integer.MAX_VALUE;
			known[next] = true;
			total--;

			for (int i = 0; i < size; i++) {
				// if vertex is not known
				if (!known[i] && edgeMatrix[next][i] > -1 && edgeMatrix[next][i] < cost[i]) {
					cost[i] = edgeMatrix[next][i];
					path[i] = next;
				}

			}

			for (int i = 0; i < size; i++) {
				if (!known[i] && cost[i] < minCost && cost[i] != -1) {
					minCost = cost[i];
					next = i;
				}
			}
		}

		int sum = 0;
		// clear all the edges

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				edgeMatrix[i][j] = -1;
			}
		}

		for (int i = 0; i < size; i++) {
			if (known[i]) {
				addEdge(path[i], i, cost[i]);
				sum = sum + cost[i];
			}

		}

		return sum;
	}

}
