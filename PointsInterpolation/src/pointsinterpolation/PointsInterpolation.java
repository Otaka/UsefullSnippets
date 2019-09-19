package pointsinterpolation;

import javax.swing.SwingUtilities;

/**
 *
 * @author sad
 */
public class PointsInterpolation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                MainWindow w=new MainWindow();
                w.setVisible(true);
            }
        });
        
    }
}
