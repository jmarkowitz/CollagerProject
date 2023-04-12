# CollagerProject: Layered and Image Creation

---

## Decoupling the View

In order to pass this project along to anyone else, the only files that would need to be sent are:

**Command Line**

* `ProjectView.java` Interface
* `CommandLineTextView.java` class (implements `ProjectView`)
    * These are the only files required as the view is decoupled from the controller for the command
      line version of the program.

**GUI**

* `GUIProjectView.java` Interface
* `GUIProjectViewImpl.java` class (implements `GUIProjectView`)
* `Feature.java` Interface
    * The `Feature` interface is necessary as there is coupling between the controller and view in
      order to allow for asynchronous user input. The `Feature` interface is used to allow for the
      controller to be able to call methods in the view without having to know what the view is.

---

## Recent Implementations/Changes

* Changed the `renderImage()` method in the `GUIProjectView` interface to take in a `BufferedImage`
  instead of a `PixelInterface[][]`. This was done to decouple the view from the model and allow for
  flexibility of our design.
* Added a private helper method in the `FeatureController` to turn a `PixelInterface[][]` into a
  `BufferedImage` to be used in the `renderImage()` method.

---

## Previous Implementations/Changes (Before 4/11/2023)

* Added `compressLayers()` method to `ProjectModelState` interface to allow for the user to
  compress all the layers into one image [Click Me!](#141-projectModelState)
* Added `exportProject()` method to `ProjectModelState` interface to allow for the user to
  export the project as a string [Click Me!](#141-projectmodelstate)
* Added `buildProject()` method to `ProjectModel` interface to allow for the user to build a
  project from a string [Click Me!](#142-projectmodel)
* Added `FileHandler` interface and respective classes to handle file
  input/output [Click Me!](#311-filehandler)
* Changed Filter design to use `BiFunction` instead of `Function` interface to allow for
  background layer to be passed in as well [Click Me!](#131-filterinterface)
* Added `DifferenceFilter`, `MultiplyFilter`, and `ScreenFilter` to the filter
  options [Click Me!](#1313-differencefilter)
* Added `HSLUtil` class to handle conversion between RGB and HSL and back (to help with
  `MultiplyFilter` and `ScreenFilter`) [Click Me!](#1316-hslutil)
* Added `Feature` interface and `FeatureController` class to handle user input for an asynchronous
  controller [Click Me!](#41-features)
* Added `GUIProjectView` interface and `GUIProjectViewImpl` class to handle user specific inputs and
  visualizations [Click Me!](#43-guiprojectview)
* Added a USEME.md file to help understand how the GUI should be used and interacted
  with [Click Me!](/USEME.md)

---

## Introduction

Welcome! The information below will first describe the design of the program and how everything is
represented. Then it will go over commands that can be used in the program to start a collage.
---

## Design

### 1 Model

### 1.1 Pixel Representation

#### 1.1.1 `PixelInterface`

The `PixelInterface` represents any functionality and observations to be made specific to a `Pixel`.
This interface allows certain fields to be accessed and allows for conversion from RGBA to RGB. The
methods included in the `PixelInterface` are as follows:

* `getRed()`
* `getGreen()`
* `getBlue()`
* `getAlpha()`
* `getValue()`
* `getIntensity()`
* `getLuma()`
* `bgPixelConverter(int red, int green, int blue, int alpha)`
* `convertToRGB()`
* `toString()`

#### 1.1.2 `Pixel`

The `Pixel` class implements the `PixelInterface` and represents a single pixel in an image.

Since the value, intensity, and luma are created when a new pixel is constructed, it is easy to
later get each field and use it for other purposes, such as filters.

---

### 1.2 Layer Representation

#### 1.2.1 `LayerInterface`

The `LayerInterface` represents any functionality and observations to be made specific to a `Layer`.
For this design a layer represents a collection of pixels. The methods included in
the `LayerInterface` are as follows:

* `getName()`
* `getFilter()`
* `setFilter(String filterOption)`
* `getPixelGrid()`

#### 1.2.2 `Layer`

The `Layer` class implements the `LayerInterface` and represents a single layer in a collage.

It is important to note that when every layer is created, its default filter is set to "normal".
This ensures that every layer will always have essentially no filter effect on it until the user
decides to change it using the `setFilter()` method. The `setFilter()` method also allows the user
to change the filter of a layer at any time.

---

### 1.3 Filter Representation

#### 1.3.1 `FilterInterface`

The `FilterInterface` represents any functionality and observations to be made specific to
a `AbstractFilter`. For this design, it was chosen that the `FilterInterface` would
extend `BiFunction` and use implement the `apply()` method. This
allows for the `FilterInterface` to be used as a lambda expression if needed. The methods included
in the `FilterInterface` are as follows:

* `apply(PixelInterface[][] curLayer, PixelInterface[][] bgLayer)`

#### 1.3.2 `AbstractFilter`

The `AbstractFilter` class implements the `FilterInterface` and represents a single filter that can
be applied to a layer.

The height and width are included so the filter can iterate through all the pixels in the layer and
apply the filter to each one. The filter name is included so the user can see what filter is being
applied to the layer. It also contains a few protected methods, one of which is to ensure that for
brightening or darkening filters, that the value cannot exceed 255 or go below 0.

#### 1.3.3 `NormalFilter`

* will not affect any pixels in the layer or change back to no effect.

#### 1.3.4 `RedFilter`

* will make the layer red.

#### 1.3.5 `GreenFilter`

* will make the layer green.

#### 1.3.6 `BlueFilter`

* will make the layer blue.

#### 1.3.7 `BrightenIntensityFilter`

* will brighten the layer based on the intensity of the pixels.

#### 1.3.8 `DarkenIntensityFilter`

* will darken the layer based on the intensity of the pixels.

#### 1.3.9 `BrightenLumaFilter`

* will brighten the layer based on the luma of the pixels.

#### 1.3.10 `DarkenLumaFilter`

* will darken the layer based on the luma of the pixels.

#### 1.3.11 `BrightenValueFilter`

* will brighten the layer based on the value of the pixels.

#### 1.3.12 `DarkenValueFilter`

* will darken the layer based on the value of the pixels.

#### 1.3.13 `DifferenceFilter`

* will subtract the image's pixels behind the current image's pixels and blend them together

#### 1.3.14 `MultiplyFilter`

* will convert the pixel values to HSL and then multiply the lightness value of the background
  image's pixels by the current image's pixels

#### 1.3.15 `ScreenFilter`

* will convert the pixel values into HSL and then brighten and blend the current image's pixels with
  the background image's pixels

#### 1.3.16 `HSLUtil`

* will convert the pixel values to HSL and back to RGB used for the MultiplyFilter and ScreenFilter

---

### 1.4 Project Representation

#### 1.4.1 `ProjectModelState`

The `ProjectModelState` interface represents the running state of a Collager project. This interface
allows for observation of the model's width, height, an ordered map of layers, and a map of all the
filters, the current image the model contains, and the current string that represents the project.
This does not allow for any mutation of the width, height, layers, or filters. It includes the
following methods:

* `getWidth() throws IllegalStateException`
* `getHeight() throws IllegalStateException`
* `getLayers() throws IllegalStateException`
* `getFilters() throws IllegalStateException`
* `exportProject() throws IllegalStateException`
* `compressLayers() throws IllegalStateException`

#### 1.4.2 `ProjectModel`

The `ProjectModel` interface extends the `ProjectModelState` interface and represents a project that
can be used in the collager program. The interface allows for the user to create a collage and
includes the following.

* creating a new project
* building a project from a string (represents the project)
* adding layers to the project
* adding images to layers
* adding filters to layers

This interface includes the following methods:

* `newProject()`
* `addLayer()`
* `setFilter()`
* `addImageToLayer()`
* `buildProject()`

#### 1.4.3 `Project`

The `Project` class implements the `ProjectModel` interface and represents a project that can be
created and edited. It allows the user to create/load a project, add layers and images, and set
filters.

---

### 2 View

#### 2.1 `ProjectView`

The `ProjectView` interface represents the view of a Collager project. This interface allows for a
message to be rendered to the user using the `renderMessage(String message)` method. This method
will throw an `IOException` if the transmission of the message to the data output fails.

#### 2.2 `CommandLineTextView`

The `CommandLineTextView` class implements the `ProjectView` interface and represents a view that is
specifically for using in the command line. It's default constructor uses `System.out` but contains
another constructor that takes in an `Appendable` object.

---

### 3 Controller

#### 3.1 `CollagerController`

The `CollagerController` interface represents a controller that can be used to control the model and
update the view accordingly. This interface contains one method to start the program.
The `startProgram()` method will throw an `IllegalStateException` if the method is called before the
model and view have been set.

#### 3.2 `CollagerControllerImpl`

The `CollagerControllerImpl` class implements the `CollagerController` interface and represents the
controller for the Image Collage program. It allows the user to control the program by giving
commands.

#### 3.3 `CollagerCommand`

The `CollagerCommand` interface represents a command that can be used in the Collager program. This
interface has one method called `execute(ProjectModel model) throws IOException`. This method will
throw an `IOException` if the command fails to execute. Any command subclass that implements this
will be able to change the model in some way based on the command chosen.

#### 3.4 `NewProject`

The `NewProject` class implements the `CollagerCommand` interface and represents a command that
allows the user to create a new project with the supplied height and width.

#### 3.5 `LoadProject`

The `LoadProject` class implements the `CollagerCommand` interface and represents a command that
allows the user to load in a project from a supplied project filepath. This command will throw
an `IllegalArgumentException` if the filepath does not exist or is not a valid project file.

#### 3.6 `SaveProject`

The `SaveProject` class implements the `CollagerCommand` interface and represents a command that
saves the entire model to a file with the max value of a pixel, the height, the width, and all the
layers and their respective filter name, and all unmodified pixels in that layer.

#### 3.7 `AddLayer`

The `AddLayer` class implements the `CollagerCommand` interface and represents a command that allows
the user to add a layer to the project.

#### 3.8 `AddImage`

The `AddImage` class implements the `CollagerCommand` interface and represents a command that allows
the user to add an image to a layer, given the filepath of the image and an x and y offset.

#### 3.9 `SetFilter`

The `SetFilter` class implements the `CollagerCommand` interface and represents a command that
allows the user to set a filter to a layer based on the existing filters in the model.

#### 3.10 `SaveImage`

The `SaveImage` class implements the `CollagerCommand` interface and represents a command that
allows the user to save an image to a filepath. This command will compress all layers and their
respective filters into one image file.

#### 3.11 `FileHandler`

* interface represents a class that can be used to handle files. This interface contains the
  following methods:
    * `readFile()`
    * `writeFile()`

Any class that implements this interface will be able to read and write files based on their
implementation. This interface allows for extensibility in the future to handle different kinds of
image files or project files.

#### 3.12 `AbstractFileHandler`

* represents a class that can be used to handle generic files.

#### 3.13 `ImageHandler`

* represents a class that can be used to handle image files such as .png and .jpg.

#### 3.14 `PPMHanlder`

* represents a class that can be used to handle .ppm image files.

#### 3.15 `TextProjectHandler`

* represents a class that can be used to handle .collage project files.

#### 3.16 `FileUtil`

* represents a utility class that can be used to read a file as a string.

---

### 4 Graphical User Interface (GUI)

#### 4.1 `Features`

* interface represents a class that exposes all features that can be used to handle user inputs and
  update the view accordingly. This interface contains the following methods:
    * `newProject()`
    * `loadProject()`
    * `saveProject()`
    * `saveImageAs()`
    * `addLayer()`
    * `addImageToLayer()`
    * `setFilter()`
    * `getFilters()`
    * `quit()`

#### 4.2 `FeatureController`

* class implements the `Features` interface and implements all the features that can be used to
  handle user inputs and update the view accordingly.

#### 4.3 `GUIProjectView`

* Represents the view for the GUI. This interface allows for the controller to interact with the
  view, and update any implementation to allow the user to visibly see how the program will change
  according to their input. This interface contains the following methods:
    * `addFeatures()`
    * `addLayer()`
    * `addFilters()`
    * `renderImage()`
    * `renderMessage()`
    * `activateButtons()`
    * `refresh()`

#### 4.4 `GUIProjectViewImpl`

* class implements the `GUIProjectView` interface and represents the view for the GUI. This class
  allows for the controller to interact with the view, and update any implementation to allow the
  user to visibly see how the program will change according to their input.

---

### 5 Running The Program

#### 5.1 Commands

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

#### 5.2 Example New Project Workflow

1. ````new-project 100 100````
2. ````add-layer layer1````
3. ````add-image-to-layer layer1 images/image1.ppm 0 0````
4. ````set-filter layer1 brighten-intensity````
5. ````save-image images/imageNew.ppm````
6. ````save-project projects/projectNew.ppm````
7. ````q````

***Note: use the batch_commands.txt for running all commands or follow above.***

#### 5.3 Example Load Project Workflow

1. ````load-project projects/projectNew.ppm````
2. ````add-layer layer2````
3. ````add-image-to-layer layer2 images/image2.ppm 0 0````
4. ````set-filter layer2 brighten-intensity````
5. ````save-image images/imageNew.ppm````
6. ````save-project projects/projectNew.ppm````
7. ````q````

## Enjoy!

### Authors

* Jonathan Markowitz (markowitz.jo@northeastern.edu)
* Yige Sun (sun.yig@northeastern.edu)

### 6 References

1. [Assignment Page](https://northeastern.instructure.com/courses/133134/assignments/1776187)
2. [Java Docs](https://docs.oracle.com/en/java/javase/11/docs/api/)
3. [Getting Screen Resolution](https://www.geeksforgeeks.org/java-program-to-print-screen-resolution/)
4. [LinkedHashMap](https://www.geeksforgeeks.org/java-program-to-print-screen-resolution/)
5. [Using Markdown](https://medium.com/@saumya.ranjan/how-to-write-a-readme-md-file-markdown-file-20cb7cbcd6f)
6. TurtleGraphics Command Design Pattern from Class Notes
7. [Thanos Truck Image](https://wompampsupport.azureedge.net/fetchimage?siteId=7575&v=2&jpgQuality=100&width=700&url=https%3A%2F%2Fi.kym-cdn.com%2Fentries%2Ficons%2Ffacebook%2F000%2F027%2F072%2Fthanos_car_2.jpg)
8. ImageUtil file provided by Professor
9. [Java Swing Tutorial](https://www.youtube.com/watch?v=Kmgo00avvEw)
10. [BiFunction in Java](https://www.geeksforgeeks.org/java-bifunction-interface-methods-apply-and-addthen/)
11. [JList Tutorial](https://www.youtube.com/watch?v=WiX3n2BMGIc)
12. [JListModel Tutorial](http://www.seasite.niu.edu/cs580java/JList_Basics.htm)
13. Callback Command Design Pattern from Class Notes
14. HSLUtil file provided by Professor
15. [HSL and HSV Color Models](https://en.wikipedia.org/wiki/HSL_and_HSV)
16. [Putting File Bar in Native Location for Mac](https://stackoverflow.com/questions/8955638/how-do-i-move-my-jmenubar-to-the-screen-menu-bar-on-mac-os-x)
