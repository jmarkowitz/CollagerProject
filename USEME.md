## Introduction

Welcome! The information below will give a brief overview of the GUI and how to get started. Looking
for more documentation on the actual program? [Click Me!](/README.md)

---

## Getting Started

The program can be run in three different ways depending on the command line arguments given:

1. No arguments: The program will run in GUI mode (also what happens when double-click on jar file).
    * `java -jar CollagerProject.jar`
2. Command Line Text Mode: The program will wait for commands given by the
   user. [Click here](/README.md#51-commands) to see what commands are available.
    * `java -jar CollagerProject.jar -text`
3. Batch Command Run Mode: The program will run a batch of commands from a text file given the
   filepath as the second command line argument.
    * `java -jar Program.jar -file path-of-script-file`

---

## GUI Mode Use

Upon opening the program, the user will be greeted with a blank canvas and a dialog message that
instructs the user to press **File** > **New Project** or **Open Project**.

### Layout

When a GUI is started, the user will see three main panels:

1. The Menu Bar: contains all the options for the program. The options are:
    * File: contains the options to create a new project, open a project, save a project, save an
      image, and
      quit the program.
    * Project: contains the option to add an image to a layer.
2. The Layer Panel: contains all the layers in the project, and allows the user to add a new layer.
   To add a layer click the **+** button. Input a name for the layer in the dialog popup, and it
   will add it to the list
   on the left.
3. The Workspace Panel: contains the canvas and the image that is being worked on. If the project
   being created is too large for the panel, scroll bars will allow the user to scroll to the limits
   of the canvas. The canvas will be updated as the user adds images to layers or applies filters.
4. The Tool/Filter Panel: contains all the available filters or effects. To add a filter, select the
   layer, then select the filter and press the **Apply** button. To remove a filter, select the
   layer, then select the **Normal** filter and press apply.

### Example Workflow

* Create a new project/Open a project
    * File > New Project
        * Input the width and height of the project
    * File > Open Project
        * Select the project file to open (.collage)
* Add a layer
    * Input a name for the layer
* Add an image to the layer
    * Select the layer
    * Select Project > Add Image To Selected Layer
    * choose the image file to add (supports .ppm, .png, jpg/jpeg)
    * input the x and y coordinates of where to place the top left corner of image
* Apply a filter
    * Select the layer
    * Select the filter
    * Press the **Apply** button
* Save the project
    * File > Save Project
        * Select the directory to save the project file
        * Input the name of the file with the proper extension (.collage)
    * File > Save Image
        * Select the directory to save the image file
        * Input the name of the file with the proper extension (.ppm, .png)
* Quit the program
    * File > Quit
