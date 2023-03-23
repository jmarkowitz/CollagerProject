# CollagerProject: Layered and Image Creation

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
The `Pixel` class has the following fields:

* `redVal`
* `greenVal`
* `blueVal`
* `alphaVal`
* `value`
* `intensity`
* `luma`

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
The `Layer` class has the following fields:

* `layerName`
* `filter`
* `grid`

It is important to note that when every layer is created, its default filter is set to "normal".
This ensures that every layer will always have essentially no filter effect on it until the user
decides to change it using the `setFilter()` method. The `setFilter()` method also allows the user
to change the filter of a layer at any time.

---

### 1.3 Filter Representation

#### 1.3.1 `FilterInterface`

The `FilterInterface` represents any functionality and observations to be made specific to
a `AbstractFilter`. For this design, it was chosen that the `FilterInterface` would
extend `Function<LayerInterface, LayerInterface>` and use implement the `apply()` method. This
allows for the `FilterInterface` to be used as a lambda expression if needed. The methods included
in the `FilterInterface` are as follows:

* `apply(LayerInterface layer)`
* `getFilterName()`

#### 1.3.2 `AbstractFilter`

The `AbstractFilter` class implements the `FilterInterface` and represents a single filter that can
be applied to a layer. The `AbstractFilter` class has the following fields:

* `height`
* `width`
* `filterName`

The height and width are included so the filter can iterate through all the pixels in the layer and
apply the filter to each one. The filter name is included so the user can see what filter is being
applied to the layer. It also contains a few protected methods, one of which is to ensure that for
brightening or darkening filters, that the value cannot exceed 255 or go below 0.

#### 1.3.3 `NormalFilter`

The `NormalFilter` class extends the `AbstractFilter` class and represents a filter that will not
affect any pixels in the layer.

#### 1.3.4 `RedFilter`

The `RedFilter` class extends the `AbstractFilter` class and represents a filter that will make the
layer red.

#### 1.3.5 `GreenFilter`

The `GreenFilter` class extends the `AbstractFilter` class and represents a filter that will make
the layer green.

#### 1.3.6 `BlueFilter`

The `BlueFilter` class extends the `AbstractFilter` class and represents a filter that will make the
layer blue.

#### 1.3.7 `BrightenIntensity`

The `BrightenIntensity` class extends the `AbstractFilter` class and represents a filter that will
brighten the layer based on the intensity of the pixels.

#### 1.3.8 `DarkenIntensity`

The `DarkenIntensity` class extends the `AbstractFilter` class and represents a filter that will
darken the layer based on the intensity of the pixels.

#### 1.3.9 `BrightenLuma`

The `BrightenLuma` class extends the `AbstractFilter` class and represents a filter that will
brighten the layer based on the luma of the pixels.

#### 1.3.10 `DarkenLuma`

The `DarkenLuma` class extends the `AbstractFilter` class and represents a filter that will darken
the layer based on the luma of the pixels.

#### 1.3.11 `BrightenValue`

The `BrightenValue` class extends the `AbstractFilter` class and represents a filter that will
brighten the layer based on the value of the pixels.

#### 1.3.12 `DarkenValue`

The `DarkenValue` class extends the `AbstractFilter` class and represents a filter that will darken
the layer based on the value of the pixels.

---

### 1.4 Image Representation

#### 1.4.1 `Image`

The `Image` class represents an image that can be used in a collage. It contains
the `readPPM(String imageFile, int projectHeight, int projectWidth)` method that will take in an
entire ppm file as a string and create a 2D array of pixels with the correct dimensions. It will
throw an `IllegalArgumentException` if the file is not a valid ppm file. Can be changed in future to
handle more image file types. It can also scale the pixel values based on the max value of the file
to ensure any pixel representation can be handled.

---

### 1.5 Project Representation

#### 1.5.1 `ProjectModelState`

The `ProjectModelState` interface represents the running state of a Collager project. This interface
allows for observation of the model's width, height, an ordered map of layers, and a map of all the
filters. This does not allow for any mutation of the width, height, layers, or filters. It includes
the following methods:

* `getWidth() throws IllegalStateException`
    * This method will return the width of the project and throw an `IllegalStateException` if the
      method is called before a project has been created or loaded in.
* `getHeight() throws IllegalStateException`
    * This method will return the height of the project and throw an `IllegalStateException` if the
      method is called before a project has been created or loaded in.
* `getLayers() throws IllegalStateException`
    * This method will return an ordered map of the layers in the project and throw an
      `IllegalStateException` if the method is called before a project has been created or loaded
      in.
* `getFilters() throws IllegalStateException`
    * This method will return a map of all the filters in the project and throw an
      `IllegalStateException` if the method is called before a project has been created or loaded
      in.

#### 1.5.2 `ProjectModel`

The `ProjectModel` interface extends the `ProjectModelState` interface and represents a project that
can be used in the collager program. The interface allows for the user to create a collage and
includes the following.

* creating a new project
* adding layers to the project
* adding images to layers
* adding filters to layers

This interface include the following methods:

* `newProject(int height, int width) throws IllegalArgumentException`
    * This method will create a new project and throw an `IllegalArgumentException` if the height or
      width is to large for the user's computer screen or the height and width provided is negative.
* `addLayer(String layerName)`
    * This method will add a new layer to the project and throw an `IllegalArgumentException` if
      the layer name already exists. This will also throw an `IllegalStateException` if the method
      is called before a project has been created or loaded in.
* `setFilter(String layerName, String filterName) throws IllegalArgumentException, IllegalStateException`
    * This method will set the filter of a layer to the filter name provided and throw an
      `IllegalArgumentException` if the layer name or filter name does not exist. This will also
      throw an `IllegalStateException` if the method is called before a project has been created or
      loaded in.
* `addImageToLayer(String layerName, PixelInterface[][] imagePixelGrid, int x, int y) throws IllegalArgumentException, IllegalStateException`
    * This method will add an image to a layer at the x and y coordinates provided and throw an
      `IllegalArgumentException` if the layer name does not exist or the x and y coordinates are
      outside the bounds of the layer. This will also throw an `IllegalStateException` if the method
      is called before a project has been created or loaded in.

#### 1.5.3 `Project`

The `Project` class implements the `ProjectModel` interface and represents a project that can be
created and edited. It allows the user to create a new project, add layers and images, and set
filters. It contains the following fields:

* `MAX_VALUE = 255`
* `screenWidth`
* `screenHeight`
* `height`
* `width`
* `layerLinkedMap`
* `allFilters`
* `inProgress`

The `MAX_VALUE` field represents the maximum value a pixel can have. The `screenWidth`
and `screenHeight` are the width and height of the user's screen. The `height` and `width` are the
height and width of the project. The `layerLinkedMap` is an ordered map of the layers in the
project. The `allFilters` is a map of all the filters in the project. The `inProgress` field is a
boolean that represents if a project is in progress or not.

---

### 2 View

#### 2.1 `ProjectView`

The `ProjectView` interface represents the view of a Collager project. This interface allows for a
message to be rendered to the user using the `renderMessage(String message)` method. This method
will throw an `IOException` if the transmission of the message to the data output fails.

#### 2.2 `CommandLineTextView`

The `CommandLineTextView` class implements the `ProjectView` interface and represents a view that is
specifically for using in the command line. It's default constructor uses `System.out` but contains
another constructor that takes in an `Appendable` object. This class contains the following fields:

* `viewOut`

The `viewOut` field represents the output stream that the view will use to render messages to the
user.

---

### 3 newstuff.Controller

#### 3.1 `CollagerController`

The `CollagerController` interface represents a controller that can be used to control the model and
update the view accordingly. This interface contains one method to start the program.
The `startProgram()` method will throw an `IllegalStateException` if the method is called before the
model and view have been set.

#### 3.2 `CollagerControllerImpl`

The `CollagerControllerImpl` class implements the `CollagerController` interface and represents the
controller for the Image Collage program. It allows the user to control the program by giving
commands. It contains the following fields:

* `model`
* `view`
* `in`
* `programStarted`
* `knownCommands`

The `model` field represents the model of the program. The `view` field represents the view of the
program. The `in` is how the user can input commands. The `programStarted` field is a boolean that
represents if the program has started or not. This is used so the user cannot try to start a new
project or load a new project while in the middle of working with one. The `knownCommands` field is
a map of all the known commands and their corresponding command function objects.

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

#### 3.11 `FileUtil`

The `FileUtil` class represents a utility class that contains method the `readFileAsString` method
that can be used to read in a file and turn it into a string.

---

### 4 Running The Program

#### 4.1 Commands

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
* ````save-image filepath````
    * This command will save the image to the filepath provided.
* ````q```` or ````quit````
    * This command will exit the program and will **NOT** save the project or image.

#### 4.2 Example New Project Workflow

1. ````new-project 100 100````
2. ````add-layer layer1````
3. ````add-image-to-layer layer1 images/image1.ppm 0 0````
   ***Note: The FileUtil gets the directory of where the src directory is stored, so images
   directory must be in same directory as src***
4. ````set-filter layer1 brighten-intensity````
5. ````save-image images/imageNew.ppm````
6. ````save-project projects/projectNew.ppm````
   ***Note: The FileUtil gets the directory of where the src directory is stored, so projects
   directory must be in same directory as src***
7. ````q````

***Note: use the exampleCommands.txt for running all commands or follow above.***

#### 4.3 Example Load Project Workflow

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

### 5 References

1. [Assignment Page](https://northeastern.instructure.com/courses/133134/assignments/1776187)
2. [Java Docs](https://docs.oracle.com/en/java/javase/11/docs/api/)
3. [Getting Screen Resolution](https://www.geeksforgeeks.org/java-program-to-print-screen-resolution/)
4. [LinkedHashMap](https://www.geeksforgeeks.org/java-program-to-print-screen-resolution/)
5. [Using Markdown](https://medium.com/@saumya.ranjan/how-to-write-a-readme-md-file-markdown-file-20cb7cbcd6f)
6. TurtleGraphics Command Design Pattern from Class Notes
7. [Thanos Truck Image](https://wompampsupport.azureedge.net/fetchimage?siteId=7575&v=2&jpgQuality=100&width=700&url=https%3A%2F%2Fi.kym-cdn.com%2Fentries%2Ficons%2Ffacebook%2F000%2F027%2F072%2Fthanos_car_2.jpg)
8. ImageUtil file provided by Professor
