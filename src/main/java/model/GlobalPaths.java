package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GlobalPaths {
    private Map<String, List<List<Integer>>> nodePaths;
    private Map<String, List<List<Edge>>> edgePaths;
    private Map<String, List<List<Vertex>>> vertexPaths;
}
