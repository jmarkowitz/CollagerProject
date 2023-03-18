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
This interface allows certain fields to be accessed and allows for conversion from RGBA to RGB. The methods included in the `PixelInterface` are as follows:
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

The `Pixel` class implements the `PixelInterface` and represents a single pixel in an image. The `Pixel` class has the following fields:

### 2 View
### 3 Controller
### 4 Commands


https://www.geeksforgeeks.org/java-program-to-print-screen-resolution/