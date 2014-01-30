import usb.core.Bus;
import usb.core.Device;

import javax.usb.*;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Aleks
 * Date: 27/01/14
 * Time: 23:11
 * To change this template use File | Settings | File Templates.
 */
public class UsbDetector {

    private static void dump(UsbDevice device) {
        UsbDeviceDescriptor desc = device.getUsbDeviceDescriptor();
        //System.out.format("%04x:%04x%n", desc.idVendor() & 0xffff, desc.idProduct() & 0xffff);
        if (device.isUsbHub())
        {
            UsbHub hub = (UsbHub) device;
            for (UsbDevice child : (List<UsbDevice>) hub.getAttachedUsbDevices())
            {
                dump(child);
            }
        }
    }

    /*public static void main(String[] args) throws UsbException
    {
        UsbServices services = UsbHostManager.getUsbServices();
        UsbHub rootHub = services.getRootUsbHub();
        findDevice(rootHub, (short)1, (short)1);
    }      */



    public static UsbDevice findDevice(UsbHub hub, short vendorId, short productId)
    {
        for (UsbDevice device : (List<UsbDevice>) hub.getAttachedUsbDevices())
        {
            UsbDeviceDescriptor desc = device.getUsbDeviceDescriptor();
            //System.out.println((desc.idVendor() & 0xffff) + " - " + (desc.idProduct() & 0xffff) + " - " + desc.iSerialNumber() + " - " + desc.bcdDevice());

            //if (desc.idVendor() == 0x2341 && desc.idProduct() == productId) return device;
            if (device.isUsbHub())
            {
                device = findDevice((UsbHub) device, vendorId, productId);
                if (device != null) return device;
            }
            else {
                System.out.format("%04x:%04x%n", desc.idVendor() & 0xffff, desc.idProduct() & 0xffff);
            }
        }
        return null;
    }
}
