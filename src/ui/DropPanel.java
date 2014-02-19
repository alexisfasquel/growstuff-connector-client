package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Aleks
 * Date: 18/02/14
 * Time: 11:21
 * To change this template use File | Settings | File Templates.
 */
public class DropPanel extends JPanel {

    private BufferedImage mDrop = GrowRes.getImage(GrowRes.DROP);
    private BufferedImage mDropFocus = GrowRes.getImage(GrowRes.DROP_FOCUS);

    private DropListener mListener;

    public interface DropListener {
        public void onDroped(File droppedfile);
    }

    private boolean mFocus = false;

    public DropPanel(DropListener listener) {
        mListener = listener;

        setBackground(GrowRes.GREEN);
        setDropTarget(new ConfigDropper());
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int x, y;
        x =  (getWidth() - mDrop.getWidth() / 2) / 2;
        y =  (getHeight() - mDrop.getHeight() / 2) / 2;
        if(mFocus) {
            g2.drawImage(mDropFocus, x, y, mDrop.getWidth(null) / 2, mDrop.getHeight(null) / 2, null);
        } else {
            g2.drawImage(mDrop, x, y, mDrop.getWidth(null) / 2, mDrop.getHeight(null) / 2, null);
        }
    }

    private class ConfigDropper extends DropTarget {

        @Override
        public void dragEnter(DropTargetDragEvent dtde) {
            mFocus = true;
            repaint();
        }

        @Override
        public void dragOver(DropTargetDragEvent dtde) {}
        @Override
        public void dropActionChanged(DropTargetDragEvent dtde) {}

        @Override
        public void dragExit(DropTargetEvent dte) {
            mFocus = false;
            repaint();
        }

        @Override
        public synchronized void drop(DropTargetDropEvent dtde) {
            try {
                dtde.acceptDrop(DnDConstants.ACTION_COPY);
                List<File> dropppedFiles = (List<File>)dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                for (File file : dropppedFiles) {
                    mListener.onDroped(file);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
