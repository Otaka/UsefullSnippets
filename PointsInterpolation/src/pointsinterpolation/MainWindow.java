package pointsinterpolation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author sad
 */
public class MainWindow extends JFrame {

    private List<FPoint> pointsToDraw = new ArrayList<FPoint>(1000);

    private List<FPoint> points = new ArrayList<FPoint>();

    public MainWindow() throws HeadlessException {
        points.add(new FPoint(0,200));
        points.add(new FPoint(800,150));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Points interpolation");
        DrawPanel panel = new DrawPanel();
        panel.setPreferredSize(new Dimension(500, 500));
        add(panel);
        pack();
        setLocationRelativeTo(null);
        pathSteps();
        panel.addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                points.add(new FPoint(e.getPoint()));
                pathSteps();
                repaint();
            }

        });
        panel.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                points.clear();
                pointsToDraw.clear();
                repaint();
            }
        });
    }

    private void pathSteps() {
        PathFollower follower = new PathFollower(points.toArray(new FPoint[points.size()]));
        while (follower.step(1)) {
            pointsToDraw.add(new FPoint(follower.getCurrentPoint()));
        }
    }

    private class DrawPanel extends JPanel {

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if (!points.isEmpty()) {
                FPoint lastPoint = points.get(0);
                g.setColor(Color.BLACK);
                for (int i = 1; i < points.size(); i++) {
                    FPoint p = points.get(i);
                    g.drawLine((int)lastPoint.getX(), (int)lastPoint.getY(), (int)p.getX(), (int)p.getY());
                    lastPoint = p;
                }

                g.setColor(Color.red);
                for (FPoint p : pointsToDraw) {
                 //   g.fillOval((int) p.getX() - 1, (int) p.getY() - 1, 2, 2);
                }
            }
        }
    }
}
