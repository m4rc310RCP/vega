package com.m4rc310.rcp.mercado.livre.ml.tests;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class DropdownExperiment {
	public static void main(String[] args) {
        Display display = new Display();
        final Shell shell = new Shell(display);
        shell.setSize(300, 200);
        shell.setText("Button Example");
        shell.setLayout(new RowLayout());


        /**
         * 
         * Approach1
         * 
         */
        final Composite btnCntrl = new Composite(shell, SWT.BORDER);
        btnCntrl.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
        btnCntrl.setBackgroundMode(SWT.INHERIT_FORCE);
        GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).spacing(0, 1).applyTo(btnCntrl);
        CLabel lbl = new CLabel(btnCntrl, SWT.NONE);
        lbl.setText("Animals");
        Button btn = new Button(btnCntrl, SWT.FLAT|SWT.ARROW|SWT.DOWN);
        btn.setLayoutData(new GridData(GridData.FILL_VERTICAL));

        btn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                super.widgetSelected(e);
                Menu menu = new Menu(shell, SWT.POP_UP);

                MenuItem item1 = new MenuItem(menu, SWT.PUSH);
                item1.setText("Hare");
                MenuItem item2 = new MenuItem(menu, SWT.PUSH);
                item2.setText("Fox");
                MenuItem item3 = new MenuItem(menu, SWT.PUSH);
                item3.setText("Pony");



                Point loc = btnCntrl.getLocation();
                Rectangle rect = btnCntrl.getBounds();

                Point mLoc = new Point(loc.x-1, loc.y+rect.height);

                menu.setLocation(shell.getDisplay().map(btnCntrl.getParent(), null, mLoc));

                menu.setVisible(true);
            }
        });



        /***
         * 
         * 
         * Approach 2
         * 
         */


        final Composite btnCntrl2 = new Composite(shell, SWT.BORDER);
        btnCntrl2.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
        btnCntrl2.setBackgroundMode(SWT.INHERIT_FORCE);
        GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).spacing(0, 1).applyTo(btnCntrl2);
        CLabel lbl2 = new CLabel(btnCntrl2, SWT.NONE);
        lbl2.setText("Animals");
        Button btn2 = new Button(btnCntrl2, SWT.FLAT|SWT.ARROW|SWT.DOWN);
        btn2.setLayoutData(new GridData(GridData.FILL_VERTICAL));

        btn2.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                super.widgetSelected(e);
                Shell menu = (Shell) btnCntrl2.getData("subshell");
                if(menu != null && !menu.isDisposed()){
                    menu.dispose();
                }
                menu = new Shell(shell, SWT.NONE);
                menu.setLayout(new FillLayout());
                Table table = new Table(menu, SWT.FULL_SELECTION);
                table.addListener(SWT.MeasureItem, new Listener() {

                    @Override
                    public void handleEvent(Event event) {
                        event.height = 20; //
                    }
                });

                table.addListener(SWT.PaintItem, new Listener() {

                    @Override
                    public void handleEvent(Event event) {
                        Rectangle bounds = event.getBounds();
                        event.gc.setBackground(event.display.getSystemColor(SWT.COLOR_BLUE));
                        event.gc.drawLine(bounds.x, bounds.y+bounds.height-1, bounds.x+bounds.width, bounds.y+bounds.height-1);
                    }
                });
                TableItem tableItem= new TableItem(table, SWT.NONE);
                tableItem.setText(0, "Hare");

                TableItem tableItem2= new TableItem(table, SWT.NONE);
                tableItem2.setText(0, "Pony" );

                TableItem tableItem3= new TableItem(table, SWT.NONE);
                tableItem3.setText(0, "Dog");


                Point loc = btnCntrl2.getLocation();
                Rectangle rect = btnCntrl2.getBounds();

                Point mLoc = new Point(loc.x, loc.y+rect.height);

                menu.setLocation(shell.getDisplay().map(btnCntrl2.getParent(), null, mLoc));
                menu.pack();
                menu.setVisible(true);

                btnCntrl2.setData("subshell", menu);

            }
        });

        display.addFilter(SWT.MouseDown, new Listener() {

            @Override
            public void handleEvent(Event event) {
                Shell shell = (Shell) btnCntrl2.getData("subshell");
                if(shell != null && !shell.getBounds().contains(event.display.map((Control)event.widget, null, new Point(event.x, event.y)))){
                    shell.dispose();
                    btnCntrl2.setData("subshell", null);
                }
            }
        });

        shell.open();
        while (!shell.isDisposed()) {
          if (!display.readAndDispatch())
            display.sleep();
        }
        display.dispose();
      }
}
