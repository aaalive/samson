package info.samson.helpers;


import java.io.InputStream;
import java.io.OutputStream;

import android.content.pm.PackageManager;

public class Utils {
    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
             
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              //Read byte from input stream
                 
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
               
              //Write byte from output stream
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }
    
    public static boolean isAppInstalled(String uri, PackageManager packageManager) {
        boolean oIsInstalled = false;
        try {
            packageManager.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            oIsInstalled = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            oIsInstalled = false;
        }
        return oIsInstalled ;
    }

}