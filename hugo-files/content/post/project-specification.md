+++
date = "2016-05-24T11:47:59+02:00"
draft = false
title = "Project Specification"

+++

# Background
Animations are key when creating engaging user interfaces. The aim of this project will be to implement advanced GUI-component animations in the Android operating system to add visual interest to user interfaces and spur user-engagement. 

The inspiration for this project comes from Google’s Material Design. A core concept in this design language is motion. The official guidelines states that “Real-world forces, like gravity, [should] inspire an element’s movement [...].” It also highlights the importance of “Hierarchical and spatial relationships between elements”. Many of these motion elements have not yet made their way into the Android eco-system (at least not in terms of first-party implementations).

# Goals
  - Integrating natural forces like gravity into animations to allow for more natural transformations of elements in a layout. The implications of this are explored further in the “Animation philosophy” section.
Implementing a morphing/merging animation between views. One view should be able to transition into another when acted on by the user. Some example from the material design specification of what this can look like can be found here: 
  - Making these animations convincing will require carefully considering the interpolations functions used, the motion path between the views and creating a convincing illusion of a morphing between these views.  


<center> {{<mp4 src="http://material-design.storage.googleapis.com/publish/material_v_8/material_ext_publish/0B8v7jImPsDi-Mk9EVk5DRVN1cmc/components-buttons-fab-transition_card_01.mp4">}}
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{<mp4 src="http://material-design.storage.googleapis.com/publish/material_v_8/material_ext_publish/0B8v7jImPsDi-bWxnMGxHUFdKbkU/components-buttons-fab-transition_morph_01.mp4">}} </center>


# Animation Philosophy
Animations should accelerate and decelerate naturally, and follow a natural easing-curve determined by physical concepts such as gravity and attraction between objects. Animations should be deliberate and follow a set hierarchy. They should be able to affect the surrounding environment in a radial spreading motion, similar to how water behaves when touched. Here are some examples to demonstrate some of these principles:

<center> {{<mp4 src="//material-design.storage.googleapis.com/publish/material_v_8/material_ext_publish/0B14F_FSUCc01eGdZQnRQYlRXek0/01_GridRipple.mp4">}} </center>
<center> {{<mp4 src="http://material-design.storage.googleapis.com/publish/material_v_8/material_ext_publish/0B14F_FSUCc01NVRScVpxWTVXeVU/02_SearchRipple-v2.mp4">}} {{<mp4 src="http://material-design.storage.googleapis.com/publish/material_v_8/material_ext_publish/0B14F_FSUCc01SktIam0yei0wSnM/Natural_02_Arc-v3.mp4">}} </center>

# Implementation Details
This project will include exploring the use of interpolation and paths in motion based transformations, and allowing gravity to inspire this motion. Since we are building this for the Android platform, the implementation language will be Java. If time permits it we would like to put these features into a library so other developers can implement these features into their own apps. This is, however, outside the initial scope of this investigation.
