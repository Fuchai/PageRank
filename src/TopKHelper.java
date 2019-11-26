import java.util.Arrays;

public class TopKHelper {
    public double[] value;
    public int[] indices;

    public void topK(double[] array, int k){
        int[] indices= new int[k];
        double[] values = new double[k];
        Arrays.fill(values, Double.MIN_VALUE);
        double min = Double.MIN_VALUE;
        for (int i = 0; i < array.length; i++) {
            double value;
            value=array[i];
            // if value is in top k so far
            if (value>min){
                for (int j = 0; j < k; j++) {
                    if (values[j]<value){
                        values[j]=value;
                        indices[j]=i;
                        break;
                    }
                }
                // update the min
                min = Double.MAX_VALUE;
                for (int j = 0; j < k; j++) {
                    if (values[j]<min){
                        min=values[j];
                    }
                }
            }
        }
        this.value=values;
        this.indices=indices;
    }
}