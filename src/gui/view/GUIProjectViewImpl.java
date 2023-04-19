package gui.view;

import gui.controller.Features;
import gui.view.GUIProjectViewImpl.CustomInputDialog.FieldValues;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
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

/**
 * Represents the view for the GUI. This class allows for the controller to interact with the view,
 * and update any implementation to allow the user to visibly see how the program will change
 * according to their input.
 */
public class GUIProjectViewImpl extends JFrame implements GUIProjectView {

  private static final int SIDE_BAR_WIDTH = 300;
  private static final int FRAME_MIN_SIZE = 400;
  private static final Color SIDE_BAR_COLOR = new Color(42, 37, 39);
  private static final String WORKING_DIRECTORY_PATH = System.getProperty("user.dir");
  private final JMenuItem newMenuItem;
  private final JMenuItem openProjectMenuItem;
  private final JMenuItem saveProjectMenuItem;
  private final JMenuItem exportMenuItem;
  private final JMenuItem quitMenuItem;
  private final JMenuItem addImageMenuItem;
  // layer stuff
  private final JButton addLayerButton;
  private final JList<String> layerList;
  private final DefaultListModel<String> layerListModel;
  private final JPanel imagePanel;
  private final int imagePanelDefaultWidth;
  private final int imagePanelDefaultHeight;
  // tool stuff
  private final JButton applyFilterButton;
  private final JList<String> filterList;
  private final DefaultListModel<String> filterListModel;
  private boolean isProjectActive;

  /**
   * Constructs the entire view frame and initializes all the components.
   *
   * @param caption the title of the frame
   */
  public GUIProjectViewImpl(String caption) {
    super(caption);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    int programWidth = (int) size.getWidth();
    int programHeight = (int) size.getHeight();
    this.setSize(programWidth, programHeight);
    this.setVisible(true);
    this.setLayout(new BorderLayout());

    // create menu bar
    // menu stuff
    JMenuBar menuBar = new JMenuBar();

    // create file menu
    JMenu fileMenu = new JMenu("File");
    newMenuItem = new JMenuItem("New Project");
    openProjectMenuItem = new JMenuItem("Open Project");
    saveProjectMenuItem = new JMenuItem("Save Project");
    exportMenuItem = new JMenuItem("Export As...");
    quitMenuItem = new JMenuItem("Quit");
    this.exportMenuItem.setEnabled(false);
    this.saveProjectMenuItem.setEnabled(false);

    // create Project menu
    JMenu projectMenu = new JMenu("Project");
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

    // --------------------------MAIN LAYER PANEL----------------------------------

    // layer panel for displaying all layers and adding new ones
    JPanel layerPanel = new JPanel();
    layerPanel.setBackground(SIDE_BAR_COLOR);
    layerPanel.setPreferredSize(new Dimension(SIDE_BAR_WIDTH, programHeight));
    this.setMinimumSize(
        new Dimension(programWidth - FRAME_MIN_SIZE, programHeight - FRAME_MIN_SIZE));

    // top layer panel for displaying the layer options
    JPanel topLayerPanel = new JPanel();
    topLayerPanel.setBackground(new Color(100, 100, 100));
    topLayerPanel.setPreferredSize(new Dimension(SIDE_BAR_WIDTH, 50));

    JLabel layerLabel = new JLabel();
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
    layerList.setFixedCellWidth(SIDE_BAR_WIDTH);
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
    // workspace stuff
    JPanel workspacePanel = new JPanel();
    workspacePanel.setBackground(new Color(30, 30, 30));
    workspacePanel.setPreferredSize(
        new Dimension(programWidth - SIDE_BAR_WIDTH * 2, programHeight));

    imagePanel = new JPanel(new BorderLayout());
    imagePanel.setForeground(Color.BLACK);
    imagePanelDefaultWidth = programWidth / 2;
    imagePanelDefaultHeight = programHeight - SIDE_BAR_WIDTH;

    // add statements
    workspacePanel.add(imagePanel, BorderLayout.CENTER);
    imagePanel.setVisible(false);
    this.add(workspacePanel, BorderLayout.CENTER);

    // --------------------------MAIN TOOL PANEL----------------------------------
    // tool panel for displaying all tools
    JPanel toolPanel = new JPanel();
    toolPanel.setBackground(SIDE_BAR_COLOR);
    toolPanel.setPreferredSize(new Dimension(SIDE_BAR_WIDTH, programHeight));

    // top tool panel for displaying the tools
    JPanel topToolPanel = new JPanel();
    topToolPanel.setBackground(new Color(100, 100, 100));
    topToolPanel.setPreferredSize(new Dimension(SIDE_BAR_WIDTH, 50));

    JLabel toolLabel = new JLabel();
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
    filterList.setFixedCellWidth(SIDE_BAR_WIDTH);
    filterList.setFixedCellHeight(50);
    filterList.setBorder(new EmptyBorder(10, 10, 10, 10));
    filterList.setLayout(new BorderLayout());
    filterList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane filterListScrollPane = new JScrollPane();
    filterListScrollPane.setViewportView(filterList);

    // add statements
    topToolPanel.add(applyFilterButton, BorderLayout.WEST);
    topToolPanel.add(toolLabel, BorderLayout.EAST);
    toolPanel.add(topToolPanel, BorderLayout.NORTH);
    toolPanel.add(filterList, BorderLayout.CENTER);
    this.add(toolPanel, BorderLayout.EAST);
    this.renderMessage("To Create/Open a project, select File.");
  }

  /**
   * Adds the given features of the controller to the view. This allows the view to interact with
   * the controller and tell it when the user has done something related to an action listener.
   *
   * @param features the features of the controller
   */
  @Override
  public void addFeatures(Features features) {
    addLayerButton.addActionListener(evt -> {
      String layerName = JOptionPane.showInputDialog("Enter layer name:");
      features.addLayer(layerName);
    });
    applyFilterButton.addActionListener(evt -> {
      String filterName = filterList.getSelectedValue();
      String layerName = layerList.getSelectedValue();
      features.setFilter(layerName, filterName);
    });
    newMenuItem.addActionListener(evt -> {
      if (!isProjectActive) {
        CustomInputDialog heightWidthInput = new CustomInputDialog("New Project", "Enter height:",
            "Enter width:");
        FieldValues valuesHW = heightWidthInput.getValues();
        features.newProject(valuesHW.getVal1(), valuesHW.getVal2());
      } else {
        this.renderMessage("Project already being worked on.");
      }
    });
    openProjectMenuItem.addActionListener(evt -> {
      if (!isProjectActive) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open Project");
        fileChooser.setCurrentDirectory(new File(WORKING_DIRECTORY_PATH));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
          File fileToOpen = fileChooser.getSelectedFile();
          String filepath = fileToOpen.getAbsolutePath();
          features.loadProject(filepath);
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
        fileChooser.setCurrentDirectory(new File(WORKING_DIRECTORY_PATH));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
          File fileToOpen = fileChooser.getSelectedFile();
          String filepath = fileToOpen.getAbsolutePath();
          String layerName = layerList.getSelectedValue();
          CustomInputDialog heightWidthInput = new CustomInputDialog(
              "Add Image to Layer: " + layerName, "Enter x position:", "Enter y position:");
          FieldValues valuesXY = heightWidthInput.getValues();
          features.addImageToLayer(layerName, filepath, valuesXY.getVal1(), valuesXY.getVal2());
        }
      } else {
        this.renderMessage("Start a new project or load in existing project to add image.");
      }
    });
    exportMenuItem.addActionListener(evt -> {
      if (isProjectActive) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose Directory to Save Image");
        fileChooser.setCurrentDirectory(new File(WORKING_DIRECTORY_PATH));
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
    saveProjectMenuItem.addActionListener(evt -> {
      if (isProjectActive) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose Directory to save Project to");
        fileChooser.setCurrentDirectory(new File(WORKING_DIRECTORY_PATH));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
          File selectedFolder = fileChooser.getSelectedFile();
          String filepath = selectedFolder.getAbsolutePath();
          features.saveProject(filepath);
        }
      } else {
        this.renderMessage("Start a new project or load in existing project to save.");
      }
    });


  }

  /**
   * Adds a new layer to the view's layer list with the given layer name.
   *
   * @param layerName the name of the layer
   */
  @Override
  public void addLayer(String layerName) {
    if (layerName != null && !layerName.equals("")) {
      layerListModel.addElement(layerName);
      layerList.setSelectedIndex(layerListModel.size() - 1);
    }
    if (layerListModel.size() >= 1) {
      this.addImageMenuItem.setEnabled(true);
      this.applyFilterButton.setEnabled(true);
    }
  }

  /**
   * Adds the given filters to the view's filter list so the user can see all available filters.
   *
   * @param filters the list of filter names
   */
  @Override
  public void addFilters(List<String> filters) {
    for (String filterName : filters) {
      filterListModel.addElement(filterName);
    }
  }

  /**
   * Takes in a 2D array of pixels and turns it into a {@code BufferedImage} that the view with then
   * render to the user. This method will replace the existing image with the updated image if
   * called more than once.
   *
   * @param image the 2D array of pixels
   */
  @Override
  public void renderImage(BufferedImage image) {
    int width = image.getWidth();
    int height = image.getHeight();
    imagePanel.setPreferredSize(new Dimension(Math.min(width, imagePanelDefaultWidth),
        Math.min(height, imagePanelDefaultHeight)));
    JLabel imageLabel = new JLabel(new ImageIcon(image));
    JScrollPane workspaceScroll = new JScrollPane(imageLabel,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    imagePanel.removeAll();
    imagePanel.add(workspaceScroll, BorderLayout.CENTER);
  }

  /**
   * Given the String passed in, it will render a popup window with the message to the user.
   *
   * @param message the message to be displayed
   */
  @Override
  public void renderMessage(String message) {
    JOptionPane.showMessageDialog(this, message);
  }

  /**
   * Activates all the buttons in the view once a project has been loaded in or created. This
   * prevents the user trying to add layers, filter layers, or adding images to layers without the
   * program being started.
   */
  @Override
  public void activateButtons() {
    this.addLayerButton.setEnabled(true);
    this.saveProjectMenuItem.setEnabled(true);
    this.exportMenuItem.setEnabled(true);
    this.isProjectActive = true;
    imagePanel.setVisible(true);
  }

  /**
   * Refreshes the entire view and repaints all the components.
   */
  @Override
  public void refresh() {
    this.repaint();
    this.revalidate();
  }

  /**
   * Represents a class that creates a custom input dialog box to solve the issue of handling
   * multiple input fields.
   */
  static class CustomInputDialog {

    private final JPanel panel;
    private final JLabel label1;
    private final JLabel label2;
    private final String title;
    private final JTextField label1Field;
    private final JTextField label2Field;

    /**
     * Constructs a custom input dialog box.
     *
     * @param title  the title of the dialog box
     * @param label1 the first label name
     * @param label2 the second label name
     */
    public CustomInputDialog(String title, String label1, String label2) {
      panel = new JPanel();
      label1Field = new JTextField(10);
      label2Field = new JTextField(10);
      this.label1 = new JLabel(label1);
      this.label2 = new JLabel(label2);
      this.title = title;
    }

    // gets the values from the input fields
    private FieldValues getValues() {
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
        return new FieldValues(val1, val2);
      }
      return null;
    }

    /**
     * Represents a class that holds two values from the input fields.
     */
    static class FieldValues {

      private final int val1;
      private final int val2;

      /**
       * Constructs a FieldValues object.
       *
       * @param val1 the first value
       * @param val2 the second value
       */
      public FieldValues(int val1, int val2) {
        this.val1 = val1;
        this.val2 = val2;
      }

      // getter for val1
      private int getVal1() {
        return val1;
      }

      // getter for val2
      private int getVal2() {
        return val2;
      }
    }
  }
}
