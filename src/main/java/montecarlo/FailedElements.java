package montecarlo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.Edge;
import model.Vertex;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class FailedElements {
    private List<Vertex> sourcesWithFailures;
    private List<Edge> failedEdges;
    private List<Edge> arcsWithReducedCapacity;

    public String toLine(){
        String response = "";
        response+="\n------ SOURCES WITH FAILURES";
        for(Vertex vertex: sourcesWithFailures){
            response+="\n SOURCE: "+vertex.getName()+", TYPE:  "+vertex.getType()+" FAILED";
        }
        response+="\n------- ARCS WITH FAILURES";
        for(Edge edge: failedEdges){
            response+="\n EDGE: "+edge.getName()+" FAILED";
        }
        for(Edge edge: arcsWithReducedCapacity){
            response+="\n EDGE: "+edge.getName()+" HAS REDUCED CAPACITY DUE TO COMPRESSION STATION FAILURE";
        }

        return response+"\n";
    }
}
