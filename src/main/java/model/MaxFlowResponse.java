package model;


import java.util.List;

public class MaxFlowResponse {
    public MaxFlowResponse() {
    }

    private Double maxFlow;
    private List<Edge> edgesMaxFlow;

    public Double getMaxFlow() {
        return maxFlow;
    }

    public void setMaxFlow(Double maxFlow) {
        this.maxFlow = maxFlow;
    }

    public List<Edge> getEdgesMaxFlow() {
        return edgesMaxFlow;
    }

    public void setEdgesMaxFlow(List<Edge> edgesMaxFlow) {
        this.edgesMaxFlow = edgesMaxFlow;
    }
}

