package pack;

/**
 * Created by Vinicius on 4/6/16.
 */
public class PSO {

    public static final int GLOBAL = 0x00;
    public static final int LOCAL = 0x01;
    public static final int FOCAL = 0x02;

    public PSO() {

    }

    private void globalTopology() {

    }

    private void localTopology() {

    }

    private  void focalTopology() {

    }

    public void updateGlobalPosition(int topology) {
        switch (topology) {
            case LOCAL:
                localTopology();
                break;
            case FOCAL:
                focalTopology();
                break;
            case GLOBAL:
            default:
                globalTopology();
                break;
        }
    }

}
