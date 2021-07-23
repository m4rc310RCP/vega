package com.m4rc310.vega.rcp.parts;

/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

/*
 * Image example snippet: display an animated GIF
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Snippet141
{
    static Display display;

    static Shell shell;

    static GC shellGC;

    static Color shellBackground;

    // static ImageLoader loader;

    static ImageData[] imageDataArray;

    static Thread animateThread;

    static Image image;

    static final boolean useGIFBackground = false;

    public static void main(String[] args)
    {
        display = new Display();
        shell = new Shell(display);
        shell.setSize(300, 300);
        shell.open();
        shellGC = new GC(shell);
        shellBackground = shell.getBackground();

        // FileDialog dialog = new FileDialog(shell);
        // dialog.setFilterExtensions(new String[] { "*.gif" });
        String fileName = "";
        // dialog.open();
        if (fileName != null)
        {
            // loader = new ImageLoader();
            try
            {
                imageDataArray = new ImageData[] { new ImageData("c:\\Blue hills.jpg"),
                        new ImageData("c:\\Sunset.jpg"), new ImageData("c:\\Water lilies.jpg"),
                        new ImageData("c:\\Winter.jpg") };
                if (imageDataArray.length > 1)
                {
                    animateThread = new Thread("Animation")
                    {
                        @Override
                        public void run()
                        {
                            /* Create an off-screen image to draw on, and fill it with the shell background. */
                            Image offScreenImage = new Image(display, 400, 300);
                            GC offScreenImageGC = new GC(offScreenImage);
                            offScreenImageGC.setBackground(shellBackground);
                            offScreenImageGC.fillRectangle(0, 0, 400, 300);

                            try
                            {
                                /* Create the first image and draw it on the off-screen image. */
                                int imageDataIndex = 0;
                                ImageData imageData = imageDataArray[imageDataIndex];
                                if (image != null && !image.isDisposed()) image.dispose();
                                image = new Image(display, imageData);
                                offScreenImageGC.drawImage(image, 0, 0, imageData.width,
                                        imageData.height, imageData.x, imageData.y,
                                        imageData.width, imageData.height);

                                /* Now loop through the images, creating and drawing each one
                                 * on the off-screen image before drawing it on the shell. */
                                int repeatCount = 50;
                                while (repeatCount > 0)
                                {
                                    switch (imageData.disposalMethod)
                                    {
                                        case SWT.DM_FILL_BACKGROUND:
                                            /* Fill with the background color before drawing. */
                                            Color bgColor = null;
                                            // if (useGIFBackground && loader.backgroundPixel !=
                                            // -1)
                                            // {
                                            // bgColor = new Color(display, imageData.palette
                                            // .getRGB(loader.backgroundPixel));
                                            // }
                                            offScreenImageGC
                                                    .setBackground(bgColor != null ? bgColor
                                                            : shellBackground);
                                            offScreenImageGC
                                                    .fillRectangle(imageData.x, imageData.y,
                                                            imageData.width, imageData.height);
//                                            if (bgColor != null) bgColor.dispose();
                                            break;
                                        case SWT.DM_FILL_PREVIOUS:
                                            /* Restore the previous image before drawing. */
                                            offScreenImageGC.drawImage(image, 0, 0,
                                                    imageData.width, imageData.height,
                                                    imageData.x, imageData.y, imageData.width,
                                                    imageData.height);
                                            break;
                                    }

                                    imageDataIndex = (imageDataIndex + 1) % imageDataArray.length;
                                    imageData = imageDataArray[imageDataIndex];
                                    image.dispose();
                                    image = new Image(display, imageData);
                                    offScreenImageGC.drawImage(image, 0, 0, imageData.width,
                                            imageData.height, imageData.x, imageData.y,
                                            imageData.width, imageData.height);

                                    /* Draw the off-screen image to the shell. */
                                    shellGC.drawImage(offScreenImage, 0, 0);

                                    /* Sleep for the specified delay time (adding commonly-used slow-down fudge factors). */
                                    try
                                    {
                                        int ms = imageData.delayTime * 10;
                                        if (ms < 20) ms += 30;
                                        if (ms < 30) ms += 10;
                                        Thread.sleep(500);
                                    }
                                    catch (InterruptedException e)
                                    {
                                    }

                                    /* If we have just drawn the last image, decrement the repeat count and start again. */
                                    if (imageDataIndex == imageDataArray.length - 1) repeatCount--;
                                }
                            }
                            catch (SWTException ex)
                            {
                                System.out.println("There was an error animating the GIF");
                            }
                            finally
                            {
                                if (offScreenImage != null && !offScreenImage.isDisposed()) offScreenImage
                                        .dispose();
                                if (offScreenImageGC != null && !offScreenImageGC.isDisposed()) offScreenImageGC
                                        .dispose();
                                if (image != null && !image.isDisposed()) image.dispose();
                            }
                        }
                    };
                    animateThread.setDaemon(true);
                    animateThread.start();
                }
            }
            catch (SWTException ex)
            {
                System.out.println("There was an error loading the GIF");
            }
        }

        while (!shell.isDisposed())
        {
            if (!display.readAndDispatch()) display.sleep();
        }
        display.dispose();
    }
}