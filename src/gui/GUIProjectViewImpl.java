package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import model.PixelInterface;

public class GUIProjectViewImpl extends JFrame implements GUIProjectView {

  private int programWidth;
  private int programHeight;

  // menu stuff
  private JMenuBar menuBar;
  private JMenu fileMenu;
  private JMenuItem newMenuItem;
  private JMenuItem openProjectMenuItem;
  private JMenuItem saveProjectMenuItem;
  private JMenuItem exportMenuItem;
  private JMenuItem quitMenuItem;
  private JMenu projectMenu;
  private JMenuItem addImageMenuItem;

  private int sideBarWidth;
  private boolean isProjectActive;

  // layer stuff
  private JButton addLayerButton;
  private JPanel layerPanel;
  private JLabel layerLabel;
  private JPanel topLayerPanel;
  private JList<String> layerList;
  private DefaultListModel<String> layerListModel;

  // workspace stuff
  private JPanel workspacePanel, imagePanel;
  private JLabel imageLabel;
  private JScrollPane workspaceScroll;

  // tool stuff
  private JButton applyFilterButton;
  private JPanel toolPanel;
  private JPanel topToolPanel;
  private JLabel toolLabel;
  private JList<String> filterList;
  private DefaultListModel<String> filterListModel;
  private JScrollPane filterListScrollPane;

  public GUIProjectViewImpl(String caption) {
    super(caption);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    programWidth = (int) size.getWidth();
    programHeight = (int) size.getHeight();
    this.setSize(programWidth, programHeight);
    this.setVisible(true);
    this.setLayout(new BorderLayout());

    // create menu bar
    menuBar = new JMenuBar();

    // create file menu
    fileMenu = new JMenu("File");
    newMenuItem = new JMenuItem("New Project");
    openProjectMenuItem = new JMenuItem("Open Project");
    saveProjectMenuItem = new JMenuItem("Save Project");
    exportMenuItem = new JMenuItem("Export As...");
    quitMenuItem = new JMenuItem("Quit");
    this.exportMenuItem.setEnabled(false);
    this.saveProjectMenuItem.setEnabled(false);

    // create edit menu
    projectMenu = new JMenu("Project");
    addImageMenuItem = new JMenuItem("Add Image To Selected Layer");
    this.addImageMenuItem.setEnabled(false);
    projectMenu.add(addImageMenuItem);

    // add menu items to file menu
    fileMenu.add(newMenuItem);
    fileMenu.add(openProjectMenuItem);
    fileMenu.add(saveProjectMenuItem);
    fileMenu.add(exportMenuItem);
    fileMenu.addSeparator();
    fileMenu.add(quitMenuItem);

    // add file menu to menu bar
    menuBar.add(fileMenu);
    menuBar.add(projectMenu);

    // Set up the macOS application menu bar
    if (System.getProperty("os.name").startsWith("Mac")) {
      System.setProperty("apple.laf.useScreenMenuBar", "true");
      System.setProperty("com.apple.mrj.application.apple.menu.about.name", "File");
      Desktop desktop = Desktop.getDesktop();
      desktop.setDefaultMenuBar(menuBar);
      this.setJMenuBar(menuBar);
    } else {
      this.setJMenuBar(menuBar);
    }

    // constants
    sideBarWidth = 300;
    Color sideBarColor = new Color(42, 37, 39);

    // --------------------------MAIN LAYER PANEL----------------------------------

    // layer panel for displaying all layers and adding new ones
    layerPanel = new JPanel();
    layerPanel.setBackground(sideBarColor);
    layerPanel.setPreferredSize(new Dimension(sideBarWidth, programHeight));
    this.setMinimumSize(new Dimension(programWidth - 400, programHeight - 400));

    // top layer panel for displaying the layer options
    topLayerPanel = new JPanel();
    topLayerPanel.setBackground(new Color(100, 100, 100));
    topLayerPanel.setPreferredSize(new Dimension(sideBarWidth, 50));

    layerLabel = new JLabel();
    layerLabel.setText("Layers");
    layerLabel.setLayout(new BorderLayout());
    layerLabel.setForeground(Color.WHITE);
    layerLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
    layerLabel.setHorizontalAlignment(JLabel.LEFT);
    layerLabel.setVerticalAlignment(JLabel.BOTTOM);

    addLayerButton = new JButton("+");
    addLayerButton.setActionCommand("Add Layer");
    addLayerButton.setPreferredSize(new Dimension(20, 20));
    addLayerButton.setEnabled(false);

    // list of layers
    layerListModel = new DefaultListModel<>();
    layerList = new JList<>(layerListModel);
    layerList.setBackground(Color.BLACK);
    layerList.setForeground(Color.WHITE);
    layerList.setFont(new Font("Helvetica", Font.BOLD, 18));
    layerList.setFixedCellWidth(sideBarWidth);
    layerList.setFixedCellHeight(50);
    layerList.setBorder(new EmptyBorder(10, 10, 10, 10));
    layerList.setLayout(new BorderLayout());
    layerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    // add statements
    topLayerPanel.add(addLayerButton, BorderLayout.EAST);
    topLayerPanel.add(layerLabel, BorderLayout.WEST);
    layerPanel.add(topLayerPanel, BorderLayout.NORTH);
    layerPanel.add(layerList, BorderLayout.CENTER);
    this.add(layerPanel, BorderLayout.WEST);

    // --------------------------MAIN WORKSPACE PANEL----------------------------------
    // workspace panel for displaying the image
    workspacePanel = new JPanel();
    workspacePanel.setBackground(new Color(30, 30, 30));
    workspacePanel.setPreferredSize(new Dimension(programWidth - sideBarWidth * 2, programHeight));

    imagePanel = new JPanel(new BorderLayout());
    imagePanel.setPreferredSize(new Dimension(500, 500)); //TODO: adjust these values to scale with screen

    workspaceScroll = new JScrollPane(imageLabel,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    // add statements
    workspacePanel.add(imagePanel, BorderLayout.CENTER);
    imagePanel.add(workspaceScroll, BorderLayout.CENTER);
    imagePanel.setVisible(false);
    this.add(workspacePanel, BorderLayout.CENTER);

    // --------------------------MAIN TOOL PANEL----------------------------------
    // tool panel for displaying all tools
    toolPanel = new JPanel();
    toolPanel.setBackground(sideBarColor);
    toolPanel.setPreferredSize(new Dimension(sideBarWidth, programHeight));

    // top tool panel for displaying the tools
    topToolPanel = new JPanel();
    topToolPanel.setBackground(new Color(100, 100, 100));
    topToolPanel.setPreferredSize(new Dimension(sideBarWidth, 50));

    toolLabel = new JLabel();
    toolLabel.setText("Filters");
    toolLabel.setLayout(new BorderLayout());
    toolLabel.setForeground(Color.WHITE);
    toolLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
    toolLabel.setHorizontalAlignment(JLabel.LEFT);
    toolLabel.setVerticalAlignment(JLabel.BOTTOM);

    applyFilterButton = new JButton("Apply");
    applyFilterButton.setActionCommand("Set Filter");
    applyFilterButton.setPreferredSize(new Dimension(80, 20));
    applyFilterButton.setEnabled(false);

    // filter list
    filterListModel = new DefaultListModel<>();
    filterList = new JList<>(filterListModel);
    filterList.setBackground(Color.BLACK);
    filterList.setForeground(Color.WHITE);
    filterList.setFont(new Font("Helvetica", Font.BOLD, 18));
    filterList.setFixedCellWidth(sideBarWidth);
    filterList.setFixedCellHeight(50);
    filterList.setBorder(new EmptyBorder(10, 10, 10, 10));
    filterList.setLayout(new BorderLayout());
    filterList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    filterListScrollPane = new JScrollPane();
    filterListScrollPane.setViewportView(filterList);

    // add statements
    topToolPanel.add(applyFilterButton, BorderLayout.WEST);
    topToolPanel.add(toolLabel, BorderLayout.EAST);
    toolPanel.add(topToolPanel, BorderLayout.NORTH);
    toolPanel.add(filterList, BorderLayout.CENTER);
    this.add(toolPanel, BorderLayout.EAST);
  }

  private static class CustomInputDialog {

    private JPanel panel = new JPanel();
    private JLabel label1;
    private JLabel label2;
    private String title;
    private JTextField label1Field = new JTextField(10);
    private JTextField label2Field = new JTextField(10);

    public CustomInputDialog(String title, String label1, String label2) {
      this.label1 = new JLabel(label1);
      this.label2 = new JLabel(label2);
      this.title = title;
    }

    public CustomInputDialog(String title, String label1) {
      this.label1 = new JLabel(label1);
      this.title = title;
    }

    private Integer[] getValues() {
      panel.add(label1);
      panel.add(label1Field);
      panel.add(label2);
      panel.add(label2Field);
      this.label1Field.requestFocus();

      int result = JOptionPane.showConfirmDialog(null, panel, title, JOptionPane.OK_CANCEL_OPTION,
          JOptionPane.PLAIN_MESSAGE);

      if (result == JOptionPane.OK_OPTION) {
        int val1 = Integer.parseInt(label1Field.getText());
        int val2 = Integer.parseInt(label2Field.getText());
        return new Integer[]{val1, val2};

      }
      return null;
    }

    private String getImageNameExt() {
      panel.add(label1);
      panel.add(label1Field);

      int result = JOptionPane.showConfirmDialog(null, panel, title, JOptionPane.OK_CANCEL_OPTION,
          JOptionPane.PLAIN_MESSAGE);

      if (result == JOptionPane.OK_OPTION) {
        String val1 = label1Field.getText();
        return val1;
      }
      return null;
    }
  }


  @Override
  public void addFeatures(Features features) {
    addLayerButton.addActionListener(evt -> {
      String layerName = JOptionPane.showInputDialog("Enter layer name:");
      features.addLayer(layerName);
      this.repaint();
      this.revalidate();
    });
    applyFilterButton.addActionListener(evt -> {
      String filterName = filterList.getSelectedValue();
      String layerName = layerList.getSelectedValue();
      features.setFilter(layerName, filterName);
      this.repaint();
      this.revalidate();
    });
    newMenuItem.addActionListener(evt -> {
      if (!isProjectActive) {
        CustomInputDialog heightWidthInput = new CustomInputDialog("New Project", "Enter height:",
            "Enter width:");
        Integer[] valuesHW = heightWidthInput.getValues();
        features.newProject(valuesHW[0], valuesHW[1]);
        this.repaint();
        this.revalidate();
      } else {
        this.renderMessage("Project already being worked on.");
      }
    });
    openProjectMenuItem.addActionListener(evt -> {
      if (!isProjectActive) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open Project");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
          File fileToOpen = fileChooser.getSelectedFile();
          String filepath = fileToOpen.getAbsolutePath();
          features.loadProject(filepath);
          this.repaint();
          this.revalidate();
        }
      } else {
        this.renderMessage("Project already being worked on.");
      }
    });
    quitMenuItem.addActionListener(evt -> features.quit());
    addImageMenuItem.addActionListener(evt -> {
      if (isProjectActive) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open Image");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
          File fileToOpen = fileChooser.getSelectedFile();
          String filepath = fileToOpen.getAbsolutePath();
          String layerName = layerList.getSelectedValue();
          CustomInputDialog heightWidthInput = new CustomInputDialog(
              "Add Image to Layer: " + layerName, "Enter x position:", "Enter y position:");
          Integer[] valuesXY = heightWidthInput.getValues();
          features.addImageToLayer(layerName, filepath, valuesXY[0], valuesXY[1]);
          this.repaint();
          this.revalidate();
        }
      } else {
        this.renderMessage("Start a new project or load in existing project to add image.");
      }
    });
    exportMenuItem.addActionListener(evt -> {
      if (isProjectActive) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose Directory to Save Image");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
          File selectedFolder = fileChooser.getSelectedFile();
          String filepath = selectedFolder.getAbsolutePath();
          features.saveImageAs(filepath);
        }
      } else {
        this.renderMessage("Start a new project or load in existing project to export image.");
      }
    });


  }

  @Override
  public void addLayer(String layerName) {
    if (layerName != null && !layerName.equals("")) {
      layerListModel.addElement(layerName);
      layerList.setSelectedIndex(layerListModel.size() - 1);
      layerList.revalidate();
      layerList.repaint();
      layerPanel.revalidate();
      layerPanel.repaint();
    }
    if (layerListModel.size() >= 1) {
      this.addImageMenuItem.setEnabled(true);
    }
  }

  @Override
  public void addFilters(List<String> filters) {
    for (String filterName : filters) {
      filterListModel.addElement(filterName);
    }
    filterList.revalidate();
    filterList.repaint();
    toolPanel.revalidate();
    toolPanel.repaint();
  }

  @Override
  public void renderImage(PixelInterface[][] image) {
    int height = image.length;
    int width = image[0].length;
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        PixelInterface curPixel = image[row][col];
        int argb =
            (curPixel.getAlpha() << 24) | (curPixel.getRed() << 16) | (curPixel.getGreen() << 8)
                | curPixel.getBlue();
        bufferedImage.setRGB(col, row, argb);
      }
    }
    imageLabel = new JLabel(new ImageIcon(bufferedImage));
    imageLabel.setHorizontalAlignment(JLabel.CENTER);
    imagePanel.removeAll();
    imagePanel.add(imageLabel);
    imagePanel.revalidate();
    imagePanel.repaint();
  }

  @Override
  public void renderMessage(String message) {
    JOptionPane.showMessageDialog(this, message);
  }

  @Override
  public void activateButtons() {
    this.addLayerButton.setEnabled(true);
    this.applyFilterButton.setEnabled(true);
    this.saveProjectMenuItem.setEnabled(true);
    this.exportMenuItem.setEnabled(true);
    this.isProjectActive = true;
    imagePanel.setVisible(true);
  }
}
