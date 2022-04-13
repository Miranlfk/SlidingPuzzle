public class Vertices {
    private int labels;

    /**
     * Parametrized Cosntructor of Vertex
     * @param labels
     */
    public Vertices(int labels) {
        this.labels = labels;
    }

    /**
     * getter method to return the labels
     * @return
     */
    public int getLabels() {
        return labels;
    }

    /**
     * setter method to set the labels
     * @param labels
     */
    public void setLabels(int labels) {
        this.labels = labels;
    }

    @Override
    public boolean equals(Object newObject) {
        if(this == newObject)
            return true;
        if(newObject == null || newObject.getClass()!= this.getClass())
            return false;
        Vertices newVertex = (Vertices) newObject;
        return (newVertex.labels ==(this.labels));
    }

    @Override
    public int hashCode() {
        return this.labels;
    }

    @Override
    public String toString() {
        return "Vertex [label=" + labels + "]";
    }

}
