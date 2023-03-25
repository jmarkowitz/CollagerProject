package gui;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import model.Pixel;
import model.PixelInterface;

public class Panel extends JFrame {

  private BufferedImage image;
  private JPanel workspacePanel;

  // constructor
  public Panel() throws IOException {
    // set window title
    super("GUI");
    image = ImageIO.read(new File("/Users/jonathanmarkowitz/Downloads/swingdemo/Jellyfish.jpg"));

    // set window size
    setSize(800, 600);

    // set layout
    setLayout(new BorderLayout());

    // create menu bar
    JMenuBar menuBar = new JMenuBar();

    // create file menu
    JMenu fileMenu = new JMenu("File");
    JMenuItem newMenuItem = new JMenuItem("New Project");
    JMenuItem openMenuItem = new JMenuItem("Open");
    JMenuItem saveMenuItem = new JMenuItem("Save Project");
    JMenuItem exportMenuItem = new JMenuItem("Export As...");

    // add menu items to file menu
    fileMenu.add(newMenuItem);
    fileMenu.add(openMenuItem);
    fileMenu.add(saveMenuItem);
    fileMenu.add(exportMenuItem);

    // add file menu to menu bar
    menuBar.add(fileMenu);

    // set menu bar
    setJMenuBar(menuBar);

    // create panel for layer list
    JPanel layerPanel = new JPanel();
    layerPanel.setLayout(new BoxLayout(layerPanel, BoxLayout.Y_AXIS));

    // create label for layer list
    JLabel layerLabel = new JLabel("Layers");
    layerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    // add layer label to layer panel
    layerPanel.add(layerLabel);

    // create scroll pane for layer list
    JScrollPane layerScrollPane = new JScrollPane(layerPanel);

    // create panel for workspace
    JPanel workspacePanel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
          g.drawImage(image, 0, 0, null);
        }
      }
    };
    workspacePanel.setPreferredSize(new Dimension(800, 600));


    // create scroll pane for workspace
    JScrollPane workspaceScrollPane = new JScrollPane(workspacePanel);
    workspaceScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    workspaceScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    // add workspace scroll pane to main panel
    add(workspaceScrollPane, BorderLayout.CENTER);

    // create panel for filter options
    JPanel filterPanel = new JPanel();
    filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));

    // create label for filter options
    JLabel filterLabel = new JLabel("Filter Options");
    filterLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    // add filter label to filter panel
    filterPanel.add(filterLabel);

    // create new layer button
    JButton newLayerButton = new JButton("New Layer");
    newLayerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    // add new layer button to layer panel
    layerPanel.add(newLayerButton);

    // add layer scroll pane and filter panel to main panel
    add(layerScrollPane, BorderLayout.WEST);
    add(filterPanel, BorderLayout.EAST);

    // set up new layer button action
    newLayerButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String layerName = JOptionPane.showInputDialog("Enter layer name:");
        if (layerName != null && !layerName.equals("")) {
          JLabel layer = new JLabel(layerName);
          layer.setAlignmentX(Component.CENTER_ALIGNMENT);
          layerPanel.add(layer);
          layerPanel.revalidate();
        }
      }
    });

    // set window to visible
    setVisible(true);
  }

  public PixelInterface[][] genImage() {
    Random randomNum = new Random();
    PixelInterface[][] image = new PixelInterface[800][600];
    for (int row = 0; row < 800; row++) {
      for (int col = 0; col < 600; col++) {
        int red = randomNum.nextInt(256);
        int green = randomNum.nextInt(256);
        int blue = randomNum.nextInt(256);
        int alpha = randomNum.nextInt(256);
        image[row][col] = new Pixel(red, green, blue, alpha);
      }
    }
    return image;
  }

  public void setImage(PixelInterface[][] pixels) {
    int width = pixels.length;
    int height = pixels[0].length;

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        PixelInterface pixel = pixels[x][y];
        int alpha = (pixel.getAlpha() >> 24) & 0xff;
        int red = (pixel.getRed() >> 16) & 0xff;
        int green = (pixel.getGreen() >> 8) & 0xff;
        int blue = pixel.getBlue() & 0xff;
        image.setRGB(x, y, (alpha << 24) | (red << 16) | (green << 8) | blue);
      }
    }

    workspacePanel.repaint();
  }

  public static void main(String[] args) throws IOException {
    new Panel();
  }
}

