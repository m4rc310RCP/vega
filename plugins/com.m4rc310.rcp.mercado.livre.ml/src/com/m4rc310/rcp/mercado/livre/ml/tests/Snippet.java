package com.m4rc310.rcp.mercado.livre.ml.tests;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class Snippet {public static void main (String [] args) {
    Display display = new Display ();
    Shell shell = new Shell (display);
    shell.setLayout(new FillLayout());
    final Tree tree = new Tree (shell, SWT.BORDER);
    for (int i=0; i<4; i++) {
        TreeItem iItem = new TreeItem (tree, 0);
        iItem.setText ("TreeItem (0) -" + i);
        for (int j=0; j<4; j++) {
            TreeItem jItem = new TreeItem (iItem, 0);
            jItem.setText ("TreeItem (1) -" + j);
            for (int k=0; k<4; k++) {
                TreeItem kItem = new TreeItem (jItem, 0);
                kItem.setText ("TreeItem (2) -" + k);
                for (int l=0; l<4; l++) {
                    TreeItem lItem = new TreeItem (kItem, 0);
                    lItem.setText ("TreeItem (3) -" + l);
                }
            }
        }
    }

    tree.addListener(SWT.PaintItem, new Listener() {

        @Override
        public void handleEvent(Event event) {
            GC gc = event.gc;
            TreeItem item = (TreeItem) event.item;
            Tree tree = (Tree) event.widget;
            Rectangle rect = tree.getClientArea();
            Rectangle bounds = item.getBounds();

            /**
             * expand/collapse native control bounds
             */

             TreeItem  parentItem= item.getParentItem();

             Rectangle ecb = null;
             if(parentItem != null){
                 Rectangle pBounds = parentItem.getBounds();
                 ecb = new Rectangle(pBounds.x, bounds.y, Math.abs(pBounds.x-bounds.x), bounds.height);
             }
             else
             {
                 ecb = new Rectangle(rect.x, bounds.y, Math.abs(rect.x-bounds.x), bounds.height);
             }

            gc.setAlpha(200);

            /**
             * paint image in this bounds depending on state
             * testing purpose: filling background
             */
            if(item.getExpanded()){
                gc.setBackground(event.display.getSystemColor(SWT.COLOR_RED));
                gc.fillRectangle(ecb.x,ecb.y,ecb.width,ecb.height);
            }
            else
            {
                gc.setBackground(event.display.getSystemColor(SWT.COLOR_CYAN));
                gc.fillRectangle(ecb.x,ecb.y,ecb.width,ecb.height);
            }
        }
    });
    shell.setSize (200, 200);
    shell.open ();
    while (!shell.isDisposed()) {
        if (!display.readAndDispatch ()) display.sleep ();
    }
    display.dispose ();
}
} 
