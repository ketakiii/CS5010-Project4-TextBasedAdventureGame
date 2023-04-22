package model;

import java.util.ArrayList;
import java.util.List;

class Mst {

  private final int vertex;

  public Mst(int numVertices) {
    this.vertex = numVertices;
  }

  public int minKey(int[] key, Boolean[] mstSet) {
    int min = Integer.MAX_VALUE;
    int minIndex = -1;
    for (int v = 0; v < this.vertex; v++) {
      if (!mstSet[v] && key[v] < min) {
        min = key[v];
        minIndex = v;
      }
    }
    return minIndex;
  }

  public List<int[]> getMst(int[] parent, int[][] graph) {
    List<int[]> selectedEdges = new ArrayList<>();
    for (int i = 1; i < vertex; i++) {
      selectedEdges.add(new int[]{parent[i], i});
    }
    return selectedEdges;
  }

  //  public void printMST(int[] parent, int[][] graph) {
  //    System.out.println("Edge \tWeight");
  //    for (int i = 1; i < V; i++)
  //      System.out.println(parent[i] + " - " + i + "\t"
  //          + graph[i][parent[i]]);
  //  }

  public List<int[]> primMst(int[][] graph) {
    int[] parent = new int[vertex];
    int[] key = new int[vertex];
    Boolean[] mstSet = new Boolean[vertex];
    for (int i = 0; i < vertex; i++) {
      key[i] = Integer.MAX_VALUE;
      mstSet[i] = false;
    }
    key[0] = 0;
    parent[0] = -1;
    for (int count = 0; count < vertex - 1; count++) {
      int u = minKey(key, mstSet);
      mstSet[u] = true;
      for (int v = 0; v < this.vertex; v++) {
        if (graph[u][v] != 0 && !mstSet[v] && graph[u][v] < key[v]) {
          parent[v] = u;
          key[v] = graph[u][v];
        }
      }
    }
    return getMst(parent, graph);
  }
}

// This code is contributed by Aakash Hasija
