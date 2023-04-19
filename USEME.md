## Introduction

Welcome! The information below will give a brief overview of the GUI and how to get started. Looking
for more documentation on the actual program? [Click Me!](/README.md)

---

## Getting Started

The program can be run in three different ways depending on the command line arguments given:

1. No arguments: The program will run in GUI mode (also what happens when double-click on jar file).
    * `java -jar CollagerProject.jar`
2. Command Line Text Mode: The program will wait for commands given by the
   user.
    * `java -jar CollagerProject.jar -text`
3. Batch Command Run Mode: The program will run a batch of commands from a text file given the
   filepath as the second command line argument.
    * `java -jar CollagerProject.jar -file path-of-script-file`

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

### Command Line Use

#### Commands

* ````new-project height width````
    * This command will create a new project with the height and width provided.
* ````load-project filepath````
    * This command will load in a project from the filepath provided.
* ````save-project filepath````
    * This command will save the project to the filepath provided.
* ````add-layer layer-name````
    * This command will add a layer to the project.
* ````add-image-to-layer layer-name filepath x y````
    * This command will add an image to the layer with the name provided.
* ````set-filter layer-name filter-option````
    * This command will set a filter to the layer with the name provided.
    * The filter options are:
        * ````red-component````
        * ````green-component````
        * ````blue-component````
        * ````brighten-value````
        * ````darken-value````
        * ````brighten-intensity````
        * ````darken-intensity````
        * ````brighten-luma````
        * ````darken-luma````
        * ````differnce````
        * ````multiply````
        * ````screen````
* ````save-image filepath````
    * This command will save the image to the filepath provided.
* ````q```` or ````quit````
    * This command will exit the program and will **NOT** save the project or image.

#### Example New Project Workflow

1. ````new-project 100 100````
2. ````add-layer layer1````
3. ````add-image-to-layer layer1 images/image1.ppm 0 0````
4. ````set-filter layer1 brighten-intensity````
5. ````save-image images/imageNew.ppm````
6. ````save-project projects/projectNew.ppm````
7. ````q````

#### Example Load Project Workflow

1. ````load-project projects/projectNew.ppm````
2. ````add-layer layer2````
3. ````add-image-to-layer layer2 images/image2.ppm 0 0````
4. ````set-filter layer2 brighten-intensity````
5. ````save-image images/imageNew.ppm````
6. ````save-project projects/projectNew.ppm````
7. ````q````
