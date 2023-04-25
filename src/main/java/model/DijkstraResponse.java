package model;

import constant.WeightConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DijkstraResponse {
    Double length;
    List<List<Edge>> edgePaths;
    List<List<Integer>> nodePaths;


    public void showAllPaths() {
        for (List<Edge> path : edgePaths) {
            path.forEach(e -> System.out.println("\t" + e.getSource().getId() + "_" + e.getDestination().getId() + ": " + e.getWeight(WeightConstant.LENGTH)));
        }
    }
}
