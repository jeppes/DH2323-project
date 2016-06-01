+++
date = "2016-05-30T15:45:55+02:00"
draft = false
title = "Arbitrary Morph Animations"

+++

## Reversing the animation 
The morph animation can now be reversed as shown in the following clip

{{< mp4 src="/videos/morph-to-bottom.mp4" >}}

Note the arc of the path. This effect is achieved by translating the view along a quadradic bezier path which is calculated based on the positions of the two views relative to one-another. The icon in the floating action button is also faded, which results in a smoother transition between the two views. 

## Arbitary Positions
The animation works for arbitrary positions:

{{< mp4 src="/videos/morph-to-middle.mp4" >}}
